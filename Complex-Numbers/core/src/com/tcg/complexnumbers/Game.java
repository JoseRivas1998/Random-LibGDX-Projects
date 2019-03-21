package com.tcg.complexnumbers;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Game extends ApplicationAdapter {
	
	@Override
	public void create () {
		ComplexNumber c1 = ComplexNumber.of(9, 5);
		ComplexNumber c2 = ComplexNumber.of(4, 7);
		System.out.println(c1 + " - " + c2 + " = " + c1.minus(c2));
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
	}
	
	@Override
	public void dispose () {
	}
}
