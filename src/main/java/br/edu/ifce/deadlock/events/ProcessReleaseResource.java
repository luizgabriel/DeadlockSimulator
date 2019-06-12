package br.edu.ifce.deadlock.events;

import br.edu.ifce.deadlock.models.ResourceAllocation;

public class ProcessReleaseResource {

    private final ResourceAllocation resourceAllocation;

    public ProcessReleaseResource(ResourceAllocation resourceAllocation) {
        this.resourceAllocation = resourceAllocation;
    }

    public ResourceAllocation getResourceAllocation() {
        return resourceAllocation;
    }
}
