package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
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
	private Stage stage;
	private OrthographicCamera camera;
	private Skin skin;
	private InputMultiplexer inputMultiplexer;
	
	private boolean[][] placeHolder;

	private boolean playing;
	
	
	@Override
	public void create () {
	
		//placeHolder = new entity[boardWidth][boardHeight];
		
		appWidth = 600;
		appHeight = 800;
		boardWidth = 600;
		boardHeight = 600;
		entityWidth = 5;
		entityHeight = 5;
		
		camera= new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		camera.setToOrtho(true, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		batch = new SpriteBatch();
		
		stage = new Stage(new StretchViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));
		skin = new Skin(Gdx.files.internal("uiskin.json"));
		
		play = new TextButton("Play", skin);
		play.setPosition(250, 50);
		stage.addActor(play);
		
		reset = new TextButton("Reset", skin);
		reset.setPosition(450, 50);
		stage.addActor(reset);
		
		inputMultiplexer = new InputMultiplexer();
		inputMultiplexer.addProcessor(this);
		inputMultiplexer.addProcessor(stage);
		Gdx.input.setInputProcessor(inputMultiplexer);
		b = new board(boardWidth,boardHeight,entityWidth,entityHeight);

		playing = false; //set to false
		
		
		play.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
			
				playing = true;
				super.clicked(event, x, y);
			}
			
		});
		
		reset.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				playing = false;
				super.clicked(event, x, y);
			}
		});
		
	
		
		
		placeHolder = new boolean[boardWidth][boardHeight];
	}

	
	@Override
	public void render () {

		
		
		batch.setProjectionMatrix(camera.combined);
		
		Gdx.gl.glClearColor(0, 0, 0, 0);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		
		stage.act();
		stage.draw();
		batch.begin();

		
		//need to test which values are going to change before actually changing them.
		//multi array of booleans?
	
		//placeHolder = new boolean[boardWidth][boardHeight];

			/*for(Point p: b.getEntities().keySet()) {
				batch.draw(b.getEntities().get(p).getTexture(), b.getEntities().get(p).getX(), b.getEntities().get(p).getY());
			}*/
				
		for(int x =0;x<b.getEntityArray().length;x+=entityWidth) {
			for(int y=0;y<b.getEntityArray().length;y+=entityHeight) {
				placeHolder[x][y] = b.liveOrDie(b.getEntityArray()[x][y], b.checkNeighbours(b.getEntityArray()[x][y]));
				//placeHolder[i][j].setLiving(b.liveOrDie(placeHolder[i][j], b.checkNeighbours(placeHolder[i][j])));
			}
		}
		
			
			//works better
			for(int x =0;x<b.getEntityArray().length;x+=entityWidth) {
				for(int y=0;y<b.getEntityArray().length;y+=entityHeight) {
					
					System.out.println(x + " x" + " y" + y);
					//b.getEntityArray()[x][y].setLiving(placeHolder[x][y].getLiving());
			
					batch.draw(b.getEntityArray()[x][y].getTexture(), b.getEntityArray()[x][y].getX(), b.getEntityArray()[x][y].getY());
					//b.getEntityArray()[x][y].setLiving(b.liveOrDie(b.getEntityArray()[x][y], b.checkNeighbours(b.getEntityArray()[x][y])));
					
					if(playing) {
						b.getEntityArray()[x][y].setLiving(placeHolder[x][y]);
					}
					
				}
			}
			
		
			
				
				
					//if(b.getEntities().get(e).getLiving()){}
				
					
				
					
					/*if(playing) {
						b.getEntities().get(p).setLiving(b.liveOrDie(b.getEntities().get(p), b.checkNeighbours(b.getEntities().get(p))));
						//System.out.println(b.checkNeighbours(b.getEntities().get(new Point(0,0))));
					
					}*/
				
				//b.getEntities().get(new Point(5,5)).live();
			
		//playing = false;
		//batch.draw(b.getEntities().get(new Point(15, 0)).t, b.getEntities().get(new Point(15,0)).x, b.getEntities().get(new Point(15, 0)).y);

		//batch.draw(b.getEntities().get(20).t, b.getEntities().get(20).x, b.getEntities().get(20).y);
		//b.getEntities().get(20).pixmap.drawRectangle(5, 5, 5, 5);
		//b.getEntities().get(20).pixmap.drawPixel(200, 200);
		batch.end();
		
	
	
		
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		for(int x =0;x<b.getEntityArray().length;x+=entityWidth) {
			for(int y=0;y<b.getEntityArray().length;y+=entityHeight) {
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
		
		while(x%5!=0) {
			x--;
		}
		while(y%5!=0) {
			y--;
		}
			
		System.out.println("X COORDINATE: " + x);
		System.out.println("Y COORDINATE: " + y);
		//System.out.println("POINT X" + b.getEntities().get(new Point(x, y)).getX());
		//System.out.println("POINT Y" + b.getEntities().get(new Point(x, y)).getY());
		
		//entity e = b.getEntities().get(new Point(x,y));
		if(x<boardWidth&&y<boardHeight) {
		entity e = b.getEntityArray()[x][y];
		
			if(e.getLiving()) {
				//e.die();
				e.setLiving(false);
			}
			else {
				//e.live();
				e.setLiving(true);
			}
		}
	
	
		return false;
	}


	
	
	
	
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
}
