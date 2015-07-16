/*
 * Copyright (c) 2015. Chris Ferris
 *  This file is licensed under the "GPL V2" license.
 *  Refer to LICENSE for distribution details.
 */

package game.gfx;

/**
 * Created by Christopher on 7/3/2015.
 */
public class Colors {

	private static int numColors = 0;

	public Colors(int number) {this.numColors = number;}

	public static int get(int color1, int color2, int color3, int color4) {
		return (get(color4)<<24) + (get(color3)<<16) + (get(color2)<<8) + get(color1);
	}

	private static int get(int color) {
		if(color<0) return 255;
		int red = color/100%10;
		int green = color/10%10;
		int blue = color%10;
		return red*numColors*numColors + green*numColors + blue;
	}
}
