package com.mygdx.game;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;

public class entity {

	
	private final Pixmap aliveP;
	private final Pixmap deadP;
	
	private final Texture alive;
	private final Texture dead;
	
	private final int x;
	private final int y;
	private boolean living;
	
	
	//if clicked set to living or dead.
	
	public entity(int xCo, int yCo, int width, int height) {
		aliveP = new Pixmap( width, height, Format.RGBA8888 );
		aliveP.setColor( 0, 1, 1, 0.75f );
		aliveP.fill();
		deadP = new Pixmap( width, height, Format.RGBA8888 );
		deadP.setColor( 1, 0, 0, 0.5f);
		deadP.fill();
		alive = new Texture(aliveP);
		dead = new Texture(deadP);
		x = xCo;
		y = yCo;
		living = false;
		
		
		
	}
	

	public void die() {
		
		living = false;
	
	}
	

	public void live() {
		living = true;
	}

	public Texture getTexture() {
		if(living) {
			return alive;
		}
		else {
			return dead;
		}	
		
	}
	
	
	public void setLiving(boolean living) {
		this.living = living;
	}
	
	public boolean getLiving() {
		return living;
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	
	public void dispose() {
		aliveP.dispose();
		deadP.dispose();
		alive.dispose();
		dead.dispose();
	}
	
}
