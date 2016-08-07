package com.datagraph.core.engine.scheduler;

import com.datagraph.core.engine.job.JobManager;
import com.datagraph.core.engine.job.Manager;

import java.util.List;

/**
 * Created by Denny Joseph on 6/4/16.
 */
public class Scheduler implements Runnable {

    private Manager manager;
    private volatile boolean stopped = false;
    private int sleepDelay = 1; // in seconds
    private long lastCheck = System.currentTimeMillis();

    public Scheduler(Manager manager, int sleepDelay) {
        this.manager = manager;
        this.stopped = false;
        this.sleepDelay = sleepDelay;
        this.lastCheck = System.currentTimeMillis();
    }

    public void stop() {
        this.stopped = true;
    }

    public void restart() {
        this.stopped = false;
        this.lastCheck = System.currentTimeMillis();
    }

    @Override
    public void run() {
        try {
            while (!stopped) {
                List<JobManager> jobManagers = manager.getJobList();
                System.out.println("scheduler running ");
                for(JobManager jobManager : jobManagers) {
                    if(jobManager.canStart())
                        jobManager.start();
                }
                Thread.sleep(sleepDelay * 1000);
            }
        }catch(Throwable e) {
            System.out.println(e);
        }

    }
}
