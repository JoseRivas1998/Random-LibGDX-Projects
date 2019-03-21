package com.tcg.butterflycurve;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class Butterfly extends Curve {

	@Override
	protected float r(float theta) {
		float term1 = (float) Math.pow(MathUtils.E, MathUtils.sin(theta));
		float term2 = 2 * MathUtils.cos(4 * theta);
		float term3Num = 2 * theta - MathUtils.PI;
		float term3Denom = 24f;
		float term3 = (float) Math.pow(MathUtils.sin(term3Num / term3Denom), 5);
		return term1 - term2 + term3;
	}

}
