package br.edu.ifce.deadlock.ui;

import javafx.scene.Parent;

import java.io.IOException;

public class MainScene {
    private final Parent root = Component.load("main_scene.fxml");

    public MainScene() throws IOException {

    }

    public Parent getRoot() {
        return this.root;
    }
}
