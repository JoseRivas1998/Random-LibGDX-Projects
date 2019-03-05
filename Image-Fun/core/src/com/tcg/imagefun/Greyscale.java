package com.tcg.imagefun;

public class Greyscale extends ImageEffectBase {

    private int row;

    @Override
    public void create() {
        super.create();
        this.row = 0;
    }

    @Override
    public void render() {
        super.render();
        grayscale();
    }

    private void grayscale() {
        if (row < this.image.image.length) {
            for (int col = 0; col < this.image.image[row].length; col++) {
                MyImage.MyColor color = this.image.image[row][col];
                int l = color.mag();
                color.r = l;
                color.g = l;
                color.b = l;
            }
            row++;
        }
    }

}
