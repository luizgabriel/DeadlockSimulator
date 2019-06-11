package br.edu.ifce.deadlock.ui;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class CreateResourceDialog implements Initializable {

    @FXML
    private TextField nameTextField;

    @FXML
    private TextField qtdTextField;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //
    }

    @FXML
    public void onClickSave() {
        //TODO: Add save logic
    }
}
