package com.tcg.imagefun;

public class NoBlue extends ImageEffectBase {

	private int row;

	@Override
	public void create() {
		super.create();
		this.row = 0;
	}

	@Override
	public void render() {
		super.render();
		removeBlue();
	}

	private void removeBlue() {
		if (row < this.image.pixels.length) {
			for (int col = 0; col < this.image.pixels[row].length; col++) {
				this.image.pixels[row][col].b = 0;
			}
			row++;
		}
	}
}
