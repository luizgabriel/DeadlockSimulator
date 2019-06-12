package br.edu.ifce.deadlock.events;

import br.edu.ifce.deadlock.models.ProcessInfo;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class DeadlockDetectedEvent {
    private List<ProcessInfo> processes;

    public DeadlockDetectedEvent(List<ProcessInfo> processes) {
        this.processes = Collections.unmodifiableList(processes);
    }

    public List<ProcessInfo> getProcesses() {
        return processes;
    }

    public List<String> getProcessesNames() {
        return getProcesses().stream().map(ProcessInfo::getName).collect(Collectors.toList());
    }
}
