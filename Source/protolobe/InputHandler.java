/*
 * Copyright (c) 2015. Chris Ferris
 *  This file is licensed under the "GPL V2" license.
 *  Refer to LICENSE for distribution details.
 */

package protolobe;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Created by Christopher on 7/3/2015.
 */
public class InputHandler implements KeyListener {

	public InputHandler(LoBEmain game) {game.addKeyListener(this);}

	public class Key {
		private boolean pressed = false;
		private int numTimesPressed = 0;
		public boolean isPressed() {return pressed;}

		public void toggle(boolean isPressed) {
			pressed = isPressed;
			if(isPressed) ++numTimesPressed;
		}

		public int getNumTimesPressed() {return numTimesPressed;}
	}

//	public List<Key>keys = new ArrayList<Key>();

	public Key up     = new Key();
	public Key down   = new Key();
	public Key left   = new Key();
	public Key right  = new Key();
	public Key attack = new Key();

	/**
	 * Invoked when a key has been typed.
	 * See the class description for {@link KeyEvent} for a definition of
	 * a key typed event.
	 *
	 * @param e
	 */
	@Override
	public void keyTyped(KeyEvent e) {

	}

	public void toggleKey(int keyCode, boolean isPressed) {
		if ( keyCode == KeyEvent.VK_W | keyCode == KeyEvent.VK_UP ) { up.toggle(isPressed);}
		if ( keyCode == KeyEvent.VK_S | keyCode == KeyEvent.VK_DOWN ) { down.toggle(isPressed);}
		if ( keyCode == KeyEvent.VK_A | keyCode == KeyEvent.VK_LEFT ) { left.toggle(isPressed);}
		if ( keyCode == KeyEvent.VK_D | keyCode == KeyEvent.VK_RIGHT ) { right.toggle(isPressed);}
		if ( keyCode == KeyEvent.VK_F ) { attack.toggle(isPressed);}
	}

	/**
	 * Invoked when a key has been pressed.
	 * See the class description for {@link KeyEvent} for a definition of
	 * a key pressed event.
	 *
	 * @param e
	 */
	@Override
	public void keyPressed(KeyEvent e) {
		toggleKey(e.getKeyCode(), true);
	}

	/**
	 * Invoked when a key has been released.
	 * See the class description for {@link KeyEvent} for a definition of
	 * a key released event.
	 *
	 * @param e
	 */
	@Override
	public void keyReleased(KeyEvent e) {
		toggleKey(e.getKeyCode(), false);
	}
}
