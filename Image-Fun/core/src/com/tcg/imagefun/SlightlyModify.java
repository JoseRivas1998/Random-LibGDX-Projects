package com.tcg.imagefun;

import com.badlogic.gdx.math.MathUtils;

import java.util.Random;

public class SlightlyModify extends ImageEffectBase {

    private int row;
    private int change = 1;
    private Random random;


    @Override
    public void render() {
        super.render();
        random = new Random();
        noise();
    }

    private void noise() {
        if(row < this.image.pixels.length && row >= 0) {
            for (int col = 0; col < this.image.pixels[row].length; col++) {
                MyImage.MyColor myColor = this.image.pixels[row][col];
                int diff = nextGaussian(0x5);
                myColor.r = Util.clamp(myColor.r + diff, 0, 0xFF);
                myColor.g = Util.clamp(myColor.g + diff, 0, 0xFF);
                myColor.b = Util.clamp(myColor.b + diff, 0, 0xFF);
            }
        } else {
            change *=-1;
        }
        row+=change;
    }

    private int nextGaussian(int range) {
        return (int) (range * random.nextGaussian());
    }

}
