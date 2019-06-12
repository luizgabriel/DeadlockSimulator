package br.edu.ifce.deadlock.events;

import br.edu.ifce.deadlock.models.ResourceAllocation;

public class ProcessAcquiredResource {

    private final ResourceAllocation resourceAllocation;

    public ProcessAcquiredResource(ResourceAllocation resourceAllocation) {
        this.resourceAllocation = resourceAllocation;
    }

    public ResourceAllocation getResourceAllocation() {
        return resourceAllocation;
    }

}
