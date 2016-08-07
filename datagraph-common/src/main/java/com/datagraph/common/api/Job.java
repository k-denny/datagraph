package com.datagraph.common.api;

/**
 * Created by Denny Joseph on 5/11/16.
 */
public interface Job{

    public void addOperator(Operator operator);

    public void setSchedule(String cronExpression);

    public static final int RUNNING = 1;
    public static final int FAILED = -1;
    public static final int KILLED = 2, ABANDONED = 3, STOPPED = 0, SUCCEEDED = 4;

}
