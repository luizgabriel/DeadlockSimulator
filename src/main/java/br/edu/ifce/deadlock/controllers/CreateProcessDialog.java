package br.edu.ifce.deadlock.controllers;

import br.edu.ifce.deadlock.ApplicationManager;
import br.edu.ifce.deadlock.models.ProcessInfo;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;


import java.net.URL;
import java.util.ResourceBundle;

public class CreateProcessDialog implements Initializable {

    @FXML
    private TextField nameTextField, dtsTextField, dtuTextField;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //
    }

    @FXML
    public void onClickSave() {
        String name = nameTextField.getText();

        if (!name.isEmpty()) {
            ProcessInfo process = new ProcessInfo(name,
                    Integer.parseInt(dtsTextField.getText()),
                    Integer.parseInt(dtuTextField.getText())
            );

            ApplicationManager.getInstance().addProcess(process);
        }

    }
}
