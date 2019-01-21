package com.tcg.neat;

/**
 * @author Jose de Jesus Rodriguez Rivas
 * @version 1/20/19
 */
public class InnovationGenerator {

    private int currentInnovation = 0;

    public int getInnovation() {
        return currentInnovation++;
    }

}
