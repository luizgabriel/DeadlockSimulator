package br.edu.ifce.deadlock.events;

import br.edu.ifce.deadlock.models.ProcessInfo;

public class ProcessUpdatedStatus {
    private final ProcessInfo process;
    private final String status;

    public ProcessUpdatedStatus(ProcessInfo process, String status) {
        this.process = process;
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public ProcessInfo getProcess() {
        return process;
    }

}
