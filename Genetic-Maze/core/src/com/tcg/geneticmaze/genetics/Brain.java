package com.tcg.geneticmaze.genetics;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.tcg.lichengine.TCGHelpers;
import com.tcg.lichengine.managers.TCGValues;

/**
 * @author Jose de Jesus Rodriguez Rivas
 * @version 1/22/19
 */
public class Brain {

    private Vector2[] directions;
    private int step;

    public Brain(int size) {
        directions = new Vector2[size];
        step = 0;
        randomize();
    }

    private void randomize() {
        for (int i = 0; i < directions.length; i++) {
            directions[i] = TCGHelpers.randomVelocity(TCGValues.getFloat("dot_acc"));
        }
    }

    public Vector2 nextVector() {
        step++;
        return new Vector2(directions[step - 1]);
    }

    public boolean hasNext() {
        return step < directions.length;
    }

    public Brain copy() {
        Brain brain = new Brain(directions.length);
        for (int i = 0; i < directions.length; i++) {
            brain.directions[i] = new Vector2(directions[i]);
        }
        return brain;
    }

    public void mutate() {
        float mutationRate = TCGValues.getFloat("mutation_rate");
        for (int i = 0; i < directions.length; i++) {
            if(MathUtils.randomBoolean(mutationRate)) {
                directions[i] = TCGHelpers.randomVelocity(TCGValues.getFloat("dot_acc"));
            }
        }
    }

    public int getStep() {
        return step;
    }
}
