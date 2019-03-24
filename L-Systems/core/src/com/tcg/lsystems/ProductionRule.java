package com.tcg.lsystems;

public final class ProductionRule {
    public final char input;
    public final String output;

    public ProductionRule(char input, String output) {
        this.input = input;
        this.output = output;
    }

    @Override
    public String toString() {
        return input + " -> " + output;
    }
}
