package com.mygdx.game;

import java.awt.Point;
import java.util.HashSet;
import java.util.Set;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.StretchViewport;

/**
 * 
 * @author Alex
 * 
 * Main game class renders the board, entities and controls.
 * Updates the state of the entities.
 * Controls input.
 *
 */
public class MyGdxGame extends ApplicationAdapter implements InputProcessor {
	
	
	private SpriteBatch batch;

	//app attributes
	private int appWidth,
	appHeight;

	
	private board b;
	
	//GUI buttons and fields
	private TextButton play
	,reset
	,stop
	,apply
	,quit;
	
	private TextField
	eWidth,
	eHeight;
		
	private Label
	patternLabel,
	eWidthLabel,
	eHeightLabel,
	status,
	aliveLabel,
	error;
	
	private SelectBox<String> patternSelect;
	
	private Stage stage;
	private OrthographicCamera camera;
	private Skin skin;
	private InputMultiplexer inputMultiplexer;
	
	
	//PlaceHolder to store the state of each entity. Dead or alive.
	private boolean[][] placeHolder;
	//Whether the game is paused or running
	private boolean playing;
	
	
	private String aliveString;
	private int aliveCells;	
	
	
	private Set<Point> newActiveCells;
	
	private Pixmap border;
	private Texture borderTexture;
	
