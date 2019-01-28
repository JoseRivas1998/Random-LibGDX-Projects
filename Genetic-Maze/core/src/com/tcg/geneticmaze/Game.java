package com.tcg.geneticmaze;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.tcg.geneticmaze.gamestates.States;
import com.tcg.geneticmaze.managers.GameStateManager;
import com.tcg.geneticmaze.managers.LevelsManager;
import com.tcg.lichengine.LichengineGame;
import com.tcg.lichengine.managers.FPSCounter;

public class Game extends ApplicationAdapter {

	private FPSCounter fpsCounter;
	private GameStateManager gsm;

	@Override
	public void create () {
		LichengineGame.init();
		LevelsManager.init(Gdx.files);
		fpsCounter = new FPSCounter();
		gsm = new GameStateManager(States.Play);
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		float dt = Gdx.graphics.getDeltaTime();

		gsm.handleInput(dt);
		gsm.update(dt);
		gsm.draw(dt);

		fpsCounter.update(dt);
		Gdx.graphics.setTitle(fpsCounter.getTitleString());

	}

	@Override
	public void resize(int width, int height) {
		LichengineGame.resize(width, height);
		gsm.resize(width, height);
	}

	@Override
	public void dispose () {
		gsm.dispose();
	}
}
