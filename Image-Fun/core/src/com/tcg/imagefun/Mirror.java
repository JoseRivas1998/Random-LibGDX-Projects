package com.tcg.imagefun;

import java.util.Arrays;

public class Mirror extends ImageEffectBase {

    private int row;

    @Override
    public void render() {
        super.render();
        this.mirror();
    }

    private void mirror() {
        if(row < this.image.pixels.length) {
            int rowLength = this.image.pixels[row].length;
            for(int col = 0; col < rowLength / 2; col++) {
                Util.swap(this.image.pixels[row], col, rowLength - col - 1);
            }
            row++;
        }
    }

}
