package br.edu.ifce.deadlock.tasks;

import br.edu.ifce.deadlock.events.*;
import br.edu.ifce.deadlock.models.ProcessInfo;
import br.edu.ifce.deadlock.models.ResourceInfo;
import com.google.common.eventbus.Subscribe;

import java.util.*;

public class DeadlockDetector {

    private final EventBus eventBus;
    private final Map<ResourceInfo, List<ProcessInfo>> blocked;

    public DeadlockDetector() {
        blocked = new HashMap<>();
        eventBus = EventBus.getInstance();

        eventBus.subscribe(this);
    }

    public void check() {
        List<ProcessInfo> processesInDeadlock = new ArrayList<>();

        for (Map.Entry<ResourceInfo, List<ProcessInfo>> e: blocked.entrySet()) {
            if (e.getValue().size() > 1) {
                processesInDeadlock.addAll(e.getValue());
            }
        }

        if (processesInDeadlock.size() > 1)
            eventBus.dispatch(new DeadlockDetectedEvent(processesInDeadlock));
    }

    @Subscribe
    public void onProcessBlocked(ProcessBlockedEvent event) {
        if (blocked.containsKey(event.getResourceTried())) {
            blocked.get(event.getResourceTried()).add(event.getProcess());
        } else {
            List<ProcessInfo> list = new ArrayList<>();
            list.add(event.getProcess());

            blocked.put(event.getResourceTried(), list);
        }
    }

    @Subscribe
    public void onRemovedProcess(ProcessRemovedEvent event) {
        for (List<ProcessInfo> processes: blocked.values()) {
            processes.remove(event.getProcess());
        }
    }

    @Subscribe
    public void onAcquireResource(ProcessAcquiredResource event) {
        ResourceInfo resource = event.getResourceAllocation().getResource();
        ProcessInfo process = event.getResourceAllocation().getProcess();

        if (blocked.containsKey(resource)) {
            blocked.get(resource).remove(process);
        }
    }

}
