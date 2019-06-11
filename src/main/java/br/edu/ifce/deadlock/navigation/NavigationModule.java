package br.edu.ifce.deadlock.navigation;

import dagger.Module;
import dagger.Provides;

import javax.inject.Singleton;
import java.io.IOException;

@Module
public class NavigationModule {

    @Provides
    @Singleton
    public INavigator providesNavigator() {
        try {
            return new JavaFXNavigatorImpl();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

}
