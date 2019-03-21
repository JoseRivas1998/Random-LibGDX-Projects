package com.tcg.butterflycurve;

import com.badlogic.gdx.math.MathUtils;

public class HyperbolicSpiral extends Curve {

    @Override
    public float getScale() {
        return 22f;
    }

    @Override
    public float getMax() {
        return 25 * MathUtils.PI;
    }

    @Override
    protected float r(float theta) {
        return getScale() / theta;
    }
}
