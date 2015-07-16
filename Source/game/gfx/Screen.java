/*
 * Copyright (c) 2015. Chris Ferris
 *  This file is licensed under the "GPL V2" license.
 *  Refer to LICENSE for distribution details.
 */

package game.gfx;

/**
 * Created by Christopher on 7/3/2015.
 */
public class Screen {

	public static final int MAP_WIDTH = 64;
	public static final int MAP_WIDTH_MASK = MAP_WIDTH - 1;

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

	public void render(int xPos, int yPos, int tile, int color) {
		render(xPos, yPos, tile, color, 0x00, 1);
	}

	public void render(int xPos, int yPos, int tile, int inColor, int mirrorDir, int scale) {
		xPos -= xOffset;
		yPos -= yOffset;

		boolean mirrorX = (mirrorDir & BIT_MIRROR_X) > 0;
		boolean mirrorY = (mirrorDir & BIT_MIRROR_Y) > 0;

		int scaleMap = scale - 1;
		int xTile = tile % 32;
		int yTile = tile / 32;
		int tileOffset = (xTile << 3) + (yTile << 3) * sheet.width;

		for ( int y = 0; y < 8; ++y ) {
			int ySheet = y;
			if ( mirrorY )
				ySheet = 7 - y;
			int yPixel = y + yPos + (y * scaleMap) - ((scaleMap << 3) / 2);
			for ( int x = 0; x < 8; ++x ) {
				int xSheet = x;
				if ( mirrorX )
					xSheet = 7 - x;
				int xPixel = x + xPos + (x * scaleMap) - ((scaleMap << 3) / 2);
				int color = (inColor >> (sheet.pixels[xSheet + ySheet * sheet.width + tileOffset] * 8)) & 255;
				if ( color < 255 ) {
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