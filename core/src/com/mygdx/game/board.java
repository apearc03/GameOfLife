package com.mygdx.game;


public class board {

	


	
	
	private entity[][] entities;
	
	private int entityWidth;
	private int entityHeight;
	
	private int boardWidth;
	private int boardHeight;
	
	public board(int width, int height, int entityW, int entityH) {
		
		entityWidth = entityW;
		entityHeight = entityH;
		
		boardWidth = width;
		boardHeight = height;
		
		entities = new entity[width][height];
		//try with array instead. Hashmap doesnt loop through in order.
		for(int x = 0;x<width;x+=entityWidth) {
			for(int y = 0;y<height;y+=entityHeight) {
				entities[x][y]= new entity(x, y, entityWidth, entityHeight);
			}
		}
		

	
	}
	
	
	
	
	
	//improve with checks for periodic checks of liveNeighbours
	public int checkNeighbours(entity e) {
		int liveNeighbours = 0;
		
		
		int neighbourx = e.getX();
		int neighboury = e.getY();
		//left middle
		if(e.getX()>0&&entities[neighbourx-entityWidth][neighboury].getLiving()) {
			liveNeighbours++;
			//entities[neighbourx][neighboury].setLiving(true);
		}
		
		//left corner

		
		if(e.getX()>0&&e.getY()>0&&entities[neighbourx-entityWidth][neighboury-entityHeight].getLiving()) {
			liveNeighbours++;
			
		}
		
		//top

		if(e.getY()>0&&entities[neighbourx][neighboury-entityHeight].getLiving()) {
			liveNeighbours++;
			//entities.get(neighbour).setLiving(true);
		}
		
		//right corner

		if(e.getX()<boardWidth-entityWidth&&e.getY()>0&&entities[neighbourx+entityWidth][neighboury-entityHeight].getLiving()) {
			liveNeighbours++;
			//entities.get(neighbour).setLiving(true);
		}
		
		//right middle

		if(e.getX()<boardWidth-entityWidth&&entities[neighbourx+entityWidth][neighboury].getLiving()) {
			liveNeighbours++;
			//entities.get(neighbour).setLiving(true);
			
		}
		
		//right bottom


		if(e.getX()<boardWidth-entityWidth&&e.getY()<boardHeight-entityHeight&&entities[neighbourx+entityWidth][neighboury+entityHeight].getLiving()) {
			liveNeighbours++;
			//entities.get(neighbour).setLiving(true);
			
		}
		
		
		//bottom
	
		if(e.getY()<boardHeight-entityHeight&&entities[neighbourx][neighboury+entityHeight].getLiving()) {
			liveNeighbours++;
			//entities.get(neighbour).setLiving(true);
			
		}
		
		//bottom left
		if(e.getX()>0&&e.getY()<boardHeight-entityHeight&&entities[neighbourx-entityWidth][neighboury+entityHeight].getLiving()) {
			liveNeighbours++;
			//entities.get(neighbour).setLiving(true);
			
		}
		
		
		return liveNeighbours;
	}
	


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
