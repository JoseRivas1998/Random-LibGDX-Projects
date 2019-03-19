package com.tcg.recursivetree;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

public class LineSegment {

    private final Vector2 p1;
    private final Vector2 p2;

    private LineSegment(float x1, float y1, float x2, float y2) {
        this.p1 = new Vector2(x1, y1);
        this.p2 = new Vector2(x2, y2);
    }

    private LineSegment(Vector2 p1, Vector2 p2) {
        this(p1.x, p1.y, p2.x, p2.y);
    }

    public static LineSegment of(float x1, float y1, float x2, float y2) {
        return new LineSegment(x1, y1, x2, y2);
    }

    public static LineSegment of(float x1, float y1, Vector2 p2) {
        return new LineSegment(x1, y1, p2.x, p2.y);
    }

    public static LineSegment of(Vector2 p1, float x2, float y2) {
        return new LineSegment(p1.x, p1.y, x2, y2);
    }

    public LineSegment of(Vector2 p1, Vector2 p2) {
        return new LineSegment(p1, p2);
    }

    public Vector2 getP1() {
        return new Vector2(p1);
    }

    public Vector2 getP2() {
        return new Vector2(p2);
    }

    public float getLength() {
        return (float) Math.sqrt(Math.pow(p1.x - p2.x, 2) + Math.pow(p1.y - p2.y, 2));
    }

    public float getAngle() {
        return MathUtils.atan2(p1.y - p2.y, p1.x - p2.x);
    }

    public void draw(ShapeRenderer shapeRenderer) {
        shapeRenderer.line(p1, p2);
    }

}
