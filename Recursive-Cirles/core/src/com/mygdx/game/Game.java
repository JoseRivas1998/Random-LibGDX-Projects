package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.util.Stack;

public class Game extends ApplicationAdapter {

	private static final float INITIAL_RADIUS = 500;
	private static final float MIN_RADIUS = 1f;
	private ShapeRenderer sr;
	private Viewport viewport;
	private Array<Circle> circles;
	private Stack<Circle> circleStack;

	@Override
	public void create () {
		sr = new ShapeRenderer();
		viewport = new ScreenViewport();
		viewport.update(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		viewport.apply();

		circles = new Array<Circle>();
		circleStack = new Stack<Circle>();
		circleStack.push(new Circle(0, 0, INITIAL_RADIUS));

	}

	@Override
	public void render () {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		viewport.getCamera().position.set(0, 0, 0);
		viewport.update(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		viewport.apply();

		if(!circleStack.isEmpty()) {
			Circle circle = circleStack.pop();
			if(circle.radius > MIN_RADIUS) {
				circles.add(circle);
				circleStack.push(new Circle(circle.center.x, circle.center.y + circle.radius, circle.radius * .5f));
				circleStack.push(new Circle(circle.center.x, circle.center.y - circle.radius, circle.radius * .5f));
				circleStack.push(new Circle(circle.center.x + circle.radius, circle.center.y, circle.radius * .5f));
				circleStack.push(new Circle(circle.center.x - circle.radius, circle.center.y, circle.radius * .5f));
			}
		}

		sr.begin(ShapeRenderer.ShapeType.Line);
		sr.setProjectionMatrix(viewport.getCamera().combined);
		for (Circle circle : circles) {
			circle.draw(sr);
		}
		sr.end();
	}

	@Override
	public void resize(int width, int height) {
		viewport.update(width, height);
		viewport.apply();
	}

	@Override
	public void dispose () {
	}

	class Circle {
		public final Vector2 center;
		public final float radius;

		public Circle(float x, float y, float radius) {
			center = new Vector2(x, y);
			this.radius = radius;
		}

		public void draw(ShapeRenderer sr) {
			sr.ellipse(center.x - radius, center.y - radius, radius * 2, radius * 2);
		}

	}
}
