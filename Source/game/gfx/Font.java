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
public class Font {

	private static String chars = "?;:'\"[]~        " + "!@#$%^&*()-=+/,.wxyz1234567890  " + "ghijklmnopqrstuvQRSTUVWXYZabcdefABCDEFGHIJKLMNOP";

	public static void render(String msg, Screen screen, int x, int y, int[] color, int scale) {
		int i = 0;
		for(char c : msg.toCharArray()) {
			int charIndex = chars.indexOf(c);
			switch(c) {
				case 'g':
				case 'j':
				case 'p':
				case 'q':
				case 'y':
				case ',':
				case '~':
				case '*':
				case ';':
					y += 6;
					screen.render(x + (12*i++), y, charIndex + 10 * LoBEmain.TILES_PER_ROW, color, 0x00, scale);
					y -= 6;
					break;
				default:
					screen.render(x + (12*i++), y, charIndex + 10 * LoBEmain.TILES_PER_ROW, color, 0x00, scale);
					break;
			}
		}
	}
}
