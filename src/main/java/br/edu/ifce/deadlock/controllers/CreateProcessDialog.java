package br.edu.ifce.deadlock.controllers;

import br.edu.ifce.deadlock.DeadlockApplication;
import br.edu.ifce.deadlock.events.ProcessCreatedEvent;
import com.google.common.eventbus.EventBus;
import br.edu.ifce.deadlock.models.ProcessInfo;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;

import javax.inject.Inject;
import java.net.URL;
import java.util.ResourceBundle;

public class CreateProcessDialog implements Initializable {

    @Inject
    public EventBus eventBus;

    @FXML
    private TextField nameTextField, dtsTextField, dtuTextField;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        DeadlockApplication.getAppComponent().inject(this);
    }

    @FXML
    public void onClickSave() {
        String name = nameTextField.getText();

        if (!name.isEmpty()) {
            ProcessInfo process = new ProcessInfo(name,
                    Integer.parseInt(dtsTextField.getText()),
                    Integer.parseInt(dtuTextField.getText())
            );

            eventBus.post(new ProcessCreatedEvent(process));
        }

    }
}
