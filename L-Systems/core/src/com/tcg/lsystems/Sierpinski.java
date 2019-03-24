package com.tcg.lsystems;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

public class Sierpinski extends AbstractFractal {
    @Override
    protected Turtle generateTree(String code) {
        Turtle turtle = new Turtle(new Vector2(0, 0), MathUtils.PI / 2f, 10, false);
        for (int i = 0; i < code.length(); i++) {
            switch (code.charAt(i)) {
                case 'F':
                case 'G':
                    turtle.moveForward();
                    break;
                case '+':
                    turtle.turnLeft(120);
                    break;
                case '-':
                    turtle.turnRight(120);
                    break;
                default:
            }
        }
        return turtle;
    }

    @Override
    protected void initRules() {
        this.rules = new ProductionRule[]{
                new ProductionRule('F', "F-G+F+G-F"),
                new ProductionRule('G', "GG")
        };
    }

    @Override
    protected String getAxiom() {
        return "F-G-G";
    }
}
