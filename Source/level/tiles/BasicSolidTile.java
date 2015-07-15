/*
 * Copyright (c) 2015. Chris Ferris
 *  This file is licensed under the "GPL V2" license.
 *  Refer to LICENSE for distribution details.
 */

package level.tiles;

/**
 * Created by Christopher on 7/11/2015.
 */
public class BasicSolidTile extends BasicTile{

	public BasicSolidTile(int id, int x, int y, int tileColor, int levelColor) {
		super(id, x, y, tileColor, levelColor);
		this.solid = true;
	}
}
