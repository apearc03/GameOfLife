package com.mygdx.game;



/**
 * 
 * @author Alex Pearce
 * 
 * Class to represent the on screen board containing cell entities.
 *
 */
public class board {

	//Two dimensional array to store every cell entity
	private entity[][] entities;
	
	//Entity and board attributes
	private int entityWidth;
	private int entityHeight;
	private int boardWidth;
	private int boardHeight;
	
	
	//Constructor initializes the board and fills the entity two dimensional array
	public board(int width, int height, int entityW, int entityH) {
		
		entityWidth = entityW;
		entityHeight = entityH;
		
		boardWidth = width;
		boardHeight = height;
		
		entities = new entity[width][height];

		for(int x = 0;x<width;x+=entityWidth) {
			for(int y = 0;y<height;y+=entityHeight) {
				entities[x][y]= new entity(x, y);
			}
		}
		

	
	}
	
	
	
	
	
	//Checks the neighbours of an entity in every direction and returns the live count
	public int checkNeighbours(entity e) {
		int liveNeighbours = 0;
		
		
		int neighbourx = e.getX();
		int neighboury = e.getY();
		//left middle
		if(e.getX()>0&&entities[neighbourx-entityWidth][neighboury].getLiving()) {
			liveNeighbours++;
	
		}
		
		//left corner

		
		if(e.getX()>0&&e.getY()>0&&entities[neighbourx-entityWidth][neighboury-entityHeight].getLiving()) {
			liveNeighbours++;
			
		}
		
		//top

		if(e.getY()>0&&entities[neighbourx][neighboury-entityHeight].getLiving()) {
			liveNeighbours++;
			
		}
		
		//right corner

		if(e.getX()<boardWidth-entityWidth&&e.getY()>0&&entities[neighbourx+entityWidth][neighboury-entityHeight].getLiving()) {
			liveNeighbours++;
		
		}
		
		//right middle

		if(e.getX()<boardWidth-entityWidth&&entities[neighbourx+entityWidth][neighboury].getLiving()) {
			liveNeighbours++;
		
			
		}
		
		//right bottom


		if(e.getX()<boardWidth-entityWidth&&e.getY()<boardHeight-entityHeight&&entities[neighbourx+entityWidth][neighboury+entityHeight].getLiving()) {
			liveNeighbours++;
		
			
		}
		
		
		//bottom
	
		if(e.getY()<boardHeight-entityHeight&&entities[neighbourx][neighboury+entityHeight].getLiving()) {
			liveNeighbours++;
			
			
		}
		
		//bottom left
		if(e.getX()>0&&e.getY()<boardHeight-entityHeight&&entities[neighbourx-entityWidth][neighboury+entityHeight].getLiving()) {
			liveNeighbours++;
			
			
		}
		
		
		return liveNeighbours;
	}
	

	//Returns the state of the entity after evaluating the live Neighbours
	public boolean liveOrDie(entity e, int liveNeighbours) {
		
		if(liveNeighbours==3 || (liveNeighbours==2&&e.getLiving())) {
				return true;
		}
		else{
			return false;
		}
		
		
	}
	

	//Getters
	public entity[][] getEntityArray() {
		return entities;
	}
	
	public int getBoardWidth() {
		return boardWidth;
	}
	
	public int getBoardHeight() {
		return boardHeight;
	}
	
	public int getEntityWidth() {
		return entityWidth;
	}
	
	public int getEntityHeight() {
		return entityHeight;
	}
	
	
}
