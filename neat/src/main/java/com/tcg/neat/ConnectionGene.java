package com.tcg.neat;

/**
 * @author Jose de Jesus Rodriguez Rivas
 * @version 1/20/19
 */
public class ConnectionGene {

    private int inNode;
    private int outNode;
    private float weight;
    private boolean expressed;
    private int innovation;

    public ConnectionGene(int inNode, int outNode, float weight, boolean expressed, int innovation) {
        this.inNode = inNode;
        this.outNode = outNode;
        this.weight = weight;
        this.expressed = expressed;
        this.innovation = innovation;
    }

    public int getInNode() {
        return inNode;
    }

    public int getOutNode() {
        return outNode;
    }

    public float getWeight() {
        return weight;
    }

    public boolean isExpressed() {
        return expressed;
    }

    public int getInnovation() {
        return innovation;
    }

    public void disable() {
        expressed = false;
    }

    public ConnectionGene copy() {
        return new ConnectionGene(inNode, outNode, weight, expressed, innovation);
    }


}
