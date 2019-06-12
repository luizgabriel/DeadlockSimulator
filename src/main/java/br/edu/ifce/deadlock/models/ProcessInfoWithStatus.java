package br.edu.ifce.deadlock.models;

public class ProcessInfoWithStatus {
    private ProcessInfo processInfo;
    private String status;

    public ProcessInfoWithStatus(ProcessInfo processInfo, String status) {
        this.processInfo = processInfo;
        this.status = status;
    }

    public String getName() {
        return this.getProcessInfo().getName();
    }

    public int getDts() {
        return this.getProcessInfo().getDts();
    }

    public int getDtu() {
        return this.getProcessInfo().getDtu();
    }

    public ProcessInfo getProcessInfo() {
        return processInfo;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
