package br.edu.ifce.deadlock.tasks;

import br.edu.ifce.deadlock.events.EventBus;
import br.edu.ifce.deadlock.events.OSDeltaTimeUpdated;
import com.google.common.eventbus.Subscribe;

public class OSSimulationTask implements Runnable {

    private final DeadlockDetector deadlockDetector;
    private int deltaTime;

    public OSSimulationTask(int deltaTime) {
        this.deltaTime = deltaTime;
        this.deadlockDetector = new DeadlockDetector();

        EventBus.getInstance().subscribe(this);
        EventBus.getInstance().dispatch(new OSDeltaTimeUpdated(deltaTime));
    }

    @Subscribe
    void onChangeDeltaTime(OSDeltaTimeUpdated event) {
        deltaTime = event.getTime();
    }

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(deltaTime * 1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            deadlockDetector.check();
        }
    }


}
