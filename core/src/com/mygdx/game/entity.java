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

	//Pixmaps and textures for entity colouring
	private final Pixmap aliveP;
	//private final Pixmap deadP;
	
	private final Texture alive;
	//private final Texture dead;
	
	//x and y coordinates for positioning on the board
	private final int x;
	private final int y;
	//state of the entity
	private boolean living;
	
	//Constructor sets the position and size. Initialises the entity as dead.
	public entity(int xCo, int yCo, int width, int height) {
		aliveP = new Pixmap( width, height, Format.RGBA8888 );
		aliveP.setColor( 0, 1, 1, 0.75f );
		aliveP.fill();
		//deadP = new Pixmap( width, height, Format.RGBA8888 );
		//deadP.setColor( 1, 0, 0, 0.5f);
		//deadP.fill();
		alive = new Texture(aliveP);
		//dead = new Texture(deadP);
		x = xCo;
		y = yCo;
		living = false;
		
		

	}
	

	//Returns the texture dependent on the state of the cell
	public Texture getTexture() {
		//if(living) {
			return alive;
		//}
		/*else {
			return dead;
		}*/	
		
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
	
	//Disposes of resources. Call when the app closes
	public void dispose() {
		aliveP.dispose();
		//deadP.dispose();
		alive.dispose();
		//dead.dispose();
	}
	
}
