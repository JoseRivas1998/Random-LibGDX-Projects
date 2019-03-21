package com.tcg.butterflycurve;

import com.badlogic.gdx.math.MathUtils;

public class DoubleFolium extends Curve {

    @Override
    public float getMax() {
        return MathUtils.PI;
    }

    @Override
    public float getScale() {
        return 15f;
    }

    @Override
    protected float r(float theta) {
        return 4 * getScale() * MathUtils.cos(theta) * (float) Math.pow(MathUtils.sin(theta), 2);
    }
}
