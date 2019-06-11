package br.edu.ifce.deadlock.ui;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.MenuBar;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainScene implements Initializable {

    @FXML
    private MenuBar menuBar;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //menuBar.setUseSystemMenuBar(true);
    }

    @FXML
    public void onClickCreateProcess() throws IOException {
        Stage stage = new Stage();

        stage.setScene(new Scene(Component.load("create_process_dialog.fxml")));
        stage.setTitle("Criar processo");
        stage.setResizable(false);
        stage.show();
    }

    @FXML
    public void onClickCreateResource() throws IOException {
        Stage stage = new Stage();

        stage.setScene(new Scene(Component.load("create_resource_dialog.fxml")));
        stage.setTitle("Criar recurso");
        stage.setResizable(false);
        stage.show();
    }
}
