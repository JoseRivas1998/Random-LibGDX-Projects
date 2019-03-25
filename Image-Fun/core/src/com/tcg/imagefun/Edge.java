package com.tcg.imagefun;

public class Edge extends ImageEffectBase {

    final double threshold = 0.5;

    private int row = 0;

    @Override
    public void render() {
        super.render();
        edgeDetection();
    }

    public void edgeDetection() {

        if(row < this.image.height - 1) {
            for (int col = 0; col < this.image.width; col++) {
                MyImage.MyColor current = this.image.pixels[row][col];
                MyImage.MyColor below = this.image.pixels[row + 1][col];

                if(Math.abs(current.avg() - below.avg()) < threshold) {
                    this.image.pixels[row][col].set(0xFFFFFFFF);
                } else {
                    this.image.pixels[row][col].set(0x000000FF);
                }
            }
            row++;
        }

    }

}
