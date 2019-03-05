package com.tcg.imagefun;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class NoRed extends ImageEffectBase {

	 private int row;
	
	@Override
	public void create () {
		super.create();
		this.row = 0;
	}

	@Override
	public void render () {
		super.render();
		removeRed();
	}

	private void removeRed() {
		if(row < this.image.image.length) {
			for (int col = 0; col < this.image.image[row].length; col++) {
				this.image.image[row][col].r = 0;
			}
			row++;
		}
	}
}
