package br.edu.ifce.deadlock.events;

import com.google.common.eventbus.EventBus;
import dagger.Module;
import dagger.Provides;

import javax.inject.Singleton;

@Module
public class EventBusModule {

    @Provides
    @Singleton
    public EventBus providesEventBus() {
        return new EventBus();
    }

}
