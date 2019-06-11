package br.edu.ifce.deadlock.controllers;

import br.edu.ifce.deadlock.DeadlockApplication;
import br.edu.ifce.deadlock.events.ResourceCreatedEvent;
import br.edu.ifce.deadlock.models.ResourceInfo;
import com.google.common.eventbus.EventBus;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;

import javax.inject.Inject;
import java.net.URL;
import java.util.ResourceBundle;

public class CreateResourceDialog implements Initializable {

    @Inject
    public EventBus eventBus;

    @FXML
    private TextField nameTextField, qtdTextField;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        DeadlockApplication.getAppComponent().inject(this);
    }

    @FXML
    public void onClickSave() {
        ResourceInfo resource = new ResourceInfo(nameTextField.getText(), Integer.parseInt(qtdTextField.getText()));
        eventBus.post(new ResourceCreatedEvent(resource));
    }
}
