package com.tcg.imagefun;

public class FullSelectionSortRowByRow extends ImageEffectBase {

    private int row;

    @Override
    public void render() {
        super.render();
        fullSelectionSort();
    }

    private void fullSelectionSort() {
        if(row < this.image.pixels.length) {
            for (int col = 0; col < this.image.pixels[row].length; col++) {
                if(row == this.image.pixels.length - 1 && col >= this.image.pixels[row].length - 1) break;
                Util.RowCol min = Util.getMin(this.image.pixels, row, col);
                Util.swap(this.image.pixels, row, col, min.row, min.col);
            }
            row++;
        }
    }
}
