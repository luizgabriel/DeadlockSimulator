package br.edu.ifce.deadlock.events;

import br.edu.ifce.deadlock.models.ProcessInfo;
import br.edu.ifce.deadlock.models.ResourceInfo;

public class ProcessBlockedEvent {
    private final ProcessInfo process;
    private final ResourceInfo resourceTried;

    public ProcessBlockedEvent(ProcessInfo process, ResourceInfo resourceTried) {
        this.process = process;
        this.resourceTried = resourceTried;
    }

    public ProcessInfo getProcess() {
        return process;
    }

    public ResourceInfo getResourceTried() {
        return resourceTried;
    }
}
