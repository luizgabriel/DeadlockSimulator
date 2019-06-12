package br.edu.ifce.deadlock.events;

public final class EventBus {

    private static EventBus instance;
    
    private com.google.common.eventbus.EventBus handler;

    private EventBus() {
        handler = new com.google.common.eventbus.EventBus();
    }
    
    public void subscribe(Object instance) {
        handler.register(instance);
    }
    
    public void dispatch(Object event) {
        handler.post(event);
    }
    
    public static EventBus getInstance() {
        if (instance == null)
            instance = new EventBus();
        
        return instance;
    }
}
