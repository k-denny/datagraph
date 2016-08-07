package com.datagraph.core.engine.job;

import com.datagraph.common.app.App;
import com.datagraph.common.exception.JobException;
import com.datagraph.common.Context;
import com.datagraph.core.example.SimpleApp;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutorService;

/**
 * Created by Denny Joseph on 5/24/16.
 */
public class Manager implements Runnable{

    private List<JobManager> jobList = Collections.synchronizedList(new ArrayList<>());
    private ExecutorService executorService;
    private Context context;

    public Manager(Context context) {
        this.context = context;
    }

    public void loadJobs() throws Exception {
        App simpleApp = new SimpleApp();
        String appName = simpleApp.getClass().getCanonicalName();
        JobManager jobManager = new JobManager(context, appName);
        simpleApp.execute(context, jobManager);
        jobManager.build();
        jobList.add(jobManager);
    }

    public void start() {

    }

    public List<JobManager> getJobList() {
        return jobList;
    }

    @Override
    public void run() {
        System.out.println("Inside Manager. this will check for new jobs");
        System.out.println("if new jobs found it will call loadJobs methods");
    }
}
