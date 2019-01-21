package com.tcg.geneticimages;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;

import java.util.Arrays;

/**
 * @author Jose de Jesus Rodriguez Rivas
 * @version 1/20/19
 */
public class Population {

    private float mutationRate;
    private DNA[] population;
    private Array<DNA> matingPool;
    private int[][] target;
    private int generations;
    private boolean finished;
    private int perfectScore;

    public Population(int[][] target, float mutationRate, int num) {
        this.target = target;
        this.mutationRate = mutationRate;
        population = new DNA[num];
        for (int i = 0; i < population.length; i++) {
            population[i] = new DNA(target.length, target[0].length);
        }
        calcFitness();
        matingPool = new Array<DNA>();
        finished = false;
        generations = 0;

        perfectScore = target.length * target[0].length;
    }

    public void calcFitness() {
        for (DNA dna : population) {
            dna.fitness(target);
        }
    }

    public void naturalSelection() {
        matingPool.clear();

        float maxFitness = 0;
        for (DNA dna : population) {
            if(dna.getFitness() > maxFitness) {
                maxFitness = dna.getFitness();
            }
        }

        for (DNA dna : population) {
            float fitness = maxFitness <= 1 ? 0.01f : Util.normalize(dna.getFitness(), 0, maxFitness);
            int n = (int) (fitness * 100f);
            for (int j = 0; j < n; j++) {
                matingPool.add(dna);
            }

        }

    }

    public void generate() {
        for (int i = 0; i < population.length; i++) {
            int a = MathUtils.random(matingPool.size - 1);
            int b = MathUtils.random(matingPool.size - 1);
            DNA partnerA = matingPool.get(a);
            DNA partnerB = matingPool.get(b);
            DNA child = partnerA.crossOver(partnerB);
            child.mutate(mutationRate);
            population[i] = child;
        }
        generations++;
    }

    public Texture getBest() {
        float record = 0.0f;
        int index = 0;
        for (int i = 0; i < population.length; i++) {
            if(population[i].getFitness() > record) {
                record = population[i].getFitness();
                index = i;
            }
        }

        if((int) record == perfectScore) {
            finished = true;
        }
        return population[index].asTexture();

    }

    public boolean isFinished() {
        return finished;
    }

    public int getGenerations() {
        return generations;
    }

    public float getAverageFitness() {
        float total = (float) Arrays.stream(population)
                            .mapToDouble(DNA::getFitness).sum();
        return total / population.length;
    }

    public float getMaxFitness() {
        return (float) Arrays.stream(population).mapToDouble(DNA::getFitness).max().getAsDouble();
    }

}
