package br.edu.ifce.deadlock.controllers;

import br.edu.ifce.deadlock.events.EventBus;
import br.edu.ifce.deadlock.events.ResourceCreatedEvent;
import br.edu.ifce.deadlock.models.ResourceInfo;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class CreateResourceDialog implements Initializable {

    @FXML
    private TextField nameTextField, qtdTextField;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //
    }

    @FXML
    public void onClickSave() {
        String name = nameTextField.getText();

        if (!name.isEmpty()) {
            ResourceInfo resource = new ResourceInfo(name, Integer.parseInt(qtdTextField.getText()));
            EventBus.getInstance().dispatch(new ResourceCreatedEvent(resource));
        }
    }
}
