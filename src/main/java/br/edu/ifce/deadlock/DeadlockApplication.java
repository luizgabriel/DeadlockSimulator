package br.edu.ifce.deadlock;

import br.edu.ifce.deadlock.utils.Component;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

public class DeadlockApplication extends Application {

    private static DeadlockApplication instance;

    private DeadlockApplicationComponent component;

    public static void main(String[] args) {
        launch(args);
    }

    public static DeadlockApplicationComponent getAppComponent() {
        return instance.component;
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        instance = this;

        component = DaggerDeadlockApplicationComponent.builder().build();

        primaryStage.setScene(new Scene(Component.load("main_scene")));
        primaryStage.setMaximized(true);
        primaryStage.setTitle("IFCE .:: DETECTOR DE DEADLOCK");
        primaryStage.show();
    }

}
