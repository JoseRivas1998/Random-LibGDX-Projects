package com.tcg.lsystems;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

public class Dragon extends AbstractFractal {
    @Override
    protected Turtle generateTree(String code) {
        Turtle turtle = new Turtle(new Vector2(0, 0), MathUtils.PI / 2f, 5, false);
        for (int i = 0; i < code.length(); i++) {
            switch (code.charAt(i)) {
                case 'F':
                    turtle.moveForward();
                    break;
                case '-':
                    turtle.turnLeft(90);
                    break;
                case '+':
                    turtle.turnRight(90);
                    break;
                default:
            }
        }
        return turtle;
    }

    @Override
    protected void initRules() {
        this.rules = new ProductionRule[] {
            new ProductionRule('X', "X+YF+"),
            new ProductionRule('Y', "-FX-Y")
        };
    }

    @Override
    protected String getAxiom() {
        return "FX";
    }
}
