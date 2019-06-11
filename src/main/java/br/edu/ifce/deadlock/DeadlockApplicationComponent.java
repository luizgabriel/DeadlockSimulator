package br.edu.ifce.deadlock;

import br.edu.ifce.deadlock.controllers.CreateProcessDialog;
import br.edu.ifce.deadlock.controllers.CreateResourceDialog;
import br.edu.ifce.deadlock.controllers.MainScene;
import br.edu.ifce.deadlock.events.EventBusModule;
import br.edu.ifce.deadlock.navigation.NavigationModule;
import dagger.Component;

import javax.inject.Singleton;

@Singleton
@Component(modules = {EventBusModule.class, NavigationModule.class})
public interface DeadlockApplicationComponent {
    void inject(MainScene obj);

    void inject(CreateProcessDialog obj);

    void inject(CreateResourceDialog obj);
}
