package com.mygdx.game;

import java.awt.Point;
import java.util.HashMap;

import com.badlogic.gdx.scenes.scene2d.Actor;

public class board extends Actor{

	

	private HashMap<Point,entity> entities;

	
	
	public board(int width, int height, int entityWidth, int entityHeight) {
		
			
		
		
		
		entities = new HashMap<Point, entity>();
		for(int i = 0;i<width;i+=entityWidth) {
			for(int j = 0;j<=height;j+=entityHeight) {
				entities.put(new Point(i, j),new entity(i, j,entityWidth,entityHeight));	
			}
			
		}

	}
	
	
	
	
	
	public void setAlive(int x, int y) {
		
		
	}
	
	
	
	

	
	
	public HashMap<Point, entity> getEntities() {
		return entities;
	}
	
	
	
}
