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

	public static int FRed = (LoBEmain.NUM_RGB-1)*100;
	public static int FGreen = (LoBEmain.NUM_RGB-1)*10;
	public static int FBlue = LoBEmain.NUM_RGB-1;
	public static int White = FRed + FGreen + FBlue;
	public static int LSkin = FRed + FGreen-10 + FBlue-2;
	public static int FPurple = FRed + FBlue;
	public static int FYellow = FRed + FGreen;
	public static int FCyan = FBlue + FGreen;
	public static int HRed = ((LoBEmain.NUM_RGB-1)/2)*100;
	public static int HGreen = ((LoBEmain.NUM_RGB-1)/2)*10;
	public static int HBlue = FBlue/2;
	public static int HGray = HRed + HGreen + HBlue;
	public static int MSkin = HRed + HGreen-10 + HBlue-2;
	public static int HPurple = HRed + HBlue;
	public static int HYellow = HRed + HGreen;
	public static int HCyan = HBlue + HGreen;
	public static int DRed = ((LoBEmain.NUM_RGB-1)/4)*100;
	public static int DGreen = ((LoBEmain.NUM_RGB-1)/4)*10;
	public static int DBlue = HBlue/2;
	public static int DGray = DRed + DGreen + DBlue;
	public static int DSkin = DRed + DGreen-10 + DBlue-2;
	public static int DPurple = DRed + DBlue;
	public static int DYellow = DRed + DGreen;
	public static int DCyan = DGreen + DBlue;

	private static int numRGB = LoBEmain.NUM_RGB;
	private static int numColors = LoBEmain.NUM_C;
	private static int transparent = LoBEmain.TRANSPARENT;

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
		int red = color/100%10;
		int green = color/10%10;
		int blue = color%10;
		return red*numRGB*numRGB + green*numRGB + blue;
	}
}
