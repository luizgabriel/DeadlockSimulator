package br.edu.ifce.deadlock.navigation;

import java.io.IOException;

public final class Navigator {

    private static INavigator instance;

    public static INavigator getInstance() {
        if (instance == null) {
            try {
                instance = new JavaFXNavigatorImpl();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return instance;
    }

}
