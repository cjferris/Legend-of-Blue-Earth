/*
 * Copyright (c) 2015. Chris Ferris
 *  This file is licensed under the "GPL V2" license.
 *  Refer to LICENSE for distribution details.
 */

/**
 * Created by Christopher on 7/2/2015.
 */
package protolobe;

import game.entity.Player;
import game.gfx.Colors;
import game.gfx.Screen;
import game.gfx.SpriteSheet;
import level.Level;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

public class LoBEmain extends Canvas implements Runnable {

	private static final long serialVersionUID = 1L;

	public static final int NUM_C = 6;
	public static final int WIDTH = 240;
	public static final int HEIGHT = WIDTH/12*9;
	public static final int SCALE = 3;
	public static final String NAME = "Legend of Blue Earth";

	private JFrame frame;
	public boolean running = false;
	public int tickCount = 0;
	private BufferedImage image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
	private int [] pixels = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();
	private int [] colors = new int[NUM_C*NUM_C*NUM_C];

	private Screen screen;
	public InputHandler input;
	public Colors sColors;
	public Level level;
	public Player player;

	public LoBEmain () {
		setPreferredSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));
		setMinimumSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));
		setMaximumSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));

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
		int index = 0;
		for(int r=0; r<NUM_C; ++r) {
			for(int g=0; g<NUM_C; ++g) {
				for(int b=0; b<NUM_C; ++b) {
					int red = r*255/(NUM_C-1);
					int green = g*255/(NUM_C-1);
					int blue = b*255/(NUM_C-1);
					colors[index++] = red<<16 | green<<8 | blue;
				}
			}
		}

		sColors = new Colors(NUM_C);
		screen = new Screen(WIDTH, HEIGHT, new SpriteSheet("/sprite_sheet2.png"));
		input = new InputHandler(this);
		level = new Level("/levels/waterTestLevel.png");
		player = new Player(level, 50, 100, input, JOptionPane.showInputDialog(this, "Please enter a character name: "));
		level.addEntity(player);
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

			if(System.currentTimeMillis() - lastTimer >= 1000){
				lastTimer += 1000;
				System.out.println(frames + " frames, " + ticks + " ticks");
				frames = 0;
				ticks = 0;
			}
		}
	}

	private int x = 0, y = 0;

	public void tick() {
		++tickCount;
		level.tick();
	}

	public void render() {
		BufferStrategy bs = getBufferStrategy();
		if(bs == null){
			createBufferStrategy(3);
			return;
		}

		int xOffset = player.x - screen.width/2;
		int yOffset = player.y - screen.height/2;

		level.renderTiles(screen, xOffset, yOffset);

/*		for (int x = 0; x < level.width; ++x) {
			int color = Colors.get(-1, -1, -1, 000);
			if (x % 10 == 0 && x != 0) {
				color = Colors.get(-1, -1, -1, 500);
			}
			Font.render((x % 10) + "", screen, 0 + (x * 8), 0, color, 1);
		}*/

		level.renderEntities(screen);

		for(int y=0; y<screen.height; ++y) {
			for(int x=0; x<screen.width; ++x) {
				int colorCode = screen.pixels[x + y*screen.width];
				if(colorCode<255) pixels[x + y*WIDTH] = colors[colorCode];
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
