package br.edu.ifce.deadlock.tasks;

import br.edu.ifce.deadlock.ApplicationManager;
import br.edu.ifce.deadlock.events.*;
import br.edu.ifce.deadlock.models.ProcessInfo;
import br.edu.ifce.deadlock.models.ProcessWithResource;
import br.edu.ifce.deadlock.models.ResourceInfo;

public class ProcessTask implements Runnable {
    private ProcessInfo process;
    private boolean running;

    private final ApplicationManager manager;
    private final EventBus eventBus;

    public ProcessTask(ProcessInfo process) {
        this.process = process;
        this.running = true;

        this.manager = ApplicationManager.getInstance();
        this.eventBus = EventBus.getInstance();
    }

    public void stop() {
        this.running = false;
    }

    @Override
    public void run() {
        while (this.running) {
            eventBus.dispatch(new ProcessUpdatedStatus(process, "Esperando..."));

            try {
                Thread.sleep(process.getDts() * 1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            ResourceInfo resource = manager.getRandomResource();
            if (resource != null) {
                ProcessWithResource holder = manager.requestResource(process, resource);
                eventBus.dispatch(new ProcessUpdatedStatus(process, "Utilizando o recurso " + resource.getName()));

                while (holder.getUsageTime() < process.getDtu()) {
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
    }
}
