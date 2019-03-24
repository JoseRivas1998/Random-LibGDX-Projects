package com.tcg.lsystems;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;

public final class LineSegment {

    private final float x1;
    private final float y1;
    private final float x2;
    private final float y2;

    private LineSegment(float x1, float y1, float x2, float y2) {
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
    }

    public static LineSegment of(float x1, float y1, float x2, float y2) {
        return new LineSegment(x1, y1, x2, y2);
    }

    public static LineSegment of(Vector2 p1, Vector2 p2) {
        return LineSegment.of(p1.x, p1.y, p2.x, p2.y);
    }

    public Vector2 p1() {
        return new Vector2(x1, y1);
    }

    public Vector2 p2() {
        return new Vector2(x2, y2);
    }

    public void draw(ShapeRenderer sr) {
        sr.line(p1(), p2());
    }

}
