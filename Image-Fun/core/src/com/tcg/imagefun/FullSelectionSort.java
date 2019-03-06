package com.tcg.imagefun;

public class FullSelectionSort extends ImageEffectBase {

    private int row;
    private int col;

    @Override
    public void render() {
        super.render();
        fullSelectionSort();
    }

    private void fullSelectionSort() {
        if(row < this.image.pixels.length) {
            if(col < this.image.pixels[row].length) {
                if(row == this.image.pixels.length - 1 && col >= this.image.pixels[row].length - 1) {
                    col = 0;
                    row++;
                } else {
                    Util.RowCol min = Util.getMin(this.image.pixels, row, col);
                    Util.swap(this.image.pixels, row, col, min.row, min.col);
                    col++;
                }
            } else {
                col = 0;
                row++;
            }
        }
    }
}
