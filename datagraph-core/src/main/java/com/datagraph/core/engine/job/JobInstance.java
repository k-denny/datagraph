package com.datagraph.core.engine.job;

import com.datagraph.common.api.Operator;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Denny Joseph on 6/14/16.
 */
public class JobInstance {

    private long id;
    private String name;
    private List<Operator> operators;
    private String schedule;
    private int state;
    private long lastTimeStarted;
    private long lastTimeEnded;
    private long timeStarted;
    private long timeEnded;
    private int backfill = 0;

    public JobInstance(String name) {
        this.name = name;
        operators = new ArrayList<>();
    }

    public JobInstance(long id, String name, String schedule, int state, long lastTimeStarted, long timeStarted, long lastTimeEnded, long timeEnded) {
        this(name);
        this.id = id;
        this.schedule = schedule;
        this.state = state;
        this.lastTimeStarted = lastTimeStarted;
        this.timeStarted = timeStarted;
        this.lastTimeEnded = lastTimeEnded;
        this.timeEnded = timeEnded;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void addOperator(Operator operator) {
        operators.add(operator);
    }

    public List<Operator> getOperators() {
        return operators;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSchedule() {
        return schedule;
    }

    public void setSchedule(String schedule) {
        this.schedule = schedule;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public long getLastTimeStarted() {
        return lastTimeStarted;
    }

    public void setLastTimeStarted(long lastTimeStarted) {
        this.lastTimeStarted = lastTimeStarted;
    }

    public long getLastTimeEnded() {
        return lastTimeEnded;
    }

    public void setLastTimeEnded(long lastTimeEnded) {
        this.lastTimeEnded = lastTimeEnded;
    }

    public long getTimeStarted() {
        return timeStarted;
    }

    public void setTimeStarted(long timeStarted) {
        this.timeStarted = timeStarted;
    }

    public long getTimeEnded() {
        return timeEnded;
    }

    public void setTimeEnded(long timeEnded) {
        this.timeEnded = timeEnded;
    }

    public int getBackfill() {
        return backfill;
    }

    public void setBackfill(int backfill) {
        this.backfill = backfill;
    }

    @Override
    public String toString() {
        return "JobInstance{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", operators=" + operators +
                ", schedule='" + schedule + '\'' +
                ", state=" + state +
                ", lastTimeStarted=" + lastTimeStarted +
                ", lastTimeEnded=" + lastTimeEnded +
                ", timeStarted=" + timeStarted +
                ", timeEnded=" + timeEnded +
                '}';
    }
}
