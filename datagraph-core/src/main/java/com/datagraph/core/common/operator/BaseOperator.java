package com.datagraph.core.common.operator;

import com.datagraph.common.Context;
import com.datagraph.common.api.Job;
import com.datagraph.common.api.Operator;
import com.datagraph.common.cons.TASKSTATE;
import com.datagraph.core.engine.tasks.Task;

import java.util.List;

/**
 * Created by Denny Joseph on 5/11/16.
 */
public abstract class BaseOperator implements Operator {

    private int id;
    private int pipeline_id;
    protected String name = "";
    private Task task;
    private Context context;

    public BaseOperator(Context context, String name, List<Operator> dependencies, int numTries, long retryWait, String email) {
        this(context, name, dependencies);
        task.setRetryWait(retryWait*1000);
        task.setEmail(email);
        task.setNumTries(numTries);
    }

    public BaseOperator(Context context, String name, List<Operator> dependencies) {
        this.context = context;
        task = new Task(name, dependencies);
        this.name = name;
    }

    public BaseOperator(Task task) {
        this.task = task;
    }


    /*
    This method is called first time before the task is run
    during retry this method is not called.
     */

    public void init() {
        task.setRetryCount(0);
        task.setRetry(true);
    }

    /*
    This method is called every time before the task is run
    during retry this method is not called.
     */
    public void start() {
        task.setStartTime(System.currentTimeMillis());
        updateState(TASKSTATE.WAITING);
    }

    public void reset() {
        updateState(TASKSTATE.WAITING);
    }

    @Override
    public void setName(String name) {
        this.task.setName(name);
    }

    //template method as well as thread run method
    @Override
    public final TASKSTATE call() {
        int exitCode = 0;
        if(!task.isRetry())
            init();
        while(task.getRetryCount() < task.getNumTries() && task.isRetry()) {
            start();
            exitCode = 0;
            try {
                if(task.getRetryCount() > 0) { //this is second or more retry
                    Thread.sleep(task.getRetryWait());
                }
                updateState(TASKSTATE.RUNNING);
                exitCode = execute();
                stop(exitCode);
            }catch (Exception e) {
                System.out.println(e);
                stop(2);
                System.out.println("Retry Attempts left : " + (task.getNumTries()-task.getRetryCount()));
                System.out.println("Retrying in seconds: " + task.getRetryWait());
            }
        }
        destroy();
        return task.getState();
    }

    public abstract int execute() throws Exception;

    public void stop(int exitCode) {
        task.setEndTime(System.currentTimeMillis());
        if(exitCode == 0) {
            updateState(TASKSTATE.SUCCEEDED);
            task.setRetry(false);
        } else {
            task.setRetry(true);
            updateState(TASKSTATE.FAILED);
            task.setRetryCount(task.getRetryCount()+1);
            System.out.println("Data is : " + task.getRetryCount());
        }
    }

    public void destroy() {

    }


    @Override
    public List<Operator> getDependencies() {
        return task.getDependencies();
    }

    public void updateState(TASKSTATE state) {
        task.setState(state);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BaseOperator baseOperator = (BaseOperator) o;
        return task.getName().equals(baseOperator.task.getName());

    }

    @Override
    public int hashCode() {
        return task.getName().hashCode();
    }


    public TASKSTATE getState() {
        return task.getState();
    }

    public void setState(TASKSTATE state) {
        task.setState(state);
    }

    @Override
    public String getName() {
        return this.name;
    }

    public Context getContext() {
        return context;
    }

    @Override
    public String toString() {
        return "{" +
                "name='" + task.getName() + '\'' +
               // ", retryWait=" + retryWait +
               // ", email='" + email + '\'' +
               // ", startTime=" + startTime +
               // ", endTime=" + endTime +
               // ", retryCount=" + retryCount +
               ", state=" + task.getState() +
                "}\n";
    }

}
