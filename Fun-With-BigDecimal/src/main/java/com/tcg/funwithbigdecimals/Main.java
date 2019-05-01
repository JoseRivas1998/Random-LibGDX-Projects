package com.tcg.funwithbigdecimals;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.math.BigDecimal;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeSet;

import static com.tcg.funwithbigdecimals.Util.map;
import static com.tcg.funwithbigdecimals.Util.norm;

public class Main extends Application {

    public static final int CANVAS_HEIGHT = 300;
    public static final int CANVAS_WIDTH = 300;

    public Set<BigDecimal> vertical;
    public Set<BigDecimal> horizontal;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        vertical = new TreeSet<>();
        horizontal = new TreeSet<>();

        for (int i = 0; i < CANVAS_WIDTH; i += 10) {
            vertical.add(new BigDecimal(i));
        }
        for (int i = 0; i < CANVAS_HEIGHT; i += 10) {
            horizontal.add(new BigDecimal(i));
        }

        Canvas inputPlane = new Canvas(CANVAS_WIDTH, CANVAS_HEIGHT);
        drawInputPlane(inputPlane.getGraphicsContext2D());

        Canvas outputPlane = new Canvas(CANVAS_WIDTH, CANVAS_HEIGHT);
        drawOutputPlane(outputPlane.getGraphicsContext2D(), x -> x.square().add(BigComplexNumber.ofReal(BigDecimal.ONE)));

        HBox hBox = new HBox(5, inputPlane, outputPlane);
        Scene scene = new Scene(hBox);
        primaryStage.setScene(scene);

        primaryStage.show();
    }

    private void drawInputPlane(GraphicsContext gc) {
        gc.setFill(Color.BLUE);
        for (BigDecimal bigDecimal : vertical) {
            for (int i = 0; i < CANVAS_HEIGHT; i++) {
                double x = bigDecimal.doubleValue();
                double y = i;
                gc.fillOval(x, y, 1, 1);
            }
        }
        for (BigDecimal bigDecimal : horizontal) {
            for (int i = 0; i < CANVAS_WIDTH; i++) {
                double x = i;
                double y = bigDecimal.doubleValue();
                gc.fillOval(x, y, 1, 1);
            }
        }
    }

    private void drawOutputPlane(GraphicsContext gc, ComplexFunction f) {
        gc.setFill(Color.BLUE);
        for (BigDecimal x : vertical) {
            for (int i = 0; i < CANVAS_HEIGHT; i++) {
                BigDecimal min = new BigDecimal(-2);
                BigDecimal max = new BigDecimal(2);
                BigDecimal y = new BigDecimal(i);
                BigDecimal realIn = map(x, BigDecimal.ZERO, new BigDecimal(CANVAS_WIDTH), min, max);
                BigDecimal complexIn = map(y, BigDecimal.ZERO, new BigDecimal(CANVAS_HEIGHT), min, max);
                BigComplexNumber normedOut = f.f(BigComplexNumber.of(realIn, complexIn));
                BigDecimal mappedReal = map(normedOut.getReal(), min, max, BigDecimal.ZERO, new BigDecimal(CANVAS_WIDTH));
                BigDecimal mappedComplex = map(normedOut.getComplex(), min, max, BigDecimal.ZERO, new BigDecimal(CANVAS_HEIGHT));
                gc.fillOval(mappedReal.doubleValue(), mappedComplex.doubleValue(), 1, 1);
            }
        }
        for (BigDecimal y : horizontal) {
            for (int i = 0; i < CANVAS_HEIGHT; i++) {
                BigDecimal min = new BigDecimal(-4);
                BigDecimal max = new BigDecimal(4);
                BigDecimal x = new BigDecimal(i);
                BigDecimal realIn = map(x, BigDecimal.ZERO, new BigDecimal(CANVAS_WIDTH), min, max);
                BigDecimal complexIn = map(y, BigDecimal.ZERO, new BigDecimal(CANVAS_HEIGHT), min, max);
                BigComplexNumber normedOut = f.f(BigComplexNumber.of(realIn, complexIn));
                BigDecimal mappedReal = map(normedOut.getReal(), min, max, BigDecimal.ZERO, new BigDecimal(CANVAS_WIDTH));
                BigDecimal mappedComplex = map(normedOut.getComplex(), min, max, BigDecimal.ZERO, new BigDecimal(CANVAS_HEIGHT));
                gc.fillOval(mappedReal.doubleValue(), mappedComplex.doubleValue(), 1, 1);
            }
        }
    }

}
