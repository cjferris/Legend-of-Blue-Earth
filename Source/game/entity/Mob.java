/*
 * Copyright (c) 2015. Chris Ferris
 *  This file is licensed under the "GPL V2" license.
 *  Refer to LICENSE for distribution details.
 */

package game.entity;

import level.Level;

/**
 * Created by Christopher on 7/6/2015.
 */
public abstract class Mob extends Entity{

	protected String name;
	protected int speed;
	protected int numSteps = 0;
	protected boolean isMoving = false;
	protected int movingDir = 1;
	protected int scale = 1;

	public Mob(Level level, String name, int x, int y, int speed) {
		super(level);
		this.name = name;
		this.x = x;
		this.y = y;
		this.speed = speed;
	}

	public String getName() {
		return name;
	}

	public void move(int xa, int ya) {

		if(xa != 0 && ya != 0) {
			move(xa, 0);
			move(0, ya);
			--numSteps;
			return;
		}
		numSteps++;
		if(!hasCollided(xa, ya)) {
			if(ya < 0)
				movingDir = 0;
			if(ya > 0)
				movingDir = 1;
			if(xa < 0)
				movingDir = 2;
			if(xa > 0)
				movingDir = 3;
			x += xa * speed;
			y += ya * speed;
		}
	}

	public abstract boolean hasCollided(int xa, int ya);

}
