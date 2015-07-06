/*
 * Copyright (c) 2015. Chris Ferris
 *  This file is licensed under the "GPL V2" license.
 *  Refer to LICENSE for distribution details.
 */

package game.entity;

import game.gfx.Screen;
import level.Level;

/**
 * Created by Christopher on 7/6/2015.
 */
public abstract class Entity {
	public int x, y;
	protected Level level;

	public Entity(Level level) {
		init(level);
	}

	protected final void init(Level level) {
		this.level = level;
	}

	public abstract void tick();
	public abstract void render(Screen screen);
}
