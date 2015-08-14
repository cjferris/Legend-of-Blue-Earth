/*
 * Copyright (c) 2015. Chris Ferris
 *  This file is licensed under the "GPL V2" license.
 *  Refer to LICENSE for distribution details.
 */

package level;

import game.entity.Entity;
import game.gfx.Screen;
import level.tiles.Tile;
import protolobe.LoBEmain;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Christopher on 7/6/2015.
 */
public class Layer {

	private byte[] tiles;
	private byte [] trees;
	private int [] tileColors;
	public int width;
	public int height;
	public List<Entity> entities = new ArrayList<Entity>();
	protected int shift;
	protected String imagePath;
	protected BufferedImage image;

	public Layer(String imagePath) {
		this.shift = LoBEmain.TILE_SHIFT;
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
			this.image = ImageIO.read(Layer.class.getResource(this.imagePath));
			this.width = image.getWidth();
			this.height = image.getHeight();
			tiles = new byte[width*height];
			trees = new byte[width * height];
			this.loadTiles();
		} catch ( IOException e ) {
			e.printStackTrace();
		}
	}

	private void loadTiles() {
		tileColors = this.image.getRGB(0, 0, width, height, null, 0, width);
		for(int y=0; y<height; ++y) {
			for(int x=0; x<width; ++x) {
				tileCheck: for(Tile t : Tile.tiles) {
					if(t != null && t.getLevelColor() == tileColors[x+y*width]){
						if(t.getId() == 4)
							trees[x+y*width] = t.getId();
						else
							this.tiles[x+y*width] = t.getId();
						break tileCheck;
					}
				}
			}
		}
	}

	protected void saveLevelToFile() {
		try {
			ImageIO.write(image, "png", new File(Layer.class.getResource(this.imagePath).getFile()));
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
				if(x*y % 10 < LoBEmain.TILE_SIZE-1)
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
		if (xOffset > ((width << shift) - screen.width))
			xOffset = ((width << shift) - screen.width);
		if (yOffset < 0)
			yOffset = 0;
		if (yOffset > ((height << shift) - screen.height))
			yOffset = ((height << shift) - screen.height);

		screen.setOffset(xOffset, yOffset);

		for (int y = (yOffset >> shift); y < (yOffset + screen.height >> shift) + 1; ++y) {
			for (int x = (xOffset >> shift); x < (xOffset + screen.width >> shift) + 1; ++x) {
					getTile(x, y).render(screen, x << shift, y << shift);
			}
		}
	}

	public void renderTrees(Screen screen, int xOffset, int yOffset) {
		if (xOffset < 0)
			xOffset = 0;
		if (xOffset > ((width << shift) - screen.width))
			xOffset = ((width << shift) - screen.width);
		if (yOffset < 0)
			yOffset = 0;
		if (yOffset > ((height << shift) - screen.height))
			yOffset = ((height << shift) - screen.height);

		screen.setOffset(xOffset, yOffset);

		for (int y = (yOffset >> shift); y < (yOffset + screen.height >> shift) + 1; ++y) {
			for (int x = (xOffset >> shift); x < (xOffset + screen.width >> shift) + 1; ++x) {
				if(tileColors[x+y*width] ==  0xff006600)
				Tile.tiles[4].render(screen, x << shift, y << shift);
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

	public void removeEntity(Entity entity) { this.entities.remove(entity); }

	public void removeDead() {
		entities.removeIf(e -> e.isDead());
	}
}