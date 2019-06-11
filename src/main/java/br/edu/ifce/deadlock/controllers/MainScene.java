package br.edu.ifce.deadlock.controllers;

import br.edu.ifce.deadlock.DeadlockApplication;
import br.edu.ifce.deadlock.events.ProcessCreatedEvent;
import br.edu.ifce.deadlock.events.ProcessRemovedEvent;
import br.edu.ifce.deadlock.events.ResourceCreatedEvent;
import br.edu.ifce.deadlock.models.ProcessInfo;
import br.edu.ifce.deadlock.models.ResourceInfo;
import br.edu.ifce.deadlock.navigation.INavigator;
import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import javax.inject.Inject;
import java.net.URL;
import java.util.ResourceBundle;

public class MainScene implements Initializable {

    @Inject
    public INavigator navigator;

    @Inject
    public EventBus eventBus;

    @FXML
    private TableView processesTable, resourcesTable;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        DeadlockApplication.getAppComponent().inject(this);
        eventBus.register(this);
        //menuBar.setUseSystemMenuBar(true);

        setupProcessTableColumns();
        setupResourcesTableColumns();
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
        pRemoveCol.setCellFactory(ActionButtonTableCell.<ProcessInfo>forTableColumn("Remover", (ProcessInfo p) -> {
            processesTable.getItems().remove(p);
            eventBus.post(new ProcessRemovedEvent(p));
            return p;
        }));
    }

    @FXML
    public void onClickCreateProcess() {
        navigator.openCreateProcessDialog();
    }

    @FXML
    public void onClickCreateResource() {
        navigator.openCreateResourceDialog();
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
