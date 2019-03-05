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
		if(row < this.image.image.length) {
			for (int col = 0; col < this.image.image[row].length; col++) {
				this.image.image[row][col].g = 0;
			}
			row++;
		}
	}
}
