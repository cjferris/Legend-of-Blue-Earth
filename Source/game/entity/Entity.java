/*
 * Copyright (c) 2015. Chris Ferris
 *  This file is licensed under the "GPL V2" license.
 *  Refer to LICENSE for distribution details.
 */

package game.entity;

import game.gfx.Screen;
import level.Layer;
import protolobe.LoBEmain;

/**
 * Created by Christopher on 7/6/2015.
 */
public abstract class Entity {
	public int x, y, mHitPoints, cHitPoints;
	protected final int SHIFT = LoBEmain.TILE_SHIFT;
	protected final int TILE_SIZE = LoBEmain.TILE_SIZE;
	protected final int TILES_PER_ROW = LoBEmain.TILES_PER_ROW;
	protected int PixWide;
	protected int PixHigh;
	protected Layer layer;
	protected int hitBoxWidth;
	protected int hitBoxHeight;
	protected int offset;
	protected boolean isDead = false;
	protected boolean damageable = false;

	public boolean isDead() { return isDead; }
	public boolean isDamageable() {
		return damageable;
	}

	public Entity(Layer layer) {
		this.mHitPoints = this.cHitPoints = 10;
		this.hitBoxWidth = TILE_SIZE;
		this.hitBoxHeight = TILE_SIZE;
		this.offset = 0;
		init(layer);
	}

	protected final void init(Layer layer) {
		this.layer = layer;
	}

	public abstract void tick();
	public abstract void render(Screen screen);
	public abstract void destroy();
}
