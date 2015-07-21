/*
 * Copyright (c) 2015. Chris Ferris
 *  This file is licensed under the "GPL V2" license.
 *  Refer to LICENSE for distribution details.
 */

package level.tiles;

import game.gfx.Colors;
import game.gfx.Screen;
import level.Level;

public abstract class Tile {

	public static final Tile[] tiles = new Tile[256];
	public static final Tile VOID = new BasicSolidTile(0, 0, 0, Colors.get(new int[] {000, -1, -1, -1, -1, -1}), 0xff000000);
	public static final Tile STONE = new BasicSolidTile(1, 1, 0, Colors.get(new int[] {-1, Colors.HGray, -1, -1, -1, -1}), 0xff606060);
	public static final Tile GRASS = new BasicTile(2, 2, 0, Colors.get(new int[] {-1, Colors.HGreen, Colors.FGreen, -1, -1, -1}), 0xff00ff00);
	public static final Tile WATER = new AnimatedTile(3, new int[][]{{0,5},{1,5},{2,5},{1,5}}, Colors.get(new int[] {-1, Colors.HBlue, Colors.FBlue, -1, -1, -1}), 0xff0000ff, 750);
	public static final Tile TREE = new BasicSolidTile(4, 1, 22, Colors.get(new int[] {-1, 432, 151, 141, -1, -1}), 0xff238723);

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

	public abstract void render(Screen screen, Level level, int x, int y);

}