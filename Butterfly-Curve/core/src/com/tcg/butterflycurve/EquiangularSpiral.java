package com.tcg.butterflycurve;

import com.badlogic.gdx.math.MathUtils;

public class EquiangularSpiral extends Curve {

    @Override
    public float getScale() {
        return 1f;
    }

    @Override
    protected float r(float theta) {
        return getScale() * (float) Math.pow(MathUtils.E, theta * (1 / MathUtils.cos(MathUtils.PI / 4)));
    }
}
