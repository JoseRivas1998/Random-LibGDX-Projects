package com.tcg.butterflycurve.desktop;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.tcg.butterflycurve.*;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class DesktopLauncher extends Application {

	private Label valueLabel;

	public enum Curves {
		ButterflyCurve(Butterfly.class),
		CochleoidCurve(Cochleoid.class),
		DevilsCurve(Devil.class),
		DoubleFoliumCurve(DoubleFolium.class),
		EquiangularSpiralCurve(EquiangularSpiral.class),
		FreethsNephroidCurve(FreethsNephroid.class),
		HyperbolicSpiral(HyperbolicSpiral.class),
		RightStrophoidCurve(RightStrophoid.class),
		SpiralOfArchimedesCurve(SpiralOfArchimedes.class),
		;
		public final Class<? extends Curve> theClass;

		Curves(Class<? extends Curve> theClass) {
			this.theClass = theClass;
		}
	}

	private ComboBox<Curves> curve;
	private Slider fps;
	private CheckBox vsync;
	private Stage window;

	public static void main (String[] arg) {
		launch();
	}

	private void run() {
		try {
			window.close();
			ApplicationAdapter game = curve.getValue().theClass.newInstance();
			LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
			config.foregroundFPS = (int) fps.getValue();
			config.vSyncEnabled = vsync.isSelected();
			new LwjglApplication(game, config);
		} catch (InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		curve = new ComboBox<Curves>();
		curve.getItems().addAll(Curves.values());
		curve.setValue(Curves.values()[0]);
		Button button = new Button("Run");
		button.setOnAction(e -> run());

		fps = new Slider(0, 75, 60);
		fps.setBlockIncrement(1);
		fps.valueProperty().addListener((observable, oldValue, newValue) -> {
			valueLabel.setText(String.format("%02d", (int) fps.getValue()));
		});

		valueLabel = new Label(String.format("%02d", (int) fps.getValue()));

		vsync = new CheckBox("Use vSync");

		VBox vBox = new VBox(5,
				curve,
				new HBox(5, new Label("FPS"), fps, valueLabel),
				vsync,
				button);
		vBox.setPadding(new Insets(5));
		Scene scene = new Scene(vBox);
		window = primaryStage;
		window.setScene(scene);
		window.show();
	}
}
