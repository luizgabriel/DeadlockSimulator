package br.edu.ifce.deadlock.utils;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import java.io.IOException;
import java.net.URL;

public final class Component {

    private Component() {}

    public static Parent load(String name) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Component.getResource(String.format("fxml/%s.fxml", name)));
        return loader.load();
    }

    public static URL getResource(String path) {
        return Component.class.getClassLoader().getResource(path);
    }

}
