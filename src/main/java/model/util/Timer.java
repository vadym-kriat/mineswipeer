package model.util;

import javafx.concurrent.Task;
import model.interfaces.TimeListener;

import java.util.concurrent.TimeUnit;

public class Timer {

    private TimeListener timeListener;
    private int second;
    private boolean flag;
    private static final int TIME_OUT = 1;
    private Thread task;

    public Timer() {
        second = 0;
        flag = true;
        initTask();
    }

    private void initTask() {
        task = new Thread(new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                while (true) {
                    if (flag) {
                        timeListener.tick(second++);
                        TimeUnit.SECONDS.sleep(TIME_OUT);
                    } else {
                        return null;
                    }
                }
            }
        });
        task.setDaemon(true);
    }

    public void setTimeListener(TimeListener timeListener) {
        this.timeListener = timeListener;
    }

    public void stop() {
        flag = false;
    }

    public void start() {
        task.start();
    }
}
