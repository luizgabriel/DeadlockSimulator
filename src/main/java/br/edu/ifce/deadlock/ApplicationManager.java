package br.edu.ifce.deadlock;

import br.edu.ifce.deadlock.events.*;
import br.edu.ifce.deadlock.models.ProcessInfo;
import br.edu.ifce.deadlock.models.ProcessWithResource;
import br.edu.ifce.deadlock.models.ResourceInfo;
import br.edu.ifce.deadlock.tasks.ProcessTask;

import javax.annotation.Nullable;
import java.util.*;
import java.util.concurrent.Semaphore;

public class ApplicationManager {

    private static ApplicationManager instance;

    private final EventBus eventBus;


    private final ArrayList<ResourceInfo> resources;
    private final Map<ProcessInfo, ProcessTask> processTasks;
    private final Map<ResourceInfo, Semaphore> resourcesSemaphores;

    private ApplicationManager() {
        this.resources = new ArrayList<>();
        this.processTasks = new HashMap<>();
        this.resourcesSemaphores = new HashMap<>();

        this.eventBus = EventBus.getInstance();
    }

    @Nullable
    public ResourceInfo getRandomResource() {
        Random rand = new Random();

        if (resources.size() > 0)
            return resources.get(rand.nextInt(resources.size()));
        else
            return null;
    }

    public void addProcess(ProcessInfo process) {
        ProcessTask task = new ProcessTask(process);

        Thread thread = new Thread(task);
        thread.start();

        this.processTasks.put(process, task);

        eventBus.dispatch(new ProcessCreatedEvent(process));
    }

    public void addResource(ResourceInfo resource) {
        this.resources.add(resource);
        this.resourcesSemaphores.put(resource, new Semaphore(resource.getQtd()));

        eventBus.dispatch(new ResourceCreatedEvent(resource));
    }

    public static ApplicationManager getInstance() {
        if (instance == null)
            instance = new ApplicationManager();

        return instance;
    }

    public ProcessWithResource requestResource(ProcessInfo process, ResourceInfo resource) {
        Semaphore semaphore = this.resourcesSemaphores.get(resource);

        if (semaphore.availablePermits() == 0) {
            eventBus.dispatch(new ProcessBlockedEvent(process, resource));
            eventBus.dispatch(new ProcessUpdatedStatus(process, "Processo bloqueado ao solicitar " + resource.getName()));
        }

        try {
            semaphore.acquire();
            resource.subtractQtd();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ProcessWithResource processWithResource = new ProcessWithResource(process, resource);
        eventBus.dispatch(new ProcessAcquiredResource(processWithResource));

        return processWithResource;
    }

    public void releaseResource(ProcessWithResource processWithResource) {
        Semaphore semaphore = this.resourcesSemaphores.get(processWithResource.getResource());
        semaphore.release();
        processWithResource.getResource().incrementQtd();

        eventBus.dispatch(new ProcessReleaseResource(processWithResource));
    }

    public void removeProcess(ProcessInfo processInfo) {
        ProcessTask task = processTasks.get(processInfo);
        if (task != null) {
            task.stop();
            processTasks.remove(processInfo);

            eventBus.dispatch(new ProcessRemovedEvent(processInfo));
        }
    }
}
