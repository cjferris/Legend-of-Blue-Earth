/*
 * Copyright (c) 2015. Chris Ferris
 *  This file is licensed under the "GPL V2" license.
 *  Refer to LICENSE for distribution details.
 */

package game.entity;

import game.gfx.Colors;
import game.gfx.Font;
import game.gfx.Screen;
import level.Level;
import protolobe.InputHandler;

/**
 * Created by Christopher on 7/6/2015.
 */
public class Player extends Mob {

	private InputHandler input;
	private int color = Colors.get(-1, 111, 135, 543);
	private int scale = 1;

	public Player(Level level, int x, int y, InputHandler input, String name) {
		super(level, name, x, y, 1);
		this.input = input;
	}

	@Override
	public boolean hasCollided(int xa, int ya) {
		int xMin = 0;
		int xMax = 7;
		int yMin = 3;
		int yMax = 7;
		return collitionCheck(xa, ya, xMin, xMax, yMin, yMax);
	}

	@Override
	public void tick() {
		int xa = 0;
		int ya = 0;
		if(input.left.isPressed())  {xa--;}
		if(input.right.isPressed()) {xa++;}
		if(input.down.isPressed())  {ya++;}
		if(input.up.isPressed())    {ya--;}
		if(xa != 0 || ya != 0) {
			move(xa, ya);
			isMoving = true;
		} else
				isMoving = false;
		if(level.getTile(this.x>>3, this.y>>3).getId() == 3)
			isSwimming = true;
		else
			isSwimming = false;
		++tickCount;
	}

	@Override
	public void render(Screen screen) {
		int xTile = 0;
		int yTile = 27;
		int walkSpeed = 4;
		int flipTop = (numSteps >> walkSpeed) & 1;
		int flipBottom = (numSteps >> walkSpeed) & 1;

		if(movingDir == 1)
			xTile += 2;
		else if(movingDir > 1) {
			xTile += 4 + ((numSteps >> walkSpeed) & 1) * 2;
			flipTop = (movingDir - 1) % 2;
		}

		int modifier = 8 * scale;
		int xOffset = x - modifier / 2;
		int yOffset = y - modifier / 2 - 4;
		if(isSwimming) {
			int waterColor = 0;
			yOffset += 4;
			if(tickCount % 60 < 15)
				waterColor = Colors.get(-1, -1, 225, -1);
			else if(15 <= tickCount % 60 && tickCount % 60 < 30) {
				--yOffset;
				waterColor = Colors.get(-1, 225, 115, -1);
			}
			else if(30 <= tickCount % 60 && tickCount % 60 < 45)
				waterColor = Colors.get(-1, 115, -1, 225);
			else {
				--yOffset;
				waterColor = Colors.get(-1, 225, 115, -1);
			}
			screen.render(xOffset, yOffset + 3, 26*32, waterColor, 0x00, 1);
			screen.render(xOffset + 8, yOffset + 3, 26*32, waterColor, 0x01, 1);
		}
		screen.render(xOffset + (modifier * flipTop), yOffset, xTile + yTile * 32, color, flipTop, scale); // upper
		screen.render(xOffset + modifier - (modifier * flipTop), yOffset, (xTile + 1) + yTile * 32, color, flipTop, scale); // upper body part 2
		if(!isSwimming) {
			screen.render(xOffset + (modifier * flipBottom), yOffset + modifier, xTile + (yTile + 1) * 32, color, flipBottom, scale); // lower body part 1
			screen.render(xOffset + modifier - (modifier * flipBottom), yOffset + modifier, (xTile + 1) + (yTile + 1) * 32, color, flipBottom, scale); // lower body part 2
		}
		if(name != null)
			Font.render(name, screen, xOffset - ((7 - name.length()%2) * (name.length() - 1) / 2), yOffset - 10, Colors.get(-1, -1, -1, 555), 1);
	}
}
