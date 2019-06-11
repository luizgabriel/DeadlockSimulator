package br.edu.ifce.deadlock.events;

public class OSDeltaTimeUpdated {
    private int time;

    public OSDeltaTimeUpdated(int time) {
        this.time = time;
    }

    public int getTime() {
        return time;
    }
}
