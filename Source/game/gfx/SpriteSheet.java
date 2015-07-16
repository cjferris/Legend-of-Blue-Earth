/*
 * Copyright (c) 2015. Chris Ferris
 *  This file is licensed under the "GPL V2" license.
 *  Refer to LICENSE for distribution details.
 */

package game.gfx;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * Created by Christopher on 7/3/2015.
 */
public class SpriteSheet {
	public String path;
	public int width;
	public int height;
	public int [] pixels;

	public SpriteSheet(String path) {
		BufferedImage image = null;
		try {
			image = ImageIO.read(SpriteSheet.class.getResourceAsStream(path));
		} catch ( IOException e ) {
			e.printStackTrace();
		}

		if(image == null){
			return;
		}

		this.path = path;
		this.width = image.getWidth();
		this.height = image.getHeight();
		pixels = image.getRGB(0, 0, width, height, null, 0, width); // Save image values into pixels array.

		for(int i=0; i<pixels.length; ++i) {
			pixels[i] = (pixels[i] & 0xff)/64;
		}

	}
}
