package com.tcg.geneticmaze.genetics;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.tcg.geneticmaze.entities.Dot;
import com.tcg.geneticmaze.entities.Level;
import com.tcg.lichengine.managers.TCGValues;

/**
 * @author Jose de Jesus Rodriguez Rivas
 * @version 1/22/19
 */
public class Population {

    private Dot[] dots;

    private float fitnessSum;
    private int gen;

    int bestDot;

    int minStep;

    public Population(int size, Vector2 startingPoint, Vector2 goalPoint) {
        fitnessSum = 0;
        gen = 1;
        minStep = TCGValues.getInt("initial_step");
        dots = new Dot[size];
        for (int i = 0; i < size; i++) {
            dots[i] = new Dot(startingPoint, goalPoint);
        }
    }

    public void update(float dt, Level level) {
        for (Dot dot : dots) {
            dot.update(dt);
            dot.collisions(level);
        }
    }

    public void draw(SpriteBatch sb, ShapeRenderer sr, float dt) {
        for (int i = dots.length - 1; i >= 0; i--) {
            dots[i].draw(sb, sr, dt);
        }
    }

    public void calculateFitness() {
        for (Dot dot : dots) {
            dot.calculateFitness();
        }
    }

    public boolean allDotsDead() {
        for (Dot dot : dots) {
            if(!dot.isDead() && !dot.isReachedGoal()) return false;
        }
        return true;
    }

    public void naturalSelection() {
        Dot[] newDots = new Dot[dots.length];
        setBestDot();
        calculateFitnessSum();

        newDots[0] = dots[bestDot].crossOver();
        newDots[0].setBest(true);

        for (int i = 1; i < dots.length; i++) {
            Dot parent = selectParent();
            newDots[i] = parent.crossOver();
        }

        dots = newDots.clone();
        gen++;

    }

    public void calculateFitnessSum() {
        fitnessSum = 0f;
        for (Dot dot : dots) {
            fitnessSum += dot.getFitness();
        }
    }

    public Dot selectParent() {
        float rand = MathUtils.random(fitnessSum);
        float runningSum = 0;
        for (Dot dot : dots) {
            runningSum += dot.getFitness();
            if(runningSum > rand) {
                return dot;
            }
        }
        return null;
    }

    public void mutate() {
        for (int i = 1; i < dots.length; i++) {
            Dot dot = dots[i];
            dot.mutate();
        }
    }

    public void setBestDot() {
        float max = 0;
        int maxIndex = 0;
        for (int i = 0; i < dots.length; i++) {
            Dot dot = dots[i];
            if(dot.getFitness() > max) {
                max = dot.getFitness();
                maxIndex = i;
            }
        }
        bestDot = maxIndex;
        if(dots[bestDot].isReachedGoal()) {
            minStep = dots[bestDot].getStep();
        }
    }

}
