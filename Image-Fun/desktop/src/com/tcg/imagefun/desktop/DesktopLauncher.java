package com.tcg.imagefun.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.tcg.imagefun.*;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class DesktopLauncher extends Application {

    public enum Effects {
        NoRed, NoGreen, NoBlue, Greyscale
    }

    private Stage window;
    private ComboBox<Effects> effectsComboBox;
    private Button button;

    public static void main(String[] arg) {
        launch(arg);
    }

    public void runApp() {
        FileChooser fileChooser = new FileChooser();
        File file = fileChooser.showOpenDialog(window);
        if (file != null && file.exists()) {
            BufferedImage image = null;
            try {
                image = ImageIO.read(file);
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (image != null) {
                this.window.close();
                ImageFun.filePath = file.getAbsolutePath();
                LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
                config.width = image.getWidth();
                config.height = image.getHeight();
                config.foregroundFPS = 0;
                config.title = effectsComboBox.getValue().toString();
                switch (effectsComboBox.getValue()) {
                    case NoRed:
                        new LwjglApplication(new NoRed(), config);
                        break;
                    case NoBlue:
                        new LwjglApplication(new NoBlue(), config);
                        break;
                    case NoGreen:
                        new LwjglApplication(new NoGreen(), config);
                        break;
                    case Greyscale:
                        new LwjglApplication(new Greyscale(), config);
                        break;
                }
            }
        }
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.window = primaryStage;
        button = new Button("Run");
        button.setOnAction(e -> runApp());
        effectsComboBox = new ComboBox<>();
        effectsComboBox.getItems().addAll(Effects.values());
        effectsComboBox.setValue(Effects.values()[0]);
        Scene scene = new Scene(new VBox(5, effectsComboBox, button));
        this.window.setScene(scene);
        this.window.show();
    }
}
