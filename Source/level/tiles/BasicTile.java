/*
 * Copyright (c) 2015. Chris Ferris
 *  This file is licensed under the "GPL V2" license.
 *  Refer to LICENSE for distribution details.
 */

package level.tiles;

/**
 * Created by Christopher on 7/6/2015.
 */

import game.gfx.Screen;
import level.Level;

public class BasicTile extends Tile {

	protected int tileId;
	protected int[] tileColor;

	public BasicTile(int id, int x, int y, int[] tileColor, int levelColor) {
		super(id, false, false, levelColor);
		this.tileId = x + y;
		this.tileColor = tileColor;
	}

	public void render(Screen screen, Level level, int x, int y) {
		screen.render(x, y, tileId, tileColor, 0x00, 1);
	}

	@Override
	public void tick() {

	}

}
