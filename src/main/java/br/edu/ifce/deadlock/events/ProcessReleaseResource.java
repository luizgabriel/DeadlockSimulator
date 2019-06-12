package br.edu.ifce.deadlock.events;

import br.edu.ifce.deadlock.models.ProcessInfo;
import br.edu.ifce.deadlock.models.ProcessWithResource;
import br.edu.ifce.deadlock.models.ResourceInfo;

public class ProcessReleaseResource {

    private final ProcessWithResource processWithResource;

    public ProcessReleaseResource(ProcessWithResource processWithResource) {
        this.processWithResource = processWithResource;
    }

    public ProcessWithResource getProcessWithResource() {
        return processWithResource;
    }
}
