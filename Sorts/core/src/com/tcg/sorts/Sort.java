package com.tcg.sorts;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Sort extends ApplicationAdapter {
	SpriteBatch batch;
	Texture img;
	long frames = 0;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		img = new Texture("badlogic.jpg");
		Gdx.graphics.setContinuousRendering(false);
		Gdx.input.setInputProcessor(new InputProcessor() {
			@Override
			public boolean keyDown(int keycode) {
				return true;
			}

			@Override
			public boolean keyUp(int keycode) {
				return true;
			}

			@Override
			public boolean keyTyped(char character) {
				return true;
			}

			@Override
			public boolean touchDown(int screenX, int screenY, int pointer, int button) {
				return true;
			}

			@Override
			public boolean touchUp(int screenX, int screenY, int pointer, int button) {
				return true;
			}

			@Override
			public boolean touchDragged(int screenX, int screenY, int pointer) {
				return true;
			}

			@Override
			public boolean mouseMoved(int screenX, int screenY) {
				return true;
			}

			@Override
			public boolean scrolled(int amount) {
				return true;
			}
		});
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		batch.draw(img, 0, 0);
		batch.end();
		frames++;
		Gdx.graphics.setTitle(String.valueOf(frames));
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		img.dispose();
	}
}
