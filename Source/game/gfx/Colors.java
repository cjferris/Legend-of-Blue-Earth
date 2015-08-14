/*
 * Copyright (c) 2015. Chris Ferris
 *  This file is licensed under the "GPL V2" license.
 *  Refer to LICENSE for distribution details.
 */

package game.gfx;

import protolobe.LoBEmain;

/**
 * Created by Christopher on 7/3/2015.
 */
public class Colors {

	public static final int FRed = (LoBEmain.NUM_RGB-1)*LoBEmain.RGB_SHIFT*LoBEmain.RGB_SHIFT;
	public static final int FGreen = (LoBEmain.NUM_RGB-1)*LoBEmain.RGB_SHIFT;
	public static final int FBlue = LoBEmain.NUM_RGB-1;
	public static final int White = FRed + FGreen + FBlue;
	public static final int FPink = FRed + FBlue;
	public static final int FYellow = FRed + FGreen;
	public static final int FCyan = FBlue + FGreen;
	public static final int Red1 = ((LoBEmain.NUM_RGB-1)*7/8)*LoBEmain.RGB_SHIFT*LoBEmain.RGB_SHIFT;
	public static final int Green1 = ((LoBEmain.NUM_RGB-1)*7/8)*LoBEmain.RGB_SHIFT;
	public static final int Blue1 = FBlue*7/8;
	public static final int Gray1 = Red1 + Green1 + Blue1;
	public static final int Red2 = ((LoBEmain.NUM_RGB-1)*3/4)*LoBEmain.RGB_SHIFT*LoBEmain.RGB_SHIFT;
	public static final int Green2 = ((LoBEmain.NUM_RGB-1)*3/4)*LoBEmain.RGB_SHIFT;
	public static final int Blue2 = FBlue*3/4;
	public static final int Gray2 = Red2 + Green2 + Blue2;
	public static final int Red3 = ((LoBEmain.NUM_RGB-1)*5/8)*LoBEmain.RGB_SHIFT*LoBEmain.RGB_SHIFT;
	public static final int Green3 = ((LoBEmain.NUM_RGB-1)*5/8)*LoBEmain.RGB_SHIFT;
	public static final int Blue3 = FBlue*5/8;
	public static final int Gray3 = Red3 + Green3 + Blue3;
	public static final int Red4 = ((LoBEmain.NUM_RGB-1)/2)*LoBEmain.RGB_SHIFT*LoBEmain.RGB_SHIFT;
	public static final int Green4 = ((LoBEmain.NUM_RGB-1)/2)*LoBEmain.RGB_SHIFT;
	public static final int Blue4 = FBlue/2;
	public static final int Gray4 = Red4 + Green4 + Blue4;
	public static final int Purple = Red4 + Blue4;
	public static final int HYellow = Red4 + Green4;
	public static final int HCyan = Blue4 + Green4;
	public static final int Orange = FRed + Green4;
	public static final int Red5 = ((LoBEmain.NUM_RGB-1)*3/8)*LoBEmain.RGB_SHIFT*LoBEmain.RGB_SHIFT;
	public static final int Green5 = ((LoBEmain.NUM_RGB-1)*3/8)*LoBEmain.RGB_SHIFT;
	public static final int Blue5 = FBlue*3/8;
	public static final int Gray5 = Red5 + Green5 + Blue5;
	public static final int Red6 = ((LoBEmain.NUM_RGB-1)/4)*LoBEmain.RGB_SHIFT*LoBEmain.RGB_SHIFT;
	public static final int Green6 = ((LoBEmain.NUM_RGB-1)/4)*LoBEmain.RGB_SHIFT;
	public static final int Blue6 = Blue4/2;
	public static final int Gray6 = Red6 + Green6 + Blue6;
	public static final int Purple6 = Red6 + Blue6;
	public static final int Yellow6 = Red6 + Green6;
	public static final int Cyan6 = Green6 + Blue6;
	public static final int Red7 = ((LoBEmain.NUM_RGB-1)/8)*LoBEmain.RGB_SHIFT*LoBEmain.RGB_SHIFT;
	public static final int Green7 = ((LoBEmain.NUM_RGB-1)/8)*LoBEmain.RGB_SHIFT;
	public static final int Blue7 = Blue6/2;
	public static final int Yellow7 = Red7 + Green7;
	public static final int Gray7 = Red7 + Green7 + Blue7;
	public static final int Cyan7 = Green7 + Blue7;
	public static final int Purple7 = Red7 + Blue7;
	public static final int LSkin = White - Cyan7 - Blue7;
	public static final int MSkin = Gray4 - Cyan7 - Blue7;
	public static final int DSkin = Gray6 - Cyan7 - Blue7;

	private static final int numRGB = LoBEmain.NUM_RGB;
	private static final int numColors = LoBEmain.NUM_C;
	private static final int RGBShift = LoBEmain.RGB_SHIFT;
	private static final int transparent = LoBEmain.TRANSPARENT;

	public Colors() {

	}

	public static int[] get(int [] inColor) {
		int[] color = new int[numColors];
		for(int i=0; i<numColors; ++i) {
			color[i] += get(inColor[i]);
		}
		return color;
	}

	private static int get(int color) {
		if(color<0) return transparent;
		int red = color/(RGBShift*RGBShift);
		int green = color/RGBShift%RGBShift;
		int blue = color%RGBShift;
		return red*numRGB*numRGB + green*numRGB + blue;
	}
}
