package com.tcg.imagefun;

import java.util.Arrays;
import java.util.Comparator;

public class JDKSortByRed extends ImageEffectBase {

    private int row;

    @Override
    public void create() {
        super.create();
        row = 0;
    }

    @Override
    public void render() {
        super.render();
        sort();
    }

    private void sort() {
        if (row < this.image.pixels.length) {
            Arrays.sort(this.image.pixels[row], Comparator.comparingInt(o -> o.r));
            row++;
        }
    }

}
