package br.edu.ifce.deadlock;

import br.edu.ifce.deadlock.ui.MainScene;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class DeadlockApplication extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        MainScene main = new MainScene();
        primaryStage.setScene(new Scene(main.getRoot()));
        primaryStage.setMaximized(true);
        primaryStage.setTitle("IFCE .:: DETECTOR DE DEADLOCK");
        primaryStage.show();
    }
}
