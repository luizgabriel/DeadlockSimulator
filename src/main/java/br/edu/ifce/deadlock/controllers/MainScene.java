package br.edu.ifce.deadlock.controllers;

import br.edu.ifce.deadlock.DeadlockApplication;
import br.edu.ifce.deadlock.events.*;
import br.edu.ifce.deadlock.models.ProcessInfo;
import br.edu.ifce.deadlock.models.ResourceInfo;
import br.edu.ifce.deadlock.navigation.INavigator;
import br.edu.ifce.deadlock.navigation.Navigator;
import com.google.common.base.Joiner;
import com.google.common.eventbus.Subscribe;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.ResourceBundle;

public class MainScene implements Initializable {

    @FXML
    private TableView processesTable, resourcesTable;

    @FXML
    private TextField osDeltaTimeTextField;

    private Alert deadlockAlert;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        EventBus.getInstance().subscribe(this);
        //menuBar.setUseSystemMenuBar(true);

        setupProcessTableColumns();
        setupResourcesTableColumns();

        deadlockAlert = new Alert(Alert.AlertType.WARNING);
        deadlockAlert.setTitle("DEADLOCK");
        deadlockAlert.setHeaderText("O sistema operacional detectou um deadlock");
    }

    private void setupResourcesTableColumns() {
        TableColumn rNameCol = (TableColumn) resourcesTable.getColumns().get(0);
        rNameCol.setCellValueFactory(new PropertyValueFactory<ResourceInfo, String>("name"));

        TableColumn rQtdCol = (TableColumn) resourcesTable.getColumns().get(1);
        rQtdCol.setCellValueFactory(new PropertyValueFactory<Process, Integer>("qtd"));
    }

    private void setupProcessTableColumns() {
        TableColumn pNameCol = (TableColumn) processesTable.getColumns().get(0);
        pNameCol.setCellValueFactory(new PropertyValueFactory<ProcessInfo, String>("name"));

        TableColumn pDtsCol = (TableColumn) processesTable.getColumns().get(1);
        pDtsCol.setCellValueFactory(new PropertyValueFactory<ProcessInfo, Integer>("dts"));

        TableColumn pDtuCol = (TableColumn) processesTable.getColumns().get(2);
        pDtuCol.setCellValueFactory(new PropertyValueFactory<ProcessInfo, Integer>("dtu"));

        TableColumn pRemoveCol = (TableColumn) processesTable.getColumns().get(3);
        pRemoveCol.setCellFactory(ActionButtonTableCell.forTableColumn("X", (ProcessInfo p) -> {
            processesTable.getItems().remove(p);
            EventBus.getInstance().dispatch(new ProcessRemovedEvent(p));
            return p;
        }));
    }

    @FXML
    public void onClickCreateProcess() {
        Navigator.getInstance().openCreateProcessDialog();
    }

    @FXML
    public void onClickCreateResource() {
        Navigator.getInstance().openCreateResourceDialog();
    }

    @FXML
    void onClickUpdateOSDeltaTime() {
        int time = Integer.parseInt(osDeltaTimeTextField.getText());
        EventBus.getInstance().dispatch(new OSDeltaTimeUpdated(time));
    }

    @Subscribe
    void onDeadlockDetected(DeadlockDetectedEvent event) {
        Platform.runLater(() -> {
            if (!deadlockAlert.isShowing()) {
                deadlockAlert.setContentText("Processos envolvidos: " + Joiner.on(", ").join(event.getProcessesNames()));
                deadlockAlert.showAndWait();
            }
        });
    }

    @Subscribe
    void onChangeDeltaTime(OSDeltaTimeUpdated event) {
        osDeltaTimeTextField.setText(String.valueOf(event.getTime()));
    }

    @Subscribe
    public void onCreatedProcess(ProcessCreatedEvent event) {
        processesTable.getItems().add(event.getProcess());
    }

    @Subscribe
    public void onCreatedResource(ResourceCreatedEvent event) {
        resourcesTable.getItems().add(event.getResource());
    }
}
