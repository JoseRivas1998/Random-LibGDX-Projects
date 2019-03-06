package com.tcg.imagefun;

import com.badlogic.gdx.math.MathUtils;

public class Shuffle extends ImageEffectBase {

    private int row;

    @Override
    public void create() {
        super.create();
    }

    @Override
    public void render() {
        super.render();
        shuffle();
    }

    private void shuffle() {
        if(row < this.image.pixels.length) {
            for (int col = 0; col < this.image.pixels[row].length; col++) {
                int r2 = MathUtils.random(this.image.pixels.length - 1);
                int c2 = MathUtils.random(this.image.pixels[r2].length - 1);
                Util.swap(this.image.pixels, row, col, r2, c2);
            }
            row++;
        }
    }

}
