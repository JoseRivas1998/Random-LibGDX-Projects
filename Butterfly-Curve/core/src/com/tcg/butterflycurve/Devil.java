package com.tcg.butterflycurve;

import com.badlogic.gdx.math.MathUtils;

public class Devil extends Curve {

    @Override
    public float getScale() {
        return 40f;
    }

    @Override
    public float getMin() {
        return -getMax();
    }

    @Override
    protected float r(float theta) {
        float num = (float) (25 - 24 * Math.pow(Math.tan(theta), 2));
        float den = (float) (Math.pow(Math.tan(theta), 2));
        return (float) Math.sqrt(num / den);
    }
}
