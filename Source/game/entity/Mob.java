/*
 * Copyright (c) 2015. Chris Ferris
 *  This file is licensed under the "GPL V2" license.
 *  Refer to LICENSE for distribution details.
 */

package game.entity;

import level.Layer;
import level.tiles.Tile;

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
	protected boolean isSwimming = false;
	protected int tickCount = 0;
	protected int power = 1;
	protected int wspeed = 4;

	public int getMovingDir() {
		return movingDir;
	}

	public int getPower() {
		return power;
	}

	public int getWspeed() {
		return wspeed;
	}

	public Mob(Layer layer, String name, int x, int y, int speed) {
		super(layer);
		this.name = name;
		this.x = x;
		this.y = y;
		this.speed = speed;
		this.damageable = true;
	}

	public String getName() {
		return name;
	}

	public void move(int xa, int ya) {
		if(xa != 0 && ya != 0) {
			moveX(xa);
			moveY(ya);
			--numSteps;
		} else if(ya != 0)
			moveY(ya);
		else if (xa != 0)
			moveX(xa);
	}

	public void moveY(int ya) {
		if(!collitionCheckY(ya)) {
			if ( ya < 0 ) { movingDir = 0; }
			else if ( ya > 0 ) { movingDir = 1; }
			y += ya * speed;
			++numSteps;
		} else if(this.getName().equals("Bullet"))
			this.isDead = true;
	}

	public void moveX(int xa) {
		if(!collitionCheckX(xa)) {
			if ( xa < 0 ) { movingDir = 2; }
			else if ( xa > 0 ) { movingDir = 3; }
			x += xa * speed;
			++numSteps;
		} else if(this.getName().equals("Bullet"))
			this.isDead = true;
	}

	protected boolean collitionCheckY(int dir) {
		boolean collided = false;
		for(int x=(this.PixWide - this.hitBoxWidth) / 2; x<(this.PixWide + this.hitBoxWidth) / 2; ++x) {
			if(isSolidTileY(dir, x))
				return true;
		}
			for ( Entity e : layer.entities ) {
				if ( !this.equals(e) && //this.hitBoxSize.intersects(e.hitBoxSize)
				(this.x + this.offset + (this.PixWide - this.hitBoxWidth) / 2 <= e.x + (e.PixWide + e.hitBoxWidth*2) / 2
						      && this.x + this.offset + (this.PixWide - this.hitBoxWidth) / 2 >= e.x + (e.PixWide - e.hitBoxWidth*2) / 2)
						     || (this.x + this.offset + (this.PixWide + this.hitBoxWidth) / 2 >= e.x + (e.PixWide - e.hitBoxWidth*2) / 2
								         &&  this.x + this.offset + (this.PixWide + this.hitBoxWidth) / 2 <= e.x + (e.PixWide + e.hitBoxWidth*2) / 2) ) {
						if ( this.y + this.PixHigh + this.hitBoxHeight * (dir - 1) / 2 + dir == e.y + e.PixHigh - e.hitBoxHeight * (1 + dir) / 2 ) {
							damage(e);
							return true;
						}
					}
			}
		return collided;
	}

	protected boolean collitionCheckX(int dir) {
		boolean collided = false;
		for(int y=this.PixHigh - this.hitBoxHeight; y<this.PixHigh; ++y) {
			if ( isSolidTileX(dir, y) )
				return true;
		}
			for ( Entity e : layer.entities ) {
				if ( !this.equals(e) && //this.hitBoxSize.intersects(e.hitBoxSize)
				(this.y + this.offset + (this.PixHigh - this.hitBoxHeight)/2 <= e.y + (e.PixHigh + e.hitBoxHeight*2)/2
						      && this.y + this.offset + (this.PixHigh - this.hitBoxHeight)/2 >= e.y + (e.PixHigh - e.hitBoxHeight*2)/2)
						     || (this.y + this.offset + (this.PixHigh + this.hitBoxHeight)/2 >= e.y + (e.PixHigh - e.hitBoxHeight*2)/2
								         && this.y + this.offset + (this.PixHigh + this.hitBoxHeight)/2 <= e.y + (e.PixHigh + e.hitBoxHeight*2)/2) ) {
						if ( this.x + (this.PixWide + this.hitBoxWidth * dir) / 2 + dir == e.x + (e.PixWide - e.hitBoxWidth * dir) / 2 ) {
							damage(e);
							return true;
						}
					}
			}
		return collided;
	}

	protected void damage(Entity entity) {
		entity.cHitPoints -= this.power;
		if(entity.cHitPoints <= 0)
			entity.isDead = true;
	}

	protected boolean isSolidTileX(int xa, int y) {
		if( layer != null) {
			int x = (this.PixWide + this.hitBoxWidth * xa)/2;
			//Tile tile1 = layer.getTile((this.x + x - xa * this.offset) >> SHIFT, (this.y + y) >> SHIFT);
			Tile tile2 = layer.getTile((this.x + x + xa + this.offset) >> SHIFT, (this.y + y - this.offset) >> SHIFT);
			if(/*tile1.isSolid() ||*/ tile2.isSolid())
				return true;
		}
		return false;
	}

	protected boolean isSolidTileY(int ya, int x) {
		if( layer != null) {
			int y = this.PixHigh + this.hitBoxHeight*(ya-1)/2;
//			Tile tile1 = layer.getTile((this.x + x) >> SHIFT, (this.y + y - ya * (this.speed>1?this.speed/2:1)) >> SHIFT);
			Tile tile2 = layer.getTile((this.x + x + this.offset) >> SHIFT, (this.y + y + ya - this.offset) >> SHIFT);
			if(/*tile1.isSolid() ||*/ tile2.isSolid())
				return true;
		}
		return false;
	}

	protected boolean isSolidTile(int xa, int ya, int x, int y) {
		if( layer != null) {
			Tile lastTile = layer.getTile((this.x + x) >> SHIFT, (this.y + y) >> SHIFT);
			Tile newTile = layer.getTile((this.x + x + xa) >> SHIFT, (this.y + y + ya) >> SHIFT);
			if(!lastTile.equals(newTile) && newTile.isSolid())
				return true;
		}
		return false;
	}

}
