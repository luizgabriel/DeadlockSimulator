package br.edu.ifce.deadlock;

import br.edu.ifce.deadlock.tasks.OSSimulationTask;
import br.edu.ifce.deadlock.utils.Component;
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

        primaryStage.setScene(new Scene(Component.load("main_scene")));
        primaryStage.setMaximized(true);
        primaryStage.setTitle("IFCE .:: DETECTOR DE DEADLOCK");
        primaryStage.show();

        Thread osThread = new Thread(new OSSimulationTask(20));
        osThread.start();
    }

}
