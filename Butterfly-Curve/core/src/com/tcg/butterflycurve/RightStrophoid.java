package com.tcg.butterflycurve;

import com.badlogic.gdx.math.MathUtils;

public class RightStrophoid extends Curve {

    @Override
    public float getScale() {
        return 30f;
    }

    @Override
    public float getMax() {
        return MathUtils.PI;
    }

    @Override
    protected float r(float theta) {
        return getScale() * MathUtils.cos(2 * theta) / MathUtils.cos(theta);
    }
}
