package com.tcg.neat;

/**
 * @author Jose de Jesus Rodriguez Rivas
 * @version 1/20/19
 */
public class NodeGene {

    public enum TYPE {
        INPUT,
        HIDDEN,
        OUTPUT
        ;
    }

    private TYPE type;
    private int id;

    public NodeGene(TYPE type, int id) {
        this.type = type;
        this.id = id;
    }

    public TYPE getType() {
        return type;
    }

    public int getId() {
        return id;
    }

    public NodeGene copy() {
        return new NodeGene(type, id);
    }

}
