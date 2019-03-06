package com.tcg.imagefun;

import com.badlogic.gdx.math.MathUtils;

public class SlightlyModify extends ImageEffectBase {

    private int row;
    private int change = 1;

    @Override
    public void render() {
        super.render();
        noise();
    }

    private void noise() {
        if(row < this.image.pixels.length && row >= 0) {
            for (int col = 0; col < this.image.pixels[row].length; col++) {
                MyImage.MyColor myColor = this.image.pixels[row][col];
                myColor.r = Util.clamp(myColor.r + MathUtils.random(-0xF, 0xF), 0, 0xFF);
                myColor.g = Util.clamp(myColor.g + MathUtils.random(-0xF, 0xF), 0, 0xFF);
                myColor.b = Util.clamp(myColor.b + MathUtils.random(-0xF, 0xF), 0, 0xFF);
            }
        } else {
            change *=-1;
        }
        row+=change;
    }

}
