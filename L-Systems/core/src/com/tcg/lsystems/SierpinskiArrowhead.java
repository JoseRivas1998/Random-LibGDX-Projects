package com.tcg.lsystems;

import com.badlogic.gdx.math.Vector2;

public class SierpinskiArrowhead extends AbstractFractal {
    @Override
    protected Turtle generateTree(String code) {
        Turtle turtle = new Turtle(new Vector2(0, 0), 0, 5, false);
        for (int i = 0; i < code.length(); i++) {
            switch (code.charAt(i)) {
                case 'A':
                case 'B':
                    turtle.moveForward();
                    break;
                case '+':
                    turtle.turnLeft(60);
                    break;
                case '-':
                    turtle.turnRight(60);
                    break;
                default:
            }
        }
        return turtle;
    }

    @Override
    protected void initRules() {
        this.rules = new ProductionRule[] {
                new ProductionRule('A', "B-A-B"),
                new ProductionRule('B', "A+B+A")
        };
    }

    @Override
    protected String getAxiom() {
        return "A";
    }
}
