package com.tcg.geneticmaze.entities;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.tcg.geneticmaze.genetics.Brain;
import com.tcg.lichengine.TCGHelpers;
import com.tcg.lichengine.entities.Entity;
import com.tcg.lichengine.managers.TCGValues;

import static com.tcg.lichengine.TCGHelpers.worldHeight;
import static com.tcg.lichengine.TCGHelpers.worldWidth;

/**
 * @author Jose de Jesus Rodriguez Rivas
 * @version 1/22/19
 */
public class Dot extends Entity {

    private Vector2 goalPoint;
    private Vector2 startingPoint;
    private Vector2 acceleration;

    private boolean dead;
    private boolean isBest;
    private boolean reachedGoal;

    private float fitness;

    private Brain brain;

    public Dot(Vector2 startingPoint, Vector2 goalPoint) {
        super();
        setWidth(TCGValues.getFloat("dot_size"));
        setHeight(TCGValues.getFloat("dot_size"));
        setVelocity(0, 0);
        this.goalPoint = new Vector2(goalPoint);
        this.startingPoint = new Vector2(startingPoint);
        setX(startingPoint.x - (getWidth() * .5f));
        setY(startingPoint.y - (getHeight() * .5f));
        acceleration = new Vector2();
        dead = false;
        isBest = false;
        reachedGoal = false;
        fitness = 0.0f;
        brain = new Brain(TCGValues.getInt("initial_step"));
    }

    public boolean isDead() {
        return dead;
    }

    public void setDead(boolean dead) {
        this.dead = dead;
    }

    public boolean isBest() {
        return isBest;
    }

    public void setBest(boolean best) {
        isBest = best;
    }

    public boolean isReachedGoal() {
        return reachedGoal;
    }

    public void setReachedGoal(boolean reachedGoal) {
        this.reachedGoal = reachedGoal;
    }

    public float getFitness() {
        return fitness;
    }

    public int getStep() {
        return brain.getStep();
    }

    public void mutate() {
        brain.mutate();
    }

    public void calculateFitness() {
        if(reachedGoal) {
            fitness = 1f/16f + (10000f) / (brain.getStep() * brain.getStep());
        } else {
            float distanceToGoal = TCGHelpers.distance(getPosition(), goalPoint);
            fitness = 1.0f/(distanceToGoal * distanceToGoal);
        }
    }

    public void collisions(Level level) {
        if(!dead && !reachedGoal) {
            for (Ground ground : level) {
                if(collidingWith(ground)) {
                    setDead(true);
                    break;
                }
            }
        }
    }

    private Vector2 getCenter() {
        return new Vector2(getX() + (getWidth() * .5f), getY() + (getHeight() * .5f));
    }

    public Dot crossOver() {
        Dot dot = new Dot(this.startingPoint, this.goalPoint);
        dot.brain = brain.copy();
        return dot;
    }

    @Override
    public void update(float dt) {
        if(!dead && !reachedGoal) {
            move(dt);
            if(getX() <= -getWidth() || getX() >= worldWidth() - getWidth() || getY() <= -getHeight() || getY() >= worldHeight() - getHeight()) {
                setDead(true);
            } else if(TCGHelpers.distance(getPosition(), goalPoint) <= TCGValues.getFloat("goal_radius")) {
                setReachedGoal(true);
            }
        }
    }

    private void move(float dt) {
        if(brain.hasNext()) {
            acceleration.set(brain.nextVector());
        } else {
            setDead(true);
        }
        if(TCGHelpers.magnitude(getVelocity()) < 3 * 75) {
            setVelocityX(getVelocityX() + (acceleration.x * dt));
            setVelocityY(getVelocityY() + (acceleration.y * dt));
        }
        addVelocityToPosition(dt);
    }

    @Override
    public void draw(SpriteBatch spriteBatch, ShapeRenderer shapeRenderer, float dt) {
        shapeRenderer.setColor(TCGValues.color(isBest ? "best_dot" : "dot"));
        shapeRenderer.rect(
                getX(), getY(),
                getWidth(), getHeight()
        );
    }

    @Override
    public void dispose() {

    }
}
