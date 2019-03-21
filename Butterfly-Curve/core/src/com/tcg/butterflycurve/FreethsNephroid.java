package com.tcg.butterflycurve;

import com.badlogic.gdx.math.MathUtils;

public class FreethsNephroid extends Curve {

    @Override
    public float getScale() {
        return 12f;
    }

    @Override
    public float getMax() {
        return  4 * MathUtils.PI;
    }

    @Override
    protected float r(float theta) {
        return getScale() * (1 + 2 * MathUtils.sin(theta / 2));
    }
}
