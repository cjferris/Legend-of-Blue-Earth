/*
 * Copyright (c) 2015. Chris Ferris
 *  This file is licensed under the "GPL V2" license.
 *  Refer to LICENSE for distribution details.
 */

package game.entity;

import game.gfx.Colors;
import game.gfx.Screen;
import level.Layer;

/**
 * Created by Christopher on 7/23/2015.
 */
public class Bullet extends Mob {

	public Bullet(Layer layer, String name, int x, int y, int movingDir, int speed, int power) {
		super(layer, name, x, y, 4);
		this.power = power;
		this.movingDir = movingDir;
		this.PixWide =  TILE_SIZE;
		this.PixHigh =  TILE_SIZE;
		this.hitBoxHeight = TILE_SIZE/2;
		this.hitBoxWidth = TILE_SIZE/2;
		this.offset = TILE_SIZE/2;
		//this.hitBoxSize.setBounds(x + TILE_SIZE/4, y + TILE_SIZE/4, TILE_SIZE/2, TILE_SIZE/2);
//		this.scale = 3;
	}

	@Override
	public void tick() {
		int xa = 0;
		int ya = 0;
		isMoving = true;
		if(movingDir == 0 || movingDir == 1) {
			if(movingDir == 0)
				--ya;
			else
				++ya;
			moveY(ya);
			if ( this.y > layer.height << SHIFT || this.y < 0)
				isDead = true;
		} else {
			if ( movingDir == 2 )
				--xa;
			else
				++xa;
			moveX(xa);
			if ( this.x > layer.width << SHIFT || this.x < 0)
				isDead = true;
		}
	}


	@Override
	public void render(Screen screen) {
		int xTile = 4;
		int yTile = 0;
		int modifier = TILE_SIZE * scale;
		int xOffset = x - modifier / 2;
		int yOffset = y - modifier / 2 - TILE_SIZE/2;
		screen.render(xOffset + modifier, yOffset + modifier/2, xTile + yTile * TILES_PER_ROW, Colors.get(new int[] {-1, Colors.Orange, Colors.FYellow, -1, -1, -1}), 0x00, scale);
	}

	@Override
	public void destroy() {

	}
}
