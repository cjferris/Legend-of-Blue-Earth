/*
 * Copyright (c) 2015. Chris Ferris
 *  This file is licensed under the "GPL V2" license.
 *  Refer to LICENSE for distribution details.
 */

package game.gfx;

import protolobe.LoBEmain;

/**
 * Created by Christopher on 7/3/2015.
 */
public class Screen {

	public static final int MAP_WIDTH = 64;
	public static final int MAP_WIDTH_MASK = MAP_WIDTH - 1;
	public static final int TRANSPARENT = LoBEmain.TRANSPARENT;
	public static final int COL_CON = LoBEmain.COL_CONT;
	public static final int SHIFT = LoBEmain.TILE_SHIFT;
	public static final int TILES_PER_ROW = LoBEmain.TILES_PER_ROW;
	public static final int TILE_SIZE = LoBEmain.TILE_SIZE;
	public static final byte BIT_MIRROR_X = 0x01;
	public static final byte BIT_MIRROR_Y = 0x02;

	public int[] pixels;

	public int xOffset = 0;
	public int yOffset = 0;

	public int width;
	public int height;


	public SpriteSheet sheet;

	public Screen(int width, int height, SpriteSheet sheet) {
		this.width = width;
		this.height = height;
		this.sheet = sheet;
		pixels = new int[width * height];

	}

	public void setOffset(int xOffset, int yOffset) {
		this.xOffset = xOffset;
		this.yOffset = yOffset;
	}

	public void render(int xPos, int yPos, int tile, int[] inColor, int mirrorDir, int scale) {
		xPos -= xOffset;
		yPos -= yOffset;

		boolean mirrorX = (mirrorDir & BIT_MIRROR_X) > 0;
		boolean mirrorY = (mirrorDir & BIT_MIRROR_Y) > 0;

		int scaleMap = scale - 1;
		int xTile = tile % TILES_PER_ROW;
		int yTile = tile / TILES_PER_ROW;
		int tileOffset = (xTile << SHIFT) + (yTile << SHIFT) * sheet.width;

		for ( int y = 0; y < TILE_SIZE; ++y ) {
			int ySheet = y;
			if ( mirrorY )
				ySheet = TILE_SIZE - 1 - y;
			int yPixel = y + yPos + (y * scaleMap) - ((scaleMap << SHIFT) / 2);
			for ( int x = 0; x < TILE_SIZE; ++x ) {
				int xSheet = x;
				if ( mirrorX )
					xSheet = TILE_SIZE - 1 - x;
				int xPixel = x + xPos + (x * scaleMap) - ((scaleMap << SHIFT) / 2);
				int color = (inColor[sheet.pixels[xSheet + ySheet * sheet.width + tileOffset]]) & TRANSPARENT;
				if ( color < TRANSPARENT ) {
					for ( int yScale = 0; yScale < scale; ++yScale ) {
						if ( yPixel + yScale >= 0 && yPixel + yScale < height ) {
							for ( int xScale = 0; xScale < scale; ++xScale ) {
								if ( xPixel + xScale >= 0 && xPixel + xScale < width )
									pixels[(xPixel + xScale) + (yPixel + yScale) * width] = color;
							}
						}
					}
				}
			}
		}
	}
}