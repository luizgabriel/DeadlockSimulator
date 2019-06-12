package br.edu.ifce.deadlock.tasks;

import br.edu.ifce.deadlock.ApplicationManager;
import br.edu.ifce.deadlock.events.EventBus;
import br.edu.ifce.deadlock.events.ProcessUpdatedStatus;
import br.edu.ifce.deadlock.events.RefreshData;
import br.edu.ifce.deadlock.models.ProcessInfo;
import br.edu.ifce.deadlock.models.ProcessWithResource;
import br.edu.ifce.deadlock.models.ResourceInfo;

public class ProcessUsingResourceTask implements Runnable {
    private final EventBus eventBus;
    private final ApplicationManager manager;
    private final ProcessTask parent;
    private final ProcessWithResource holder;

    public ProcessUsingResourceTask(ProcessTask parent, ProcessWithResource holder) {
        this.parent = parent;
        this.holder = holder;
        this.eventBus = EventBus.getInstance();
        this.manager = ApplicationManager.getInstance();
    }

    @Override
    public void run() {
        while (!holder.hasFinished() && parent.isRunning()) {
            eventBus.dispatch(new ProcessUpdatedStatus(holder.getProcess(), "Utilizando o recurso " + holder.getResourceName()));

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            holder.incrementUsageTime();
            eventBus.dispatch(new RefreshData());
        }

        manager.releaseResource(holder);
    }
}
