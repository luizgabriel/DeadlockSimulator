package br.edu.ifce.deadlock.navigation;

import br.edu.ifce.deadlock.utils.Component;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class JavaFXNavigatorImpl implements INavigator {

    private final Stage createResourceStage;
    private final Stage createProcessStage;

    JavaFXNavigatorImpl() throws IOException {
        createResourceStage = new Stage();
        createResourceStage.setScene(new Scene(Component.load("create_resource_dialog")));
        createResourceStage.setTitle("Criar processo");
        createResourceStage.setResizable(false);

        createProcessStage = new Stage();
        createProcessStage.setScene(new Scene(Component.load("create_process_dialog")));
        createProcessStage.setTitle("Criar recurso");
        createProcessStage.setResizable(false);
    }

    @Override
    public void openCreateResourceDialog() {
        createResourceStage.show();
    }

    @Override
    public void openCreateProcessDialog() {
        createProcessStage.show();
    }
}
