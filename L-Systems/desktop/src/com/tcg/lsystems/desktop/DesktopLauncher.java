package com.tcg.lsystems.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.tcg.lsystems.*;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class DesktopLauncher extends Application {

	public enum Fractal {
		BinaryTree(Binary.class),
		KochCurve(Koch.class),
		SierpinskiTriangle(Sierpinski.class),
		SierpinskiArrowheadTriangle(SierpinskiArrowhead.class),
		DragonCurve(Dragon.class),
		FractalPlant(Plant.class),
		;
		public final Class<? extends AbstractFractal> fractal;

		Fractal(Class<? extends AbstractFractal> fractal) {
			this.fractal = fractal;
		}
	}

	public static void main (String[] arg) {
		launch(arg);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		ComboBox<Fractal> fractalComboBox = new ComboBox<>();
		fractalComboBox.getItems().addAll(Fractal.values());
		fractalComboBox.setValue(Fractal.values()[0]);

		Button run = new Button("Run");
		run.setOnAction(event -> {
			primaryStage.close();
			LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
			try {
				new LwjglApplication(fractalComboBox.getValue().fractal.newInstance(), config);
			} catch (InstantiationException | IllegalAccessException e) {
				e.printStackTrace();
			}
		});

		VBox vBox = new VBox(5, fractalComboBox, run);
		vBox.setPadding(new Insets(10));
		Scene scene = new Scene(vBox);
		primaryStage.setScene(scene);
		primaryStage.show();

	}
}
