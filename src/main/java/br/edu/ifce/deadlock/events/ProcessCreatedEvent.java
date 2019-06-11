package br.edu.ifce.deadlock.events;

import br.edu.ifce.deadlock.models.ProcessInfo;

public class ProcessCreatedEvent {
    private final ProcessInfo process;

    public ProcessCreatedEvent(ProcessInfo process) {
        this.process = process;
    }

    public ProcessInfo getProcess() {
        return process;
    }
}
