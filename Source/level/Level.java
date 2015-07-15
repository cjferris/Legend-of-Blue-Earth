/*
 * Copyright (c) 2015. Chris Ferris
 *  This file is licensed under the "GPL V2" license.
 *  Refer to LICENSE for distribution details.
 */

package level;

import game.entity.Entity;
import game.gfx.Screen;
import level.tiles.Tile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Christopher on 7/6/2015.
 */
public class Level {

	private byte[] tiles;
	public int width;
	public int height;
	public List<Entity> entities = new ArrayList<Entity>();
	protected String imagePath;
	protected BufferedImage image;

	public Level(String imagePath) {
		if(imagePath != null) {
			this.imagePath = imagePath;
			this.loadLevelFromFile();
		} else {
			this.width = 64;
			this.height = 64;
			tiles = new byte[width * height];
			this.generateLevel();
		}
	}

	private void loadLevelFromFile() {

		try {
			this.image = ImageIO.read(Level.class.getResource(this.imagePath));
			this.width = image.getWidth();
			this.height = image.getHeight();
			tiles = new byte[width*height];
			this.loadTiles();
		} catch ( IOException e ) {
			e.printStackTrace();
		}
	}

	private void loadTiles() {
		int [] tileColors = this.image.getRGB(0, 0, width, height, null, 0, width);
		for(int y=0; y<height; ++y) {
			for(int x=0; x<width; ++x) {
				tileCheck: for(Tile t : Tile.tiles) {
					if(t != null && t.getLevelColor() == tileColors[x+y*width]){
						this.tiles[x+y*width] = t.getId();
						break tileCheck;
					}
				}
			}
		}
	}

	protected void saveLevelToFile() {
		try {
			ImageIO.write(image, "png", new File(Level.class.getResource(this.imagePath).getFile()));
		} catch ( IOException e ) {
			e.printStackTrace();
		}
	}

	public void alterTile(int x, int y, Tile newTile) {
		this.tiles[x+y*width] = newTile.getId();
		image.setRGB(x, y, newTile.getLevelColor());
	}

	public void generateLevel() {
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				if(x*y % 10 < 7)
					tiles[x + y * width] = Tile.GRASS.getId();
				else
					tiles[x + y * width] = Tile.STONE.getId();
			}
		}
	}

	public void tick() {
		for(Entity e : entities)
			e.tick();
		for(Tile t : Tile.tiles) {
			if(t != null)
				t.tick();
		}
	}

	public void renderTiles(Screen screen, int xOffset, int yOffset) {
		if (xOffset < 0)
			xOffset = 0;
		if (xOffset > ((width << 3) - screen.width))
			xOffset = ((width << 3) - screen.width);
		if (yOffset < 0)
			yOffset = 0;
		if (yOffset > ((height << 3) - screen.height))
			yOffset = ((height << 3) - screen.height);

		screen.setOffset(xOffset, yOffset);

		for (int y = (yOffset >> 3); y < (yOffset + screen.height >> 3) + 1; ++y) {
			for (int x = (xOffset >> 3); x < (xOffset + screen.width >> 3) + 1; ++x) {
				getTile(x, y).render(screen, this, x << 3, y << 3);
			}
		}
	}

	public void renderEntities(Screen screen) {
		for(Entity e : entities) {
			e.render(screen);
		}
	}

	public Tile getTile(int x, int y) {
		if (0 > x || x >= width || 0 > y || y >= height)
			return Tile.VOID;
		return Tile.tiles[tiles[x + y * width]];
	}

	public void addEntity(Entity entity) {
		this.entities.add(entity);
	}
}