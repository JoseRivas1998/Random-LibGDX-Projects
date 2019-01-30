package com.tcg.imagedrawing.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.tcg.imagedrawing.Game;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class DesktopLauncher extends Application {
	public static void main (String[] arg) {
		launch();
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		Button button = new Button("Select image");
		button.setOnAction(event -> {
			FileChooser fileChooser = new FileChooser();
			File imageFile = fileChooser.showOpenDialog(primaryStage);
			if(imageFile != null) {
				try {
					String contentType = Files.probeContentType(imageFile.toPath());
					if(contentType.contains("image")) {
						BufferedImage image = ImageIO.read(imageFile);
						int width = image.getWidth();
						int height = image.getHeight();
						primaryStage.close();
						runGame(imageFile.getAbsolutePath(), width, height);
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});
		primaryStage.setScene(new Scene(new StackPane(button)));
		primaryStage.show();
	}

	private void runGame(String image, int width, int height) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = width;
		config.height = height;
		new LwjglApplication(new Game(image), config);
	}

}
