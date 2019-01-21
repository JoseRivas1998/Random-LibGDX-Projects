package com.tcg.geneticimages;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Game extends ApplicationAdapter {
	private SpriteBatch batch;

	private String filePath;

	private TiledBackground tiledBackground;

	private Population population;

	private int[][] target;

	private Texture targetTexture;

	public Game(String filePath) {
		super();
		this.filePath = filePath;
	}

	@Override
	public void create () {
		target = Util.absoluteFilePathToColorMatrix(this.filePath);
		targetTexture = new Texture(Gdx.files.absolute(this.filePath));
		batch = new SpriteBatch();
		tiledBackground = new TiledBackground("checkboard.png");
		population = new Population(target, 0.0001f, 5000);
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		if(!population.isFinished()) {
			population.naturalSelection();
			population.generate();
			population.calcFitness();
		}

		Texture texture = population.getBest();

		batch.begin();
		tiledBackground.draw(batch);
		batch.draw(targetTexture, 0, 0);
		batch.draw(texture, targetTexture.getWidth(), 0);
		batch.end();

		texture.dispose();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		tiledBackground.dispose();
		targetTexture.dispose();
	}
}