	@Override
	public void create () {
	
	

		appWidth = 800;
		appHeight = 800;
	
		
		
		
		aliveString = "Alive cells : ";
		
		camera= new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		camera.setToOrtho(true, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		batch = new SpriteBatch();
		
		stage = new Stage(new StretchViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));
		skin = new Skin(Gdx.files.internal("uiskin.json"));
		
		play = new TextButton("Play", skin);
		play.setPosition(50, 155);
		stage.addActor(play);
		
		reset = new TextButton("Reset", skin);
		reset.setPosition(50, 120);
		stage.addActor(reset);
		
		stop = new TextButton("Stop", skin);
		stop.setPosition(50, 85);
		stage.addActor(stop);
		
		quit = new TextButton("Quit", skin);
		quit.setPosition(50, 50);
		stage.addActor(quit);
		
		status = new Label("Status : Reset", skin);
		status.setPosition(125, 155);
		stage.addActor(status);
		
		aliveLabel = new Label(aliveString, skin);
		aliveLabel.setPosition(125, 120);
		stage.addActor(aliveLabel);
		
	
		
	

		eWidth = new TextField("", skin);
		eWidth.setPosition(600, 120);
		eWidth.setMessageText("Cell Width");
		stage.addActor(eWidth);
		
		eHeight = new TextField("", skin);
		eHeight.setPosition(600, 85);
		eHeight.setMessageText("Cell Height");
		stage.addActor(eHeight);
		
	
		
		apply = new TextButton("Apply", skin);
		apply.setPosition(600, 15);
		stage.addActor(apply);
		
		error = new Label("Invalid Cell inputs", skin);
		error.setVisible(false);
		error.setPosition(650, 15);
		error.setColor(Color.RED);
		stage.addActor(error);
		
		
		
		
		eWidthLabel = new Label("Cell Width(Max = 10, Min = 1)", skin);
		eWidthLabel.setPosition(350,120);
		stage.addActor(eWidthLabel);
		
		eHeightLabel = new Label("Cell Height(Max = 10, Min = 1)", skin);
		eHeightLabel.setPosition(350,85);
		stage.addActor(eHeightLabel);
		
		patternLabel = new Label("Pattern", skin);
		patternLabel.setPosition(350, 50);
		stage.addActor(patternLabel);
		
		patternSelect = new SelectBox<String>(skin);
		patternSelect.setItems("ShowCase", "Quad Blinker", "Faces");
		patternSelect.setPosition(600, 50);
		patternSelect.setSize(150, 30);
		stage.addActor(patternSelect);
		
		inputMultiplexer = new InputMultiplexer();
		inputMultiplexer.addProcessor(this);
		inputMultiplexer.addProcessor(stage);
		Gdx.input.setInputProcessor(inputMultiplexer);
		b = new board(800,600,2,2);

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
				//status.setText("Status : Reset");
				//playing = false;
				reset();
				super.clicked(event, x, y);
			}
		});
		
	
		stop.addListener(new ClickListener() {
			
			@Override
			public void clicked(InputEvent event, float x, float y) {
				status.setText("Status : Stopped");
				
				
				
				playing = false;
				super.clicked(event, x, y);
			}
		});
		
		
		apply.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				
				error.setVisible(false);
				validate(eWidth.getText(),eHeight.getText());
				
				
				
				
				super.clicked(event, x, y);
			}

			
		});
		
		quit.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				Gdx.app.exit();
				super.clicked(event, x, y);
			}
			
		});
		
		placeHolder = new boolean[b.getBoardWidth()][b.getBoardHeight()];
		
		aliveCells = 0;
		
	
		newActiveCells = new HashSet<Point>();

		border = new Pixmap(b.getBoardWidth(), 5, Format.RGBA8888 );
		border.setColor( 1, 1, 1, 0.5f);
		border.fill();
		borderTexture = new Texture(border);
		addPattern(patternSelect.getSelected());
	
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
		
		for(Point p: newActiveCells) {
		
			/*if(placeHolder[p.x][p.y]==b.liveOrDie(b.getEntityArray()[p.x][p.y], b.checkNeighbours(b.getEntityArray()[p.x][p.y]))) {
				toRemove.add(p);
				
			}
			else {
				placeHolder[p.x][p.y] = b.liveOrDie(b.getEntityArray()[p.x][p.y], b.checkNeighbours(b.getEntityArray()[p.x][p.y]));
				toAdd.add(new Point(p.x,p.y));
			}*/
			
			
			if(placeHolder[p.x][p.y]!=b.liveOrDie(b.getEntityArray()[p.x][p.y], b.checkNeighbours(b.getEntityArray()[p.x][p.y]))) {
				placeHolder[p.x][p.y] =!placeHolder[p.x][p.y];
				
			}
			
			//System.out.println(p.x+ " X " + p.y + " Y ");
		}
		
		
	
		/*for(Point p: toAdd) {
			addActive(p);
		}*/
		
		
		//System.out.println("ToRemove " + toRemove.size());
		//activeCells.removeAll(toRemove);
		//toRemove.clear();
		//toAdd.clear();
		
		/*if(playing) {
			for(Point p: activeCells) {
				b.getEntityArray()[p.x][p.y].setLiving(placeHolder[p.x][p.y]);
			}
			
		}*/
		//System.out.println("Active cells " + activeCells.size());
		
		newActiveCells.clear();
		
		for(int x =0;x<b.getEntityArray().length;x+=b.getEntityWidth()) {
			for(int y=0;y<b.getEntityArray()[0].length;y+=b.getEntityHeight()) {
				
				//changedCell = b.getEntityArray()[x][y].getLiving();
				
				if(playing) {
					b.getEntityArray()[x][y].setLiving(placeHolder[x][y]);
				}
				
				//try only drawing cells that have changed
			
				//batch.draw(b.getEntityArray()[x][y].getTexture(), b.getEntityArray()[x][y].getX(), b.getEntityArray()[x][y].getY());
				
				
				if(b.getEntityArray()[x][y].getLiving()){
					batch.draw(b.getEntityArray()[x][y].getTexture(), b.getEntityArray()[x][y].getX(), b.getEntityArray()[x][y].getY());
					
					aliveCells++;
					addActive(new Point(x,y));
				}
				
				
				
			}
		}
		
		
		
			
		batch.draw(borderTexture, 0, b.getBoardHeight());
		
		
		
		//System.out.println(newActiveCells.size());
		
		//Inefficient solution
		/*
		//Uses the placeholder to store the next entity state dependent on the checkNeighbours method
		for(int x =0;x<b.getEntityArray().length;x+=b.getEntityWidth()) {
			 
			for(int y=0;y<b.getEntityArray()[0].length;y+=b.getEntityHeight()) {
				
					//If the placeHolder stored state is false and liveOrDie results with true then aliveCells is incremented
					if(!placeHolder[x][y]&&b.liveOrDie(b.getEntityArray()[x][y], b.checkNeighbours(b.getEntityArray()[x][y]))) {
						aliveCells+=1;
					}
					//PlaceHolder is true and liveOrDie results in false(Dead)
					if(placeHolder[x][y]&&!b.liveOrDie(b.getEntityArray()[x][y], b.checkNeighbours(b.getEntityArray()[x][y]))) {
						aliveCells-=1;
					}
				//placeHolder is initialized with the result of liveOrDie
				placeHolder[x][y] = b.liveOrDie(b.getEntityArray()[x][y], b.checkNeighbours(b.getEntityArray()[x][y]));

			
			}
		}
		
			
		//Each entity is drawn to the screen.
			for(int x =0;x<b.getEntityArray().length;x+=b.getEntityWidth()) {
				for(int y=0;y<b.getEntityArray()[0].length;y+=b.getEntityHeight()) {
					
					batch.draw(b.getEntityArray()[x][y].getTexture(), b.getEntityArray()[x][y].getX(), b.getEntityArray()[x][y].getY());
					
					
					
					if(playing) {
						b.getEntityArray()[x][y].setLiving(placeHolder[x][y]);
					}
					
				}
			}
		*/
		aliveLabel.setText(aliveString + aliveCells);	
		aliveCells = 0;
		
		batch.end();
		
	
	
		
	}
	
	@Override
	public void dispose () {
		borderTexture.dispose();
		batch.dispose();
		disposeCells();
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
		
		controls(screenX,screenY);
	
	
		return false;
	}


	
	private void addActive(Point p) {
		
		newActiveCells.add(p);
	
		
		//left middle
		if(p.x>0) {
			newActiveCells.add(new Point(p.x-b.getEntityWidth(),p.y));
		}
		
		//left corner

		//placeHolder.y-=entityHeight;

		
		
		if(p.x>0&&p.y>0) {
			newActiveCells.add(new Point(p.x-b.getEntityWidth(),p.y-b.getEntityHeight()));
			
		}
		
		//top

		
		if(p.y>0) {
			newActiveCells.add(new Point(p.x,p.y-b.getEntityHeight()));
			//entities.get(neighbour).setLiving(true);
		}
		
		//right corner

		
		if(p.x<b.getBoardWidth()-b.getEntityWidth()&&p.y>0) {
			newActiveCells.add(new Point(p.x+b.getEntityWidth(), p.y-b.getEntityHeight()));
			//entities.get(neighbour).setLiving(true);
		}
		
		//right middle

		
		if(p.x<b.getBoardWidth()-b.getEntityWidth()) {
			
			newActiveCells.add(new Point(p.x+b.getEntityWidth(),p.y));
			//entities.get(neighbour).setLiving(true);
			
		}
		
		//right bottom

		
		if(p.x<b.getBoardWidth()-b.getEntityWidth()&&p.y<b.getBoardHeight()-b.getEntityHeight()) {
			newActiveCells.add(new Point(p.x+b.getEntityWidth(),p.y+b.getEntityHeight()));
			//entities.get(neighbour).setLiving(true);
			
		}
		

		
		//bottom
		if(p.y<b.getBoardHeight()-b.getEntityHeight()) {
			newActiveCells.add(new Point(p.x,p.y+b.getEntityHeight()));
			//entities.get(neighbour).setLiving(true);
		}
		

		//bottom left
		if(p.x>0&&p.y<b.getBoardHeight()-b.getEntityHeight()) {
			newActiveCells.add(new Point(p.x-b.getEntityWidth(),p.y+b.getEntityHeight()));
			//entities.get(neighbour).setLiving(true);
			
		}
	}
	
	
	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		controls(screenX,screenY);

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
		newActiveCells.clear();
		for(int x =0;x<b.getEntityArray().length;x+=b.getEntityWidth()) {
			for(int y=0;y<b.getEntityArray()[0].length;y+=b.getEntityHeight()) {
				b.getEntityArray()[x][y].setLiving(false);
				placeHolder[x][y] = false; 
				
			}
			
		}
		playing = false;
		aliveCells = 0;
		status.setText("Status : Reset");
	}
	
	//Validates the input to the board and entity dimension textfields
	private void validate(String entWidth, String entHeight) {
	
	
		int eWidth;
		int eHeight;
		
		if(entWidth.equals("")&&entHeight.equals("")) {
			   error.setVisible(false);
		}
		else {
		   try {

		        eWidth = Integer.parseInt( entWidth );
		        eHeight = Integer.parseInt( entHeight );
		        
		        if(eWidth<=10&&eWidth>=1&&eHeight<=10&&eHeight>=1) {
		        	disposeCells();
		        	b = new board(800, 600, eWidth, eHeight);
					placeHolder = new boolean[800][600];
					//status.setText("Status : Reset");
					//playing = false;
					//reset();
					//aliveCells = 0;
					
		        }
		        else {
		        	error.setVisible(true);
		        }
		        
		    }
		    catch( NumberFormatException e ) {
		    	error.setVisible(true);
		    }
		}
		reset();
		addPattern(patternSelect.getSelected());
		
	}
	
	
	private void addPattern(String pattern) {
		
		//need to set AliveCells and add to newActiveCells. Just use addActiveMethod
		
		
		int xCoordinates[] = {b.getBoardWidth()-b.getBoardWidth()/4,0+b.getBoardWidth()/4};
		int yCoordinates[] = {b.getBoardHeight()-b.getBoardHeight()/4,0+b.getBoardHeight()/4};
		

		for(int counter = 0; counter<2;counter++) {
			while(xCoordinates[counter]%b.getEntityWidth()!=0) {
				xCoordinates[counter]--;
			}
			while(yCoordinates[counter]%b.getEntityHeight()!=0) {
				yCoordinates[counter]--;
			}
		}
		
	
		
		
		/*
		 * Works for a single coord
		 * 
		 * int x = b.getEntityArray().length/2;
		int y = b.getEntityArray()[0].length/2;
		
		while(x%b.getEntityWidth()!=0) {
			x--;
		}
		while(y%b.getEntityHeight()!=0) {
			y--;
		}*/
		
		
		
		switch (pattern) {
		case "ShowCase":
			
			
			for(int x: xCoordinates) {
				for(int y: yCoordinates) {
					addActive(new Point(x-b.getEntityWidth(),y-b.getEntityHeight()));
					addActive(new Point(x-b.getEntityWidth()+b.getEntityWidth(),y-b.getEntityHeight()));
					addActive(new Point(x-b.getEntityWidth()+b.getEntityWidth()*2,y-b.getEntityHeight()));
					
					b.getEntityArray()[x-b.getEntityWidth()][y-b.getEntityHeight()].setLiving(true);	
					b.getEntityArray()[x-b.getEntityWidth()+b.getEntityWidth()][y-b.getEntityHeight()].setLiving(true);
					b.getEntityArray()[x-b.getEntityWidth()+b.getEntityWidth()*2][y-b.getEntityHeight()].setLiving(true);
					
					////
					
					addActive(new Point(x-b.getEntityWidth()-b.getEntityWidth(),y-b.getEntityHeight()-b.getEntityHeight()));
					addActive(new Point(x-b.getEntityWidth()-b.getEntityWidth()*3,y-b.getEntityHeight()-b.getEntityHeight()*2));
					
					
					b.getEntityArray()[x-b.getEntityWidth()-b.getEntityWidth()][y-b.getEntityHeight()-b.getEntityHeight()].setLiving(true);
					b.getEntityArray()[x-b.getEntityWidth()-b.getEntityWidth()*3][y-b.getEntityHeight()-b.getEntityHeight()*2].setLiving(true);
					
					////
					
					addActive(new Point(x-b.getEntityWidth()-b.getEntityWidth()*3,y-b.getEntityHeight()));
					addActive(new Point(x-b.getEntityWidth()-b.getEntityWidth()*4,y-b.getEntityHeight()));
					
					b.getEntityArray()[x-b.getEntityWidth()-b.getEntityWidth()*3][y-b.getEntityHeight()].setLiving(true);
					b.getEntityArray()[x-b.getEntityWidth()-b.getEntityWidth()*4][y-b.getEntityHeight()].setLiving(true);	
					
				}
				
			}
			
			break;
		case "Quad Blinker":
			
			for(int x: xCoordinates) {
				for(int y: yCoordinates) {
					
					addActive(new Point(x-b.getEntityWidth(),y-b.getEntityHeight()));
					addActive(new Point(x-b.getEntityWidth()+b.getEntityWidth(),y-b.getEntityHeight()));
					addActive(new Point(x-b.getEntityWidth()+b.getEntityWidth()*2,y-b.getEntityHeight()));
					
					b.getEntityArray()[x-b.getEntityWidth()][y-b.getEntityHeight()].setLiving(true);	
					b.getEntityArray()[x-b.getEntityWidth()+b.getEntityWidth()][y-b.getEntityHeight()].setLiving(true);
					b.getEntityArray()[x-b.getEntityWidth()+b.getEntityWidth()*2][y-b.getEntityHeight()].setLiving(true);
					
					////
					
					addActive(new Point(x-b.getEntityWidth(),y-b.getEntityHeight()*5));
					addActive(new Point(x-b.getEntityWidth()+b.getEntityWidth(),y-b.getEntityHeight()*5));
					addActive(new Point(x-b.getEntityWidth()+b.getEntityWidth()*2,y-b.getEntityHeight()*5));
					
					b.getEntityArray()[x-b.getEntityWidth()][y-b.getEntityHeight()*5].setLiving(true);	
					b.getEntityArray()[x-b.getEntityWidth()+b.getEntityWidth()][y-b.getEntityHeight()*5].setLiving(true);
					b.getEntityArray()[x-b.getEntityWidth()+b.getEntityWidth()*2][y-b.getEntityHeight()*5].setLiving(true);
					
					////
					
					addActive(new Point(x-b.getEntityWidth()-b.getEntityWidth(),y-b.getEntityHeight()-b.getEntityHeight()));
					addActive(new Point(x-b.getEntityWidth()-b.getEntityWidth(),y-b.getEntityHeight()-b.getEntityHeight()*2));
					addActive(new Point(x-b.getEntityWidth()-b.getEntityWidth(),y-b.getEntityHeight()-b.getEntityHeight()*3));
					
					b.getEntityArray()[x-b.getEntityWidth()-b.getEntityWidth()][y-b.getEntityHeight()-b.getEntityHeight()].setLiving(true);
					b.getEntityArray()[x-b.getEntityWidth()-b.getEntityWidth()][y-b.getEntityHeight()-b.getEntityHeight()*2].setLiving(true);
					b.getEntityArray()[x-b.getEntityWidth()-b.getEntityWidth()][y-b.getEntityHeight()-b.getEntityHeight()*3].setLiving(true);
					
					///
					
					addActive(new Point(x-b.getEntityWidth()+b.getEntityWidth()*3,y-b.getEntityHeight()-b.getEntityHeight()));
					addActive(new Point(x-b.getEntityWidth()+b.getEntityWidth()*3,y-b.getEntityHeight()-b.getEntityHeight()*2));
					addActive(new Point(x-b.getEntityWidth()+b.getEntityWidth()*3,y-b.getEntityHeight()-b.getEntityHeight()*3));
					
					b.getEntityArray()[x-b.getEntityWidth()+b.getEntityWidth()*3][y-b.getEntityHeight()-b.getEntityHeight()].setLiving(true);
					b.getEntityArray()[x-b.getEntityWidth()+b.getEntityWidth()*3][y-b.getEntityHeight()-b.getEntityHeight()*2].setLiving(true);
					b.getEntityArray()[x-b.getEntityWidth()+b.getEntityWidth()*3][y-b.getEntityHeight()-b.getEntityHeight()*3].setLiving(true);
					
				}
				
			}
			
			break;
		case "Faces":
			
			for(int x: xCoordinates) {
				for(int y: yCoordinates) {
					
					
					
					////
					
					addActive(new Point(x-b.getEntityWidth(),y-b.getEntityHeight()-b.getEntityHeight()));
					addActive(new Point(x-b.getEntityWidth(),y-b.getEntityHeight()-b.getEntityHeight()*2));
					addActive(new Point(x-b.getEntityWidth(),y-b.getEntityHeight()-b.getEntityHeight()*3));
					
					b.getEntityArray()[x-b.getEntityWidth()][y-b.getEntityHeight()-b.getEntityHeight()].setLiving(true);
					b.getEntityArray()[x-b.getEntityWidth()][y-b.getEntityHeight()-b.getEntityHeight()*2].setLiving(true);
					b.getEntityArray()[x-b.getEntityWidth()][y-b.getEntityHeight()-b.getEntityHeight()*3].setLiving(true);
					
					///
					
					addActive(new Point(x-b.getEntityWidth()+b.getEntityWidth()*2,y-b.getEntityHeight()-b.getEntityHeight()));
					addActive(new Point(x-b.getEntityWidth()+b.getEntityWidth()*2,y-b.getEntityHeight()-b.getEntityHeight()*2));
					addActive(new Point(x-b.getEntityWidth()+b.getEntityWidth()*2,y-b.getEntityHeight()-b.getEntityHeight()*3));
					
					b.getEntityArray()[x-b.getEntityWidth()+b.getEntityWidth()*2][y-b.getEntityHeight()-b.getEntityHeight()].setLiving(true);
					b.getEntityArray()[x-b.getEntityWidth()+b.getEntityWidth()*2][y-b.getEntityHeight()-b.getEntityHeight()*2].setLiving(true);
					b.getEntityArray()[x-b.getEntityWidth()+b.getEntityWidth()*2][y-b.getEntityHeight()-b.getEntityHeight()*3].setLiving(true);
					
					///
					
					addActive(new Point(x-b.getEntityWidth()+b.getEntityWidth(),y-b.getEntityHeight()-b.getEntityHeight()*4));
					b.getEntityArray()[x-b.getEntityWidth()+b.getEntityWidth()][y-b.getEntityHeight()-b.getEntityHeight()*4].setLiving(true);
				}
				
			}
			
			break;
		default:
			break;
		}
		
		
	}
	
	
	private void disposeCells() {
		for(int x =0;x<b.getEntityArray().length;x+=b.getEntityWidth()) {
			for(int y=0;y<b.getEntityArray()[0].length;y+=b.getEntityHeight()) {
				b.getEntityArray()[x][y].dispose();
			}
		}
	}
	
	//Used for board input. Sets a dead entity to alive or an alive entity to dead.
	private void controls(int screenX,int screenY) {
		
		
		//Variables dependent on the app size
		int x = (screenX*appWidth)/Gdx.graphics.getWidth();
		int y = (screenY*appHeight)/Gdx.graphics.getHeight();
		
		
		
		while(x%b.getEntityWidth()!=0) {
			x--;
		}
		while(y%b.getEntityHeight()!=0) {
			y--;
		}

		
		if(x<b.getBoardWidth()&&y<b.getBoardHeight()&&x>=0&&y>=0) {
			entity e = b.getEntityArray()[x][y];
		
			if(e.getLiving()) {
				
				e.setLiving(false);
				newActiveCells.remove(new Point(x,y));
			}
			else {
			
				e.setLiving(true);
				newActiveCells.add(new Point(x, y));
			}
		}
	}
	
}
