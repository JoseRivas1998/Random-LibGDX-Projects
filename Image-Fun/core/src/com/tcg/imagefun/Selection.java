package com.tcg.imagefun;

public class Selection extends ImageEffectBase {

    private int row;
    private int col;

    @Override
    public void create() {
        super.create();
        row = 0;
        col = 0;
    }

    @Override
    public void render() {
        super.render();
        sort();
    }

    private void sort() {
        if(row < this.image.pixels.length) {
            if(col < this.image.pixels[row].length - 1) {
                int min = Util.getMin(this.image.pixels[row], col);
                Util.swap(this.image.pixels[row], col, min);
                col++;
            } else {
                col = 0;
                row++;
            }
        }
    }

}
