package br.edu.ifce.deadlock.tasks;

import br.edu.ifce.deadlock.events.*;
import br.edu.ifce.deadlock.models.ProcessInfo;
import com.google.common.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

public class DeadlockDetector {

    public DeadlockDetector() {
        EventBus.getInstance().subscribe(this);
    }

    public void check() {
        List<ProcessInfo> processes = new ArrayList<>();

        //processes.add(new ProcessInfo("A", 0, 0));
        //processes.add(new ProcessInfo("B", 0, 0));
        //processes.add(new ProcessInfo("C", 0, 0));

        if (processes.size() > 0)
            EventBus.getInstance().dispatch(new DeadlockDetectedEvent(processes));
    }

    @Subscribe
    public void onProcessAdded(ProcessCreatedEvent event) {
        //
    }

    @Subscribe
    public void onProcessRemoved(ProcessRemovedEvent event) {
        //
    }

    @Subscribe
    public void onProcessAcquiredResource(ProcessAcquiredResource event) {
        //
    }

    @Subscribe
    public void onProcessReleasedEvent(ProcessReleaseResource event) {
        //
    }

    @Subscribe
    public void onProcessBlocked(ProcessBlockedEvent event) {
        //
    }

}
