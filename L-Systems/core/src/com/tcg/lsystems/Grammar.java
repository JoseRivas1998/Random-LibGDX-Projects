package com.tcg.lsystems;

public class Grammar {

    public static String getNthProduction(int n, String axiom, ProductionRule... rules) {
        if(n < 0) throw new IllegalArgumentException("n must be >= 0");
        String output = axiom;
        for (int i = 0; i < n; i++) {
            output = produce(output, rules);
        }
        return output;
    }

    public static String produce(String input, ProductionRule... rules) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < input.length(); i++) {
            builder.append(produce(input.charAt(i), rules));
        }
        return builder.toString();
    }

    private static String produce(char input, ProductionRule... rules) {
        boolean found = false;
        ProductionRule rule = null;
        for (int i = 0; i < rules.length && !found; i++) {
            if(input == rules[i].input) {
                found = true;
                rule = rules[i];
            }
        }
        if(!found) {
            return String.valueOf(input);
        } else {
            return rule.output;
        }
    }



}
