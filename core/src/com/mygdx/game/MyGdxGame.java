package com.mygdx.game;

import java.awt.Point;
import java.util.ArrayList;
import java.util.HashSet;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.StretchViewport;

public class MyGdxGame extends ApplicationAdapter implements InputProcessor {
	
	
	SpriteBatch batch;



	private int appWidth;
	private int appHeight;
	private int boardWidth;
	private int boardHeight;
	private int entityWidth;
	private int entityHeight;

	
	private board b;
	private TextButton play;
	private TextButton reset;
	private TextButton stop;
	
	private TextField bHeight;
	private TextField bWidth;
	private TextField eWidth;
	private TextField eHeight;
	
	
	private Label status;
	private Label aliveLabel;
	
	private Stage stage;
	private OrthographicCamera camera;
	private Skin skin;
	private InputMultiplexer inputMultiplexer;
	
	private boolean[][] placeHolder;

	private boolean playing;
	
	
	private int aliveCells;	
	/*private HashSet<Point> activeCells;
	private ArrayList<Point> toRemove;
	private ArrayList<Point> toAdd;*/
	
	@Override
	public void create () {
	
		//placeHolder = new entity[boardWidth][boardHeight];

		appWidth = 1000;
		appHeight = 1000;
		boardWidth = 1000;
		boardHeight = 800;
		entityWidth = 4;
		entityHeight = 4;
		
		camera= new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		camera.setToOrtho(true, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		batch = new SpriteBatch();
		
		stage = new Stage(new StretchViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));
		skin = new Skin(Gdx.files.internal("uiskin.json"));
		
		play = new TextButton("Play", skin);
		play.setPosition(50, 150);
		stage.addActor(play);
		
		reset = new TextButton("Reset", skin);
		reset.setPosition(50, 100);
		stage.addActor(reset);
		
		stop = new TextButton("Stop", skin);
		stop.setPosition(50, 50);
		stage.addActor(stop);
		
		status = new Label("Status : Stopped", skin);
		status.setPosition(200, 150);
		stage.addActor(status);
		
		aliveLabel = new Label("Alive cells : ", skin);
		aliveLabel.setPosition(200, 100);
		stage.addActor(aliveLabel);
		
		inputMultiplexer = new InputMultiplexer();
		inputMultiplexer.addProcessor(this);
		inputMultiplexer.addProcessor(stage);
		Gdx.input.setInputProcessor(inputMultiplexer);
		b = new board(boardWidth,boardHeight,entityWidth,entityHeight);

		playing = false; //set to false
		
		
		play.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				status.setText("Status : Playing");
				playing = true;
				super.clicked(event, x, y);
			}
			
		});
		
		
		
		reset.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				status.setText("Status : Reset");
				playing = false;
				reset();
				super.clicked(event, x, y);
			}
		});
		
	
		stop.addListener(new ClickListener() {
			
			@Override
			public void clicked(InputEvent event, float x, float y) {
				status.setText("Status : Stopped");
				
				/*boardHeight = 400;
				boardWidth = 400;
				entityWidth =5;
				entityHeight = 10;
				b = new board(boardWidth, boardHeight , entityWidth, entityHeight);
				placeHolder = new boolean[400][400];*/
				
				
				playing = false;
				super.clicked(event, x, y);
			}
		});
		
		placeHolder = new boolean[boardWidth][boardHeight];
		
		aliveCells = 0;
		
		/*activeCells = new HashSet<Point>();
		toRemove = new ArrayList<Point>();
		toAdd = new ArrayList<Point>();*/
		
		
	}

	
	@Override
	public void render () {

		
		
		batch.setProjectionMatrix(camera.combined);
		
		Gdx.gl.glClearColor(0, 0, 0, 0);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		
		stage.act();
		stage.draw();
		batch.begin();

		
		
		

		
		//Attempted solution using active cells only
		/*
		for(Point p: activeCells) {
		
			if(placeHolder[p.x][p.y]==b.liveOrDie(b.getEntityArray()[p.x][p.y], b.checkNeighbours(b.getEntityArray()[p.x][p.y]))) {
				toRemove.add(p);
				
			}
			else {
				placeHolder[p.x][p.y] = b.liveOrDie(b.getEntityArray()[p.x][p.y], b.checkNeighbours(b.getEntityArray()[p.x][p.y]));
				toAdd.add(new Point(p.x,p.y));
			}
			
			//System.out.println(p.x+ " X " + p.y + " Y ");
		}
		
		
	
		for(Point p: toAdd) {
			addActive(p);
		}
		
		
		//System.out.println("ToRemove " + toRemove.size());
		//activeCells.removeAll(toRemove);
		//toRemove.clear();
		toAdd.clear();
		
		if(playing) {
			for(Point p: activeCells) {
				b.getEntityArray()[p.x][p.y].setLiving(placeHolder[p.x][p.y]);
			}
			
		}
		System.out.println("Active cells " + activeCells.size());

		for(int x =0;x<b.getEntityArray().length;x+=entityWidth) {
			for(int y=0;y<b.getEntityArray()[0].length;y+=entityHeight) {
				
	
		
				batch.draw(b.getEntityArray()[x][y].getTexture(), b.getEntityArray()[x][y].getX(), b.getEntityArray()[x][y].getY());
				
			}
		}*/
		
		//Inefficient solution
		for(int x =0;x<b.getEntityArray().length;x+=entityWidth) {
			 
			for(int y=0;y<b.getEntityArray()[0].length;y+=entityHeight) {
				
				
					if(!placeHolder[x][y]&&b.liveOrDie(b.getEntityArray()[x][y], b.checkNeighbours(b.getEntityArray()[x][y]))) {
						aliveCells+=1;
					}
					
					if(placeHolder[x][y]&&!b.liveOrDie(b.getEntityArray()[x][y], b.checkNeighbours(b.getEntityArray()[x][y]))) {
						aliveCells-=1;
					}
				
				placeHolder[x][y] = b.liveOrDie(b.getEntityArray()[x][y], b.checkNeighbours(b.getEntityArray()[x][y]));
				
				
			
			}
		}
		
			
		
			for(int x =0;x<b.getEntityArray().length;x+=entityWidth) {
				for(int y=0;y<b.getEntityArray()[0].length;y+=entityHeight) {
					
					batch.draw(b.getEntityArray()[x][y].getTexture(), b.getEntityArray()[x][y].getX(), b.getEntityArray()[x][y].getY());
					
					
					
					if(playing) {
						b.getEntityArray()[x][y].setLiving(placeHolder[x][y]);
					}
					
				}
			}
			
		aliveLabel.setText("Alive cells : "+ aliveCells);	
	
		
		batch.end();
		
	
	
		
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		for(int x =0;x<b.getEntityArray().length;x+=entityWidth) {
			for(int y=0;y<b.getEntityArray()[0].length;y+=entityHeight) {
				b.getEntityArray()[x][y].dispose();
			}
		}
	}

	@Override
	public void resize(int width, int height) {
		stage.getViewport().update(width, height, true);
		super.resize(width, height);
	}

	@Override
	public boolean keyDown(int keycode) {

		
		return false;
	}


	@Override
	public boolean keyUp(int keycode) {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public boolean keyTyped(char character) {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		//System.out.println(screenX + " x " +  screenY + "y");
		
		int x = (screenX*appWidth)/Gdx.graphics.getWidth();
		int y = (screenY*appHeight)/Gdx.graphics.getHeight();
		
		while(x%entityWidth!=0) {
			x--;
		}
		while(y%entityHeight!=0) {
			y--;
		}

		//entity e = b.getEntities().get(new Point(x,y));
		if(x<boardWidth&&y<boardHeight) {
		entity e = b.getEntityArray()[x][y];
		
			if(e.getLiving()) {
				
				e.setLiving(false);
				//aliveCells-=1;
	
				//activeCells.remove(new Point(x,y));
			}
			else {
				//e.live();
				e.setLiving(true);
				//aliveCells+=1;
				
				//addActive(new Point(x,y));
			}
		}
	
	
		return false;
	}


	
	/*private void addActive(Point p) {
		
		activeCells.add(p);
	
		
		//left middle
		if(p.x>0) {
			activeCells.add(new Point(p.x-entityWidth,p.y));
		}
		
		//left corner

		//placeHolder.y-=entityHeight;

		
		
		if(p.x>0&&p.y>0) {
			activeCells.add(new Point(p.x-entityWidth,p.y-entityHeight));
			
		}
		
		//top

		
		if(p.y>0) {
			activeCells.add(new Point(p.x,p.y-entityHeight));
			//entities.get(neighbour).setLiving(true);
		}
		
		//right corner

		
		if(p.x<boardWidth-entityWidth&&p.y>0) {
			activeCells.add(new Point(p.x+entityWidth, p.y-entityHeight));
			//entities.get(neighbour).setLiving(true);
		}
		
		//right middle

		
		if(p.x<boardWidth-entityWidth) {
			
			activeCells.add(new Point(p.x+entityWidth,p.y));
			//entities.get(neighbour).setLiving(true);
			
		}
		
		//right bottom

		
		if(p.x<boardWidth-entityWidth&&p.y<boardHeight-entityHeight) {
			activeCells.add(new Point(p.x+entityWidth,p.y+entityHeight));
			//entities.get(neighbour).setLiving(true);
			
		}
		

		
		//bottom
		if(p.y<boardHeight-entityHeight) {
			activeCells.add(new Point(p.x,p.y+entityHeight));
			//entities.get(neighbour).setLiving(true);
		}
		

		//bottom left
		if(p.x>0&&p.y<boardHeight-entityHeight) {
			activeCells.add(new Point(p.x-entityWidth,p.y+entityHeight));
			//entities.get(neighbour).setLiving(true);
			
		}
	}*/
	
	
	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public boolean scrolled(int amount) {
		// TODO Auto-generated method stub
		return false;
	}
	
	public void reset() {
		for(int x =0;x<b.getEntityArray().length;x+=entityWidth) {
			for(int y=0;y<b.getEntityArray()[0].length;y+=entityHeight) {
				b.getEntityArray()[x][y].setLiving(false);
				
			}
			
		}
		
	}
	
}
