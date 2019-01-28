package com.tcg.geneticmaze.entities;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.tcg.lichengine.TCGHelpers;
import com.tcg.lichengine.entities.Entity;

/**
 * @author Jose de Jesus Rodriguez Rivas
 * @version 1/22/19
 */
public class Ground extends Entity {

    public Ground() {
        this(0, 0, 0, 0);
    }

    public Ground(Vector2 pos, Vector2 size) {
        this(pos.x, pos.y, size.x, size.y);
    }

    public Ground(float x, float y, float width, float height) {
        super();
        setPosition(x, y);
        setSize(width, height);
    }

    @Override
    public void update(float dt) {}

    @Override
    public void draw(SpriteBatch spriteBatch, ShapeRenderer shapeRenderer, float dt) {
        shapeRenderer.rect(
                getX(), getY(),
                getWidth(), getHeight()
        );
    }

    @Override
    public void dispose() {}
}
