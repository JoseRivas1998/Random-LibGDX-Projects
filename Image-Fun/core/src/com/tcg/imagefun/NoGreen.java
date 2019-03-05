package com.tcg.imagefun;

public class NoGreen extends ImageEffectBase {

	 private int row;
	
	@Override
	public void create () {
		super.create();
		this.row = 0;
	}

	@Override
	public void render () {
		super.render();
		removeGreen();
	}

	private void removeGreen() {
		if(row < this.image.pixels.length) {
			for (int col = 0; col < this.image.pixels[row].length; col++) {
				this.image.pixels[row][col].g = 0;
			}
			row++;
		}
	}
}
