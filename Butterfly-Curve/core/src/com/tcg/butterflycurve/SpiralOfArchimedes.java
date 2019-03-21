package com.tcg.butterflycurve;

import com.badlogic.gdx.math.MathUtils;

public class SpiralOfArchimedes extends Curve{

    @Override
    public float getScale() {
        return 1.5f;
    }

    @Override
    public float getMax() {
        return 70 * MathUtils.PI;
    }

    @Override
    protected float r(float theta) {
        return getScale() * theta;
    }
}
