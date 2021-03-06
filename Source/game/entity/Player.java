/*
 * Copyright (c) 2015. Chris Ferris
 *  This file is licensed under the "GPL V2" license.
 *  Refer to LICENSE for distribution details.
 */

package game.entity;

import game.gfx.Colors;
import game.gfx.Font;
import game.gfx.Screen;
import level.Layer;
import protolobe.InputHandler;

/**
 * Created by Christopher on 7/6/2015.
 */
public class Player extends Mob {

	private InputHandler input;
	private int[] color = Colors.get(new int[] {-1, Colors.Gray7, Colors.Blue3, Colors.MSkin, -1, -1});
	private int scale = 1;

	public boolean isAttacking() {
		return isAttacking;
	}

	protected boolean isAttacking = false;


	public Player(Layer layer, int x, int y, InputHandler input, String name) {
		super(layer, name, x, y, 1);
		this.PixWide = TILE_SIZE;
		this.PixHigh = TILE_SIZE;
		this.input = input;
	}

	@Override
	public void tick() {
		int xa = 0;
		int ya = 0;
		if(input.left.isPressed())        {xa--;}
		else if(input.right.isPressed()) {xa++;}
		if(input.down.isPressed())        {ya++;}
		else if(input.up.isPressed())    {ya--;}
		if(input.attack.isPressed() && tickCount%10 == 0 ) {
			isAttacking = true;
		} else
			isAttacking = false;
		if(xa != 0 || ya != 0) {
			move(xa, ya);
			isMoving = true;
		} else
				isMoving = false;
		if( layer.getTile(this.x>>SHIFT, this.y>>SHIFT).getId() == 3)
			isSwimming = true;
		else
			isSwimming = false;
		++tickCount;
	}


	@Override
	public void render(Screen screen) {
		int xTile = 0;
		int yTile = 2;
		int walkSpeed = 4;
		int flipTop = (numSteps >> walkSpeed) & 1;
		int flipBottom = (numSteps >> walkSpeed) & 1;

		if(movingDir == 1)
			xTile += 2;
		else if(movingDir > 1) {
			xTile += 4 + ((numSteps >> walkSpeed) & 1) * 2;
			flipTop = (movingDir - 1) % 2;
		}

		int modifier = TILE_SIZE * scale;
		int xOffset = x - modifier / 2;
		int yOffset = y - modifier / 2 - TILE_SIZE/2;
		if(isSwimming) {
			int[] waterColor;
			yOffset += TILE_SIZE-1;
			if(tickCount % 60 < 15)
				waterColor = Colors.get(new int[] {-1, -1, Colors.Yellow6+Colors.Blue4, -1, -1, -1});
			else if(15 <= tickCount % 60 && tickCount % 60 < 30) {
				--yOffset;
				waterColor = Colors.get(new int[] {-1, Colors.Yellow6+Colors.Blue4, Colors.Yellow7+Colors.Blue4, -1, -1, -1});
			}
			else if(30 <= tickCount % 60 && tickCount % 60 < 45)
				waterColor = Colors.get(new int[] {-1, Colors.Yellow7+Colors.Blue4, -1, Colors.Yellow6+Colors.Blue4, -1, -1});
			else {
				--yOffset;
				waterColor = Colors.get(new int[] {-1, Colors.Yellow6+Colors.Blue4, Colors.Yellow7+Colors.Blue4, -1, -1, -1});
			}
			screen.render(xOffset, yOffset, 3, waterColor, 0x00, 1);
			screen.render(xOffset + TILE_SIZE, yOffset, 3, waterColor, 0x01, 1);
		}
		screen.render(xOffset + (modifier * flipTop), yOffset, xTile + yTile * TILES_PER_ROW, color, flipTop, scale); // upper
		screen.render(xOffset + modifier - (modifier * flipTop), yOffset, (xTile + 1) + yTile * TILES_PER_ROW, color, flipTop, scale); // upper body part 2
		if(!isSwimming) {
			screen.render(xOffset + (modifier * flipBottom), yOffset + modifier, xTile + (yTile + 1) * TILES_PER_ROW, color, flipBottom, scale); // lower body part 1
			screen.render(xOffset + modifier - (modifier * flipBottom), yOffset + modifier, (xTile + 1) + (yTile + 1) * TILES_PER_ROW, color, flipBottom, scale); // lower body part 2
		}
		if(name != null)
			Font.render(name, screen, xOffset - ((TILE_SIZE/2 - 1 - name.length()%2) * (name.length() - 1) / 2), yOffset - 10*SHIFT/2,
					           Colors.get(new int[] {-1, Colors.Gray4, Colors.Gray3, Colors.Gray2, Colors.Gray1, Colors.White}), 1);
	}

	@Override
	public void destroy() {

	}
}
