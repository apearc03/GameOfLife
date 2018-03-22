package com.mygdx.game;

import java.awt.Point;
import java.util.Random;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.viewport.StretchViewport;

public class MyGdxGame extends ApplicationAdapter implements InputProcessor {
	
	
	SpriteBatch batch;



	private int appWidth;
	private int appHeight;
	private int boardWidth;
	private int boardHeight;
	

	
	private board b;
	private TextButton text;
	private Stage stage;
	private OrthographicCamera camera;
	private InputMultiplexer inputMultiplexer;
	
	@Override
	public void create () {
	
		appWidth = 600;
		appHeight = 800;
		boardWidth = 600;
		boardHeight = 600;
		
		camera= new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		camera.setToOrtho(true, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		batch = new SpriteBatch();
		
		stage = new Stage(new StretchViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));
		text = new TextButton("test", new Skin(Gdx.files.internal("uiskin.json")));
		text.setPosition(250, 50);
		
		stage.addActor(text);
		inputMultiplexer = new InputMultiplexer();
		inputMultiplexer.addProcessor(this);
		inputMultiplexer.addProcessor(stage);
		Gdx.input.setInputProcessor(inputMultiplexer);
		b = new board(boardWidth,boardHeight,5,5);


	
	}

	
	@Override
	public void render () {

		batch.setProjectionMatrix(camera.combined);
		
		Gdx.gl.glClearColor(0, 0, 0, 0);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		
		stage.act();
		stage.draw();
		batch.begin();
		
		for(Point e: b.getEntities().keySet()) {
			batch.draw(b.getEntities().get(e).getTexture(), b.getEntities().get(e).getX(), b.getEntities().get(e).getY());
			//b.getEntities().get(new Point(5,5)).live();
	
			
		}
		
		
		//batch.draw(b.getEntities().get(new Point(15, 0)).t, b.getEntities().get(new Point(15,0)).x, b.getEntities().get(new Point(15, 0)).y);

		//batch.draw(b.getEntities().get(20).t, b.getEntities().get(20).x, b.getEntities().get(20).y);
		//b.getEntities().get(20).pixmap.drawRectangle(5, 5, 5, 5);
		//b.getEntities().get(20).pixmap.drawPixel(200, 200);
		batch.end();
		
	
	
		
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		
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
			
		System.out.println(x);
		System.out.println(y);
		
		entity e = b.getEntities().get(new Point(x,y));
		if(e!=null) {
			if(e.getLiving()) {
				e.die();
			}
			else {
				e.live();
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
