package br.edu.ifce.deadlock.models;

public class ProcessInfo {
    private String name;
    private int dts;
    private int dtu;

    public ProcessInfo(String name, int dts, int dtu) {
        this.name = name;
        this.dts = dts;
        this.dtu = dtu;
    }

    public String getName() {
        return name;
    }

    public int getDts() {
        return dts;
    }

    public int getDtu() {
        return dtu;
    }
}
