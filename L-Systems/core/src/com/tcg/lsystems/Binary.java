package com.tcg.lsystems;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

public class Binary extends AbstractFractal {
    @Override
    protected Turtle generateTree(String code) {
        Turtle turtle = new Turtle(new Vector2(0, 0), MathUtils.PI / 2f, 5, false);
        for (int i = 0; i < code.length(); i++) {
            switch (code.charAt(i)) {
                case '0':
                case '1':
                    turtle.moveForward();
                    break;
                case '[':
                    turtle.push();
                    turtle.turnLeft(45);
                    break;
                case ']':
                    turtle.pop();
                    turtle.turnRight(45);
                    break;
                default:
            }
        }
        return turtle;
    }

    @Override
    protected void initRules() {
        rules = new ProductionRule[]{
                new ProductionRule('1', "11"),
                new ProductionRule('0', "1[0]0")
        };
    }

    @Override
    protected String getAxiom() {
        return "0";
    }
}
