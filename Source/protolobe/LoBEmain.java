/*
 * Copyright (c) 2015. Chris Ferris
 *  This file is licensed under the "GPL V2" license.
 *  Refer to LICENSE for distribution details.
 */

/**
 * Created by Christopher on 7/2/2015.
 */
package protolobe;

import game.entity.Bullet;
import game.entity.Mirror;
import game.entity.Player;
import game.gfx.Colors;
import game.gfx.Screen;
import game.gfx.SpriteSheet;
import level.Layer;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.util.Random;

import static java.lang.Math.ceil;
import static java.lang.Math.log;
import static java.lang.Math.pow;
import static java.lang.Math.sqrt;

public class LoBEmain extends Canvas implements Runnable {

	private static final long serialVersionUID = 1L;
	public static final int NUM_RGB = 10;
	public static final int RGB_SHIFT = (int)pow(10D,(double)Integer.toString(NUM_RGB).length());
	public static final int NUM_C = 6;
	public static final int WIDTH = 600;
	public static final int HEIGHT = WIDTH/16*9;
	public static final int SCALE = 3;
	public static final int TILE_SIZE = 16;
	public static final int TILES_PER_ROW = 16;
	public static final int TILE_SHIFT = (int)sqrt((double)TILE_SIZE);
	public static final String NAME = "Legend of Blue Earth";
	public static final Dimension DIMENSIONS = new Dimension(WIDTH*SCALE, HEIGHT*SCALE);
	public static final int COL_CONT = (int)ceil(log(pow((double)NUM_RGB, 3.0D)) / log(2.0D));
	public static final int TRANSPARENT = (int)pow(2D, (double)COL_CONT)-1;

	private JFrame frame;
	public boolean running = false;
	public int tickCount = 0;
	private BufferedImage image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
	private int [] pixels = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();
	private int [] colors = new int[NUM_RGB*NUM_RGB*NUM_RGB];

	public static LoBEmain loBEmain;
	private Screen screen;
	public InputHandler input;
	public static Colors sColors;
	public static Layer layer;
	public static Player player;
	public static Mirror mirror;
	public static Random random;

	public LoBEmain () {
		setPreferredSize(DIMENSIONS);
		setMinimumSize(DIMENSIONS);
		setMaximumSize(DIMENSIONS);

		frame = new JFrame(NAME);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(new BorderLayout());
		frame.add(this, BorderLayout.CENTER);
		frame.pack();
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);

	}

	public void init() {
		loBEmain = this;
		int index = 0;
		for(int r=0; r<NUM_RGB; ++r) {
			for(int g=0; g<NUM_RGB; ++g) {
				for(int b=0; b<NUM_RGB; ++b) {
					int red = r*255/(NUM_RGB-1);
					int green = g*255/(NUM_RGB-1);
					int blue = b*255/(NUM_RGB-1);
					colors[index++] = red<<16 | green<<8 | blue;
				}
			}
		}

		random = new Random();
		sColors = new Colors();
		screen = new Screen(WIDTH, HEIGHT, new SpriteSheet("/SpriteSheet.png", NUM_C));
		input = new InputHandler(this);
		layer = new Layer("/levels/waterTestLevel.png"/*null*/);
		player = new Player(layer, 50, 100, input, JOptionPane.showInputDialog(this, "Please enter a character name: "));
		layer.addEntity(player);
		mirror = new Mirror(layer, "Mirror", 100, 120, 1);
		layer.addEntity(mirror);
	}

	public synchronized void start() {
		running = true;
		new Thread(this).start();
	}

	public synchronized void stop() {
		running = false;
	}




	/**
	 * When an object implementing interface <code>Runnable</code> is used
	 * to create a thread, starting the thread causes the object's
	 * <code>run</code> method to be called in that separately executing
	 * thread.
	 * <p/>
	 * The general contract of the method <code>run</code> is that it may
	 * take any action whatsoever.
	 *
	 * @see Thread#run()
	 */
	@Override
	public void run() {
		long lastTime = System.nanoTime();
		long lastTimer = System.currentTimeMillis();
		double nsPerTick = 1000000000D/60D;
		int ticks = 0;
		int frames = 0;
		double delta = 0;

		init();

		while ( running ){
			long now = System.nanoTime();
			delta += (now - lastTime)/nsPerTick;
			lastTime = now;
			boolean shouldRender = true;

			while(delta>=1) {
				++ticks;
				tick();
				--delta;
				shouldRender = true;
			}

			try {
				Thread.sleep(2);
			} catch ( InterruptedException e ) {
				e.printStackTrace();
			}

			if(shouldRender) {
				++frames;
				render();
			}

			if(System.currentTimeMillis() - lastTimer >= 3000){
				lastTimer += 3000;
//				System.out.println( "x = " + player.x + " y = " + player.y);
				frames = 0;
				ticks = 0;
			}
		}
	}

	private int x = 0, y = 0;

	public void tick() {
		++tickCount;
		layer.tick();
	}

	public void render() {
		BufferStrategy bs = getBufferStrategy();
		if(bs == null){
			createBufferStrategy(3);
			return;
		}

		if(player.isAttacking()) {
			Bullet bullet = new Bullet(layer, "Bullet", player.x, player.y, player.getMovingDir(), player.getWspeed(), player.getPower());
			layer.addEntity(bullet);
		}

		int xOffset = player.x - screen.width/2;
		int yOffset = player.y - screen.height/2;

		layer.renderTiles(screen, xOffset, yOffset);
		layer.renderTrees(screen, xOffset, yOffset);
		layer.removeDead();
		if(mirror.isDead()) {
			int x, y;
			do {
				x = random.nextInt(image.getWidth());
				y = random.nextInt(image.getHeight());
			}while (x == player.x || y == player.y);
			mirror = new Mirror(layer, "Mirror", x, y, 1);
			layer.addEntity(mirror);
		}
		layer.renderEntities(screen);

		for(int y=0; y<screen.height; ++y) {
			for(int x=0; x<screen.width; ++x) {
				int colorCode = screen.pixels[x + y*screen.width];
				if(colorCode<TRANSPARENT) pixels[x + y*WIDTH] = colors[colorCode];
			}
		}


		Graphics g = bs.getDrawGraphics();
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, getWidth(), getHeight());
		g.drawImage(image, 0, 0, getWidth(), getHeight(), null);
		g.dispose();
		bs.show();
	}

	public static void main(String [] args) {
		new LoBEmain().start();
	}
}
