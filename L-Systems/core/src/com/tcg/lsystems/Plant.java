package com.tcg.lsystems;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

public class Plant extends AbstractFractal {
    @Override
    protected Turtle generateTree(String code) {
        Turtle turtle = new Turtle(new Vector2(0, 0), MathUtils.PI / 4f, 2, false);
        for (int i = 0; i < code.length(); i++) {
            switch (code.charAt(i)) {
                case 'F':
                    turtle.moveForward();
                    break;
                case '-':
                    turtle.turnRight(25);
                    break;
                case '+':
                    turtle.turnLeft(25);
                    break;
                case '[':
                    turtle.push();
                    break;
                case ']':
                    turtle.pop();
                    break;
                default:
            }
        }
        return turtle;
    }

    @Override
    protected void initRules() {
        this.rules = new ProductionRule[]{
                new ProductionRule('X', "F+[[X]-X]-F[-FX]+X"),
                new ProductionRule('F', "FF")
        };
    }

    @Override
    protected String getAxiom() {
        return "X";
    }
}
