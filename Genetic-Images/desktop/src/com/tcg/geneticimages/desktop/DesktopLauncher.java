package com.tcg.geneticimages.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.backends.lwjgl.LwjglFiles;
import com.badlogic.gdx.graphics.Texture;
import com.tcg.geneticimages.Game;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
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
		launch(arg);
//		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
//		config.width = 16 * 2;
//		config.height = 16;
//		new LwjglApplication(new Game("/home/joserivas1998/Pictures/TCG Icons/16.png"), config);
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
						LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
						config.width = width * 2;
						config.height = height;
						new LwjglApplication(new Game(imageFile.getAbsolutePath()), config);
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});
		primaryStage.setScene(new Scene(new StackPane(button)));
		primaryStage.show();
	}
}
