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
		if (row < this.image.image.length) {
			for (int col = 0; col < this.image.image[row].length; col++) {
				this.image.image[row][col].b = 0;
			}
			row++;
		}
	}
}
