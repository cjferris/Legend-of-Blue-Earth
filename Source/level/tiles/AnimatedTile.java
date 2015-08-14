/*
 * Copyright (c) 2015. Chris Ferris
 *  This file is licensed under the "GPL V2" license.
 *  Refer to LICENSE for distribution details.
 */

package level.tiles;

import protolobe.LoBEmain;

/**
 * Created by Christopher on 7/12/2015.
 */
public class AnimatedTile extends BasicTile{

	protected int [][] animationTileCoordinates;
	protected int currentAnimationIndex;
	protected long lastIterationTime;
	protected int animationDelay;

	public AnimatedTile(int id, int [][] animationCoordinates, int[] tileColor, int levelColor, int animationDelay) {
		super(id, animationCoordinates[0][0], animationCoordinates[0][1], tileColor, levelColor);
		this.animationTileCoordinates = animationCoordinates;
		this.currentAnimationIndex = 0;
		this.lastIterationTime = System.currentTimeMillis();
		this.animationDelay = animationDelay;
	}

	@Override
	public void tick() {
		if((System.currentTimeMillis() - lastIterationTime) >= animationDelay) {
			lastIterationTime = System.currentTimeMillis();
			currentAnimationIndex = (currentAnimationIndex + 1) % animationTileCoordinates.length;
			this.tileId = animationTileCoordinates[currentAnimationIndex][0] + animationTileCoordinates[currentAnimationIndex][1]* LoBEmain.TILES_PER_ROW;
		}
	}
}
