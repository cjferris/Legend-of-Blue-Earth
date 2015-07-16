/*
 * Copyright (c) 2015. Chris Ferris
 *  This file is licensed under the "GPL V2" license.
 *  Refer to LICENSE for distribution details.
 */

package game.gfx;

/**
 * Created by Christopher on 7/3/2015.
 */
public class Font {

	private static String chars = "abcdefghijklmnopqrstuvwxyz      " + "ABCDEFGHIJKLMNOPQRSTUVWXYZ      " +  "0123456789.,:;'\"!?$%()-=+/     ";

	public static void render(String msg, Screen screen, int x, int y, int color, int scale) {
		int i = 0;
		for(char c : msg.toCharArray()) {
			int charIndex = chars.indexOf(c);
			switch(c) {
				case 'g':
				case 'j':
				case 'p':
				case 'q':
				case 'y':
					y += 3;
					screen.render(x + (8*i++), y, charIndex + 29 * 32, color, 0x00, scale);
					y -= 3;
					break;
				default:
					screen.render(x + (8*i++), y, charIndex + 29 * 32, color, 0x00, scale);
					break;
			}
		}
	}
}
