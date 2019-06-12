package br.edu.ifce.deadlock.models;

import java.util.UUID;

public class ResourceAllocation {

    private final int handle;
    private final ProcessInfo process;
    private final ResourceInfo resource;
    private int usageTime;

    public ResourceAllocation(ProcessInfo process, ResourceInfo resource) {
        this.handle = UUID.randomUUID().hashCode();
        this.process = process;
        this.resource = resource;
        this.usageTime = 0;
    }

    public void incrementUsageTime() {
        usageTime++;
    }

    public int getUsageTime() {
        return usageTime;
    }

    public String getUsageProgress() {
        return usageTime + "/" + process.getDtu();
    }

    public int getHandle() {
        return handle;
    }

    public ProcessInfo getProcess() {
        return process;
    }

    public ResourceInfo getResource() {
        return resource;
    }

    public String getProcessName() {
        return process.getName();
    }

    public String getResourceName() {
        return resource.getName();
    }

    public boolean hasFinished() {
        return getUsageTime() >= getProcess().getDtu();
    }

    @Override
    public boolean equals(Object obj) {
        return getHandle() == ((ResourceAllocation) obj).getHandle();
    }
}
