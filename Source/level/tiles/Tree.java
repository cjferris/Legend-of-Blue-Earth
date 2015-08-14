/*
 * Copyright (c) 2015. Chris Ferris
 *  This file is licensed under the "GPL V2" license.
 *  Refer to LICENSE for distribution details.
 */

package level.tiles;

import game.gfx.Screen;

/**
 * Created by Christopher on 8/4/2015.
 */
public class Tree extends Tile {

	protected int xTile;
	protected int yTile;
	protected int scale = 1;
	protected int XSize;
	protected int YSize;
	protected int[] treeColor;

	public Tree(int id, int x, int y, int XSize, int YSize, int [] treeColor, int levelColor) {
		super(id, true, false, levelColor);
		this.xTile = x;
		this.yTile = y;
		this.treeColor = treeColor;
		this.XSize = XSize;
		this.YSize = YSize;
	}


	@Override
	public void tick() {}



	public void render(Screen screen, int x, int y) {
		int modifier = TILE_SIZE * scale;
		int xOffset = x - modifier / 2;
		int yOffset = y - modifier / 2 - TILE_SIZE/2;
		for(int i=0; i<YSize; ++i) {
			for(int j=0; j<XSize; ++j) {
				screen.render(xOffset + modifier, yOffset + modifier, xTile+j + (yTile+i) * TILES_PER_ROW, treeColor, 0x00, scale);
			}
		}

	}

}
