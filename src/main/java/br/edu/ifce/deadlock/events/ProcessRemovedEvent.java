package br.edu.ifce.deadlock.events;

import br.edu.ifce.deadlock.models.ProcessInfo;

public class ProcessRemovedEvent {
    private final ProcessInfo process;

    public ProcessRemovedEvent(ProcessInfo process) {
        this.process = process;
    }

    public ProcessInfo getProcess() {
        return process;
    }
}
