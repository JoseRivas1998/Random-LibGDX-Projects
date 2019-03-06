package com.tcg.imagefun;

public class HalfMirror extends ImageEffectBase {

    private int row;

    @Override
    public void create() {
        super.create();
    }

    @Override
    public void render() {
        super.render();
        halfMirror();
    }

    private void halfMirror() {
        if(row < this.image.pixels.length) {
            int rowLength = this.image.pixels[row].length;
            for(int col = 0; col < rowLength / 2; col++) {
                this.image.pixels[row][rowLength - col - 1].set(this.image.pixels[row][col]);
            }
            row++;
        }
    }

}
