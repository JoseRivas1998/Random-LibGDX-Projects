package com.tcg.lsystems;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

import java.util.Stack;

public class Turtle {

    private Stack<PositionAngle> positionAngles;
    private boolean scaleOnStack;
    private Array<LineSegment> lineSegments;
    private Vector2 currentPosition;
    private float angle;
    private float initialLength;

    public Turtle(Vector2 initialPosition, float angle, float initialLength, boolean scaleOnStack) {
        this.currentPosition = new Vector2(initialPosition);
        this.angle = angle;
        this.scaleOnStack = scaleOnStack;
        this.initialLength = initialLength;
        positionAngles = new Stack<PositionAngle>();
        this.lineSegments = new Array<LineSegment>();
        this.positionAngles = new Stack<PositionAngle>();
    }

    public void push() {
        positionAngles.push(new PositionAngle(currentPosition, this.angle));
    }

    public void pop() {
        if(!positionAngles.empty()) {
            PositionAngle positionAngle = positionAngles.pop();
            currentPosition.set(positionAngle.x, positionAngle.y);
            this.angle = positionAngle.angle;
        }
    }

    public void moveForward() {
        float length;
        if(scaleOnStack) {
            length = (float) (initialLength / Math.pow(2, positionAngles.size()));
        } else {
            length = initialLength;
        }
        Vector2 nextLocation = new Vector2(
                currentPosition.x + length * MathUtils.cos(angle),
                currentPosition.y + length * MathUtils.sin(angle)
        );
        lineSegments.add(LineSegment.of(currentPosition, nextLocation));
        currentPosition.set(nextLocation);
    }

    public void turnLeft(float angleDeg) {
        this.angle += angleDeg * MathUtils.degreesToRadians;
    }

    public void turnRight(float angleDeg) {
        this.angle -= angleDeg * MathUtils.degreesToRadians;
    }

    public void draw(ShapeRenderer sr) {
        for (int i = 0; i < lineSegments.size; i++) {
            LineSegment lineSegment =  lineSegments.get(i);
            Color base = new Color(Color.GREEN);
            Color target = base.lerp(Color.PURPLE, (float) i / lineSegments.size);
            sr.setColor(target);
            lineSegment.draw(sr);
        }
    }

    private class PositionAngle {
        public final float x;
        public final float y;
        public final float angle;

        public PositionAngle(float x, float y, float angle) {
            this.x = x;
            this.y = y;
            this.angle = angle;
        }

        public PositionAngle(Vector2 p1, float angle) {
            this(p1.x, p1.y, angle);
        }

    }

}
