/*
 * Copyright (c) 2015. Chris Ferris
 *  This file is licensed under the "GPL V2" license.
 *  Refer to LICENSE for distribution details.
 */

package level.tiles;

import game.gfx.Colors;
import game.gfx.Screen;
import protolobe.LoBEmain;

public abstract class Tile {

	protected final int SHIFT = LoBEmain.TILE_SHIFT;
	protected final int TILE_SIZE = LoBEmain.TILE_SIZE;
	protected final int TILES_PER_ROW = LoBEmain.TILES_PER_ROW;
	public static final Tile[] tiles = new Tile[256];
	public static final Tile VOID = new BasicSolidTile(0, 0, 0, Colors.get(new int[] {000, -1, -1, -1, -1, -1}), 0xff000000);
	public static final Tile STONE = new BasicSolidTile(1, 1, 0, Colors.get(new int[] {-1, Colors.Gray4, -1, -1, -1, -1}), 0xff606060);
	public static final Tile GRASS = new BasicTile(2, 2, 0, Colors.get(new int[] {-1, Colors.Green4, Colors.FGreen, -1, -1, -1}), 0xff00ff00);
	public static final Tile WATER = new AnimatedTile(3, new int[][]{{0,1},{1,1},{2,1},{1,1}}, Colors.get(new int[] {-1, Colors.Blue4, Colors.FBlue, -1, -1, -1}), 0xff0000ff, 750);
	public static final Tile TREE0 = new Tree(4, 8, 2, 3, 3, Colors.get(new int[] { -1, Colors.DSkin, Colors.FGreen + Colors.Purple6, Colors.Green1, -1, -1 }), 0xff006600);

	protected byte id;
	protected boolean solid;
	protected boolean emitter;
	private int levelColor;

	public Tile(int id, boolean isSolid, boolean isEmitter, int levelColor) {
		this.id = (byte) id;
		if (tiles[id] != null)
			throw new RuntimeException("Duplicate tile id on" + id);
		this.solid = isSolid;
		this.emitter = isEmitter;
		this.levelColor = levelColor;
		tiles[id] = this;
	}

	public byte getId() {
		return id;
	}

	public boolean isSolid() {
		return solid;
	}

	public boolean isEmitter() {
		return emitter;
	}

	public int getLevelColor() {
		return levelColor;
	}

	public abstract void tick();

	public abstract void render(Screen screen, int x, int y);

}