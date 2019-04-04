package com.mygdx.game;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;

/**
 * 
 * 
 * @author Alex
 *
 *	Class to represent an entity on the board.
 *  Entities have a size and state. Dead or alive.
 *
 */
public class entity {

	//x and y coordinates for positioning on the board
	private final int x;
	private final int y;
	//state of the entity
	private boolean living;
	
	//Constructor sets the position and size. Initialises the entity as dead.
	public entity(int xCo, int yCo, int width, int height) {
		x = xCo;
		y = yCo;
		living = false;



	}

	//Getters and setters
	public boolean getLiving() {
		return living;
	}
	
	public void setLiving(boolean living) {
		this.living = living;
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}


}
