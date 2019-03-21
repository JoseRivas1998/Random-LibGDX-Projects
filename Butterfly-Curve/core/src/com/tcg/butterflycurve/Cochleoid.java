package com.tcg.butterflycurve;

import com.badlogic.gdx.math.MathUtils;

public class Cochleoid extends Curve {

    @Override
    public float getScale() {
        return 20f;
    }

    @Override
    public float getDelta() {
        return MathUtils.degreesToRadians * 0.1f;
    }

    @Override
    public float getMin() {
        return -getMax();
    }

    @Override
    protected float r(float theta) {
        return getScale() * (MathUtils.sin(theta) / theta);
    }
}
