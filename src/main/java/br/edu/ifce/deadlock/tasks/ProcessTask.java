package br.edu.ifce.deadlock.tasks;

import br.edu.ifce.deadlock.ApplicationManager;
import br.edu.ifce.deadlock.events.*;
import br.edu.ifce.deadlock.models.ProcessInfo;
import br.edu.ifce.deadlock.models.ResourceAllocation;
import br.edu.ifce.deadlock.models.ResourceInfo;

import java.util.ArrayList;
import java.util.ConcurrentModificationException;

public class ProcessTask implements Runnable {
    private ProcessInfo process;
    private boolean running;

    private final ApplicationManager manager;
    private final EventBus eventBus;
    private final ArrayList<ResourceAllocation> resourcesAllocations;
    private int waitTime;

    public ProcessTask(ProcessInfo process) {
        this.process = process;
        this.resourcesAllocations = new ArrayList<>();
        this.running = true;
        this.waitTime = 0;

        this.manager = ApplicationManager.getInstance();
        this.eventBus = EventBus.getInstance();
    }

    public void stop() {
        this.running = false;

        try {
            clear();
        } catch (ConcurrentModificationException e) {}
    }

    @Override
    public void run() {
        while (this.running) {

            if (waitTime >= process.getDts()) {
                requestRandomResource();
                waitTime = 0;
            }

            updateResourcesAllocationsTable();

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            waitTime += 1;
            eventBus.dispatch(new RefreshData());
        }

        clear();
    }

    private void requestRandomResource() {
        ResourceInfo resource = manager.getRandomResource();
        if (resource != null) {
            ResourceAllocation holder = manager.requestResource(process, resource);
            resourcesAllocations.add(holder);
        }
    }

    private void updateResourcesAllocationsTable() {
        ArrayList<ResourceAllocation> toRemove = new ArrayList<>();

        for (ResourceAllocation holder : resourcesAllocations) {
            holder.incrementUsageTime();

            if (holder.hasFinished()) {
                manager.releaseResource(holder);
                toRemove.add(holder);
            }
        }
        resourcesAllocations.removeAll(toRemove);

        if (resourcesAllocations.size() > 0) {
            eventBus.dispatch(new ProcessUpdatedStatus(process, String.format("Utilizando %d recursos ", resourcesAllocations.size())));
        }
    }

    private void clear() {
        for (ResourceAllocation holder : resourcesAllocations) {
            manager.releaseResource(holder);
        }

        resourcesAllocations.clear();
        eventBus.dispatch(new RefreshData());
    }

}