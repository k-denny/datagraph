package com.datagraph.core.engine.tasks;

import com.datagraph.common.api.Operator;
import com.datagraph.common.cons.TASKSTATE;

import java.util.List;

/**
 * Created by Denny Joseph on 6/15/16.
 */
public class Task {

    private int id;
    private long jobId;
    private int numTries = 1;
    private String name = "";
    private String fullName = "";
    private List<Operator> dependencies;
    private long retryWait = 1; // in seconds
    private String email;
    private long startTime;
    private long endTime;
    private int retryCount = 0;
    private boolean isRetry = false;
    private TASKSTATE state;

    public Task(String name, List<Operator> dependencies) {
        this.dependencies = dependencies;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getJobId() {
        return jobId;
    }

    public void setJobId(long jobId) {
        this.jobId = jobId;
    }

    public int getNumTries() {
        return numTries;
    }

    public void setNumTries(int numTries) {
        this.numTries = numTries;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public List<Operator> getDependencies() {
        return dependencies;
    }

    public void setDependencies(List<Operator> dependencies) {
        this.dependencies = dependencies;
    }

    public long getRetryWait() {
        return retryWait;
    }

    public void setRetryWait(long retryWait) {
        this.retryWait = retryWait;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public long getEndTime() {
        return endTime;
    }

    public void setEndTime(long endTime) {
        this.endTime = endTime;
    }

    public int getRetryCount() {
        return retryCount;
    }

    public void setRetryCount(int retryCount) {
        this.retryCount = retryCount;
    }

    public boolean isRetry() {
        return isRetry;
    }

    public void setRetry(boolean retry) {
        isRetry = retry;
    }

    public TASKSTATE getState() {
        return state;
    }

    public void setState(TASKSTATE state) {
        this.state = state;
    }
}
