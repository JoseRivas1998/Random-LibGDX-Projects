package com.tcg.geneticimages;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;

/**
 * @author Jose de Jesus Rodriguez Rivas
 * @version 1/20/19
 */
public class DNA {

    private int[][] genes;

    private float fitness;

    public DNA(int rows, int cols) {
        genes = new int[rows][cols];
        for (int row = 0; row < genes.length; row++) {
            for (int col = 0; col < genes[row].length; col++) {
                genes[row][col] = Util.randomRGBA();
            }
        }
    }

    public Texture asTexture() {
        return Util.coloMatrixToTexture(genes);
    }

    public void fitness(int[][] target) {
        int score = 0;
        for (int row = 0; row < genes.length; row++) {
            for (int col = 0; col < genes[row].length; col++) {
                if (genes[row][col] == target[row][col]) {
                    score++;
                }
            }
        }
        fitness = score;
    }

    public float getFitness() {
        return fitness;
    }

    public DNA crossOver(DNA partner) {
        DNA child = new DNA(genes.length, genes[0].length);

        int midRow = MathUtils.random(genes.length);
        int midCol = MathUtils.random(genes[0].length);

        for (int row = 0; row < genes.length; row++) {
            for (int col = 0; col < genes[row].length; col++) {
                if (row < midRow || (row == midRow && col < midCol)) {
                    child.genes[row][col] = genes[row][col];
                } else {
                    child.genes[row][col] = partner.genes[row][col];
                }
            }
        }

        return child;
    }

    public void mutate(float mutationRate) {
        for (int row = 0; row < genes.length; row++) {
            for (int col = 0; col < genes[row].length; col++) {
                if (MathUtils.randomBoolean(mutationRate)) {
                    genes[row][col] = Util.randomRGBA();
                }
            }
        }
    }

}
