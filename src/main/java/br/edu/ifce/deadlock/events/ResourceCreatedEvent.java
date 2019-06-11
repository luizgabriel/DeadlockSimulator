package br.edu.ifce.deadlock.events;

import br.edu.ifce.deadlock.models.ResourceInfo;

public class ResourceCreatedEvent {
   private final ResourceInfo resource;

    public ResourceCreatedEvent(ResourceInfo resource) {
        this.resource = resource;
    }

    public ResourceInfo getResource() {
        return resource;
    }
}
