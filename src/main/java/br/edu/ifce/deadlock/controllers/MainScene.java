package br.edu.ifce.deadlock.controllers;

import br.edu.ifce.deadlock.ApplicationManager;
import br.edu.ifce.deadlock.events.*;
import br.edu.ifce.deadlock.models.ProcessInfoWithStatus;
import br.edu.ifce.deadlock.models.ProcessWithResource;
import br.edu.ifce.deadlock.models.ResourceInfo;
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
    private TableView processesTable, resourcesTable, eventsTable;

    @FXML
    private TextField osDeltaTimeTextField;

    private Alert deadlockAlert;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        EventBus.getInstance().subscribe(this);
        //menuBar.setUseSystemMenuBar(true);

        setupProcessTableColumns();
        setupResourcesTableColumns();
        setupEventsTableColumns();

        deadlockAlert = new Alert(Alert.AlertType.WARNING);
        deadlockAlert.setTitle("DEADLOCK");
        deadlockAlert.setHeaderText("O sistema operacional detectou um deadlock");
    }

    private void setupResourcesTableColumns() {
        TableColumn rNameCol = (TableColumn) resourcesTable.getColumns().get(0);
        rNameCol.setCellValueFactory(new PropertyValueFactory<ResourceInfo, String>("name"));

        TableColumn rQtdCol = (TableColumn) resourcesTable.getColumns().get(1);
        rQtdCol.setCellValueFactory(new PropertyValueFactory<ResourceInfo, Integer>("qtd"));
    }

    private void setupProcessTableColumns() {
        TableColumn pNameCol = (TableColumn) processesTable.getColumns().get(0);
        pNameCol.setCellValueFactory(new PropertyValueFactory<ProcessInfoWithStatus, String>("name"));

        TableColumn pDtsCol = (TableColumn) processesTable.getColumns().get(1);
        pDtsCol.setCellValueFactory(new PropertyValueFactory<ProcessInfoWithStatus, Integer>("dts"));

        TableColumn pDtuCol = (TableColumn) processesTable.getColumns().get(2);
        pDtuCol.setCellValueFactory(new PropertyValueFactory<ProcessInfoWithStatus, Integer>("dtu"));

        TableColumn pStatusCol = (TableColumn) processesTable.getColumns().get(3);
        pStatusCol.setCellValueFactory(new PropertyValueFactory<ProcessInfoWithStatus, Integer>("status"));

        TableColumn pRemoveCol = (TableColumn) processesTable.getColumns().get(4);
        pRemoveCol.setCellFactory(ActionButtonTableCell.forTableColumn("X", (ProcessInfoWithStatus p) -> {
            ApplicationManager.getInstance().removeProcess(p.getProcessInfo());
            return p;
        }));
    }

    private void setupEventsTableColumns() {
        TableColumn pHandleCol = (TableColumn) eventsTable.getColumns().get(0);
        pHandleCol.setCellValueFactory(new PropertyValueFactory<ProcessInfoWithStatus, String>("handle"));

        TableColumn pNameCol = (TableColumn) eventsTable.getColumns().get(1);
        pNameCol.setCellValueFactory(new PropertyValueFactory<ProcessInfoWithStatus, String>("processName"));

        TableColumn rNameCol = (TableColumn) eventsTable.getColumns().get(2);
        rNameCol.setCellValueFactory(new PropertyValueFactory<ProcessInfoWithStatus, Integer>("resourceName"));

        TableColumn pDtuCol = (TableColumn) eventsTable.getColumns().get(3);
        pDtuCol.setCellValueFactory(new PropertyValueFactory<ProcessInfoWithStatus, String>("usageProgress"));
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
    void onProcessUpdateStatus(ProcessUpdatedStatus event) {
        Platform.runLater(() -> {
            for (Object s : processesTable.getItems()) {
                ProcessInfoWithStatus p = (ProcessInfoWithStatus) s;
                if (p.getProcessInfo().equals(event.getProcess())) {
                    p.setStatus(event.getStatus());
                    processesTable.refresh();
                    return;
                }
            }
        });
    }

    @Subscribe
    public void onProcessAcquiredResource(ProcessAcquiredResource event) {
        Platform.runLater(() -> {
            eventsTable.getItems().add(event.getProcessWithResource());
            resourcesTable.refresh();
        });
    }

    @Subscribe
    void onProcessReleasedResource(ProcessReleaseResource event) {
        Platform.runLater(() -> {
            for (Object o : eventsTable.getItems()) {
                ProcessWithResource p = (ProcessWithResource) o;
                if (event.getProcessWithResource().getHandle() == p.getHandle()) {
                    eventsTable.getItems().remove(p);
                    return;
                }
            }
        });
    }

    @Subscribe
    void onUpdate(RefreshData event) {
        Platform.runLater(() -> {
            eventsTable.refresh();
            processesTable.refresh();
            resourcesTable.refresh();
        });
    }

    @Subscribe
    void onProcessRemoved(ProcessRemovedEvent event) {
        processesTable.getItems().remove(event.getProcess());
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
        processesTable.getItems().add(new ProcessInfoWithStatus(event.getProcess(), "Inicializando..."));
    }

    @Subscribe
    public void onCreatedResource(ResourceCreatedEvent event) {
        resourcesTable.getItems().add(event.getResource());
    }
}
