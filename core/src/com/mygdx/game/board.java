package com.mygdx.game;

import java.awt.Point;
import java.util.HashMap;

import com.badlogic.gdx.scenes.scene2d.Actor;

public class board extends Actor{

	


	
	
	private entity[][] entities;
	
	
	public board(int width, int height, int entityWidth, int entityHeight) {
		
			
		entities = new entity[width][height];
		//try with array instead. Hashmap doesnt loop through in order.
		for(int x = 0;x<width;x+=entityWidth) {
			for(int y = 0;y<height;y+=entityHeight) {
				entities[x][y]= new entity(x, y, entityWidth, entityHeight);
			}
		}
		
	/*	
		entities = new HashMap<Point, entity>();
		for(int i = 0;i<width;i+=entityWidth) {
			for(int j = 0;j<=height;j+=entityHeight) {
				entities.put(new Point(i, j),new entity(i, j,entityWidth,entityHeight));	
			}
			
		}
	
		neighbour = new Point(0, 0);
		*/
	
	}
	
	
	

	
	public void setAlive(int x, int y) {
		
		
	}
	
	
	public int checkNeighbours(entity e) {
		int liveNeighbours = 0;
		
		//left middle
		int neighbourx = e.getX();
		int neighboury = e.getY();
		
		if(e.getX()>0&&entities[neighbourx-5][neighboury].getLiving()) {
			liveNeighbours++;
			//entities[neighbourx][neighboury].setLiving(true);
		}
		
		//left corner

		
		if(e.getX()>0&&e.getY()>0&&entities[neighbourx-5][neighboury-5].getLiving()) {
			liveNeighbours++;
			
		}
		
		//top

		if(e.getY()>0&&entities[neighbourx][neighboury-5].getLiving()) {
			liveNeighbours++;
			//entities.get(neighbour).setLiving(true);
		}
		
		//right corner

		if(e.getX()<595&&e.getY()>0&&entities[neighbourx+5][neighboury-5].getLiving()) {
			liveNeighbours++;
			//entities.get(neighbour).setLiving(true);
		}
		
		//right middle

		if(e.getX()<595&&entities[neighbourx+5][neighboury].getLiving()) {
			liveNeighbours++;
			//entities.get(neighbour).setLiving(true);
			
		}
		
		//right bottom


		if(e.getX()<595&&e.getY()<595&&entities[neighbourx+5][neighboury+5].getLiving()) {
			liveNeighbours++;
			//entities.get(neighbour).setLiving(true);
			
		}
		
		
		//bottom
	
		if(e.getY()<595&&entities[neighbourx][neighboury+5].getLiving()) {
			liveNeighbours++;
			//entities.get(neighbour).setLiving(true);
			
		}
		
		//bottom left
		if(e.getX()>0&&e.getY()<595&&entities[neighbourx-5][neighboury+5].getLiving()) {
			liveNeighbours++;
			//entities.get(neighbour).setLiving(true);
			
		}
		
		
		return liveNeighbours;
	}
	

	//Doesnt work
	public boolean liveOrDie(entity e, int liveNeighbours) {
		
		if(liveNeighbours==3 || (liveNeighbours==2&&e.getLiving())) {
				return true;
		}
		else{
			return false;
		}
		
		
	}
	

	
	public entity[][] getEntityArray() {
		return entities;
	}
	
}
