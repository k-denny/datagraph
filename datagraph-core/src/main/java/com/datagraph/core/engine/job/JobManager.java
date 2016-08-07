package com.datagraph.core.engine.job;

import com.cronutils.model.CronType;
import com.cronutils.model.definition.CronDefinitionBuilder;
import com.cronutils.model.time.ExecutionTime;
import com.cronutils.parser.CronParser;
import com.datagraph.common.api.Job;
import com.datagraph.common.api.Operator;
import com.datagraph.common.cons.TASKSTATE;
import com.datagraph.common.exception.JobException;
import com.datagraph.common.Context;
import com.datagraph.core.common.operator.BaseOperator;
import com.datagraph.core.db.DaoFactory;
import com.datagraph.core.db.model.JobDao;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;
import org.joda.time.DateTime;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by Denny Joseph on 6/13/16.
 */
public class JobManager extends DAG implements com.datagraph.common.api.Job {

    private CronParser parser;
    private ExecutorService taskExec;
    private ListeningExecutorService pool;
    private JobInstance jobInstance;
    private DaoFactory factory;

    public JobManager(Context context, String appName) {
        factory = DaoFactory.getInstance(context);
        this.jobInstance = factory.getJobDao().findLatestByName(appName);

        if(this.jobInstance == null)
            this.jobInstance = new JobInstance(appName);
        System.out.println(this.jobInstance);
        this.parser = new CronParser(CronDefinitionBuilder.instanceDefinitionFor(CronType.UNIX));
        this.pool =  MoreExecutors.listeningDecorator(Executors.newFixedThreadPool(10));
    }

    public JobInstance getJobInstance() {
        return jobInstance;
    }

    public void build() throws JobException {
        for(Operator operator : jobInstance.getOperators()) {
            String finalName = jobInstance.getName() + "." + operator.getName();
            operator.setName(finalName);
            System.out.println(operator.getName());
            buildDag(operator);
        }
        System.out.println(this.graph);
    }

    private void buildDag(Operator operator) throws JobException {
        for(Operator dependency : operator.getDependencies()){
            addEdge(dependency, operator);
        }
    }

    public boolean canStart() {
        if(jobInstance.getState() == Job.RUNNING)
            return false;
        //this.state = WAIT
        DateTime now = DateTime.now();
        ExecutionTime executionTime = ExecutionTime.forCron(parser.parse(jobInstance.getSchedule()));
        //this jobInstance never ran before
        if(jobInstance.getLastTimeStarted() == 0) {
            jobInstance.setLastTimeStarted(executionTime.lastExecution(now).getMillis());
            jobInstance.setLastTimeEnded(jobInstance.getLastTimeStarted());
        }
        //System.out.println("last: " + jobInstance.getLastTimeStarted() + ":" + new DateTime(jobInstance.getLastTimeStarted()));
        //Get date for next execution
        DateTime nextExecution = executionTime.nextExecution(new DateTime(jobInstance.getLastTimeStarted()));

        if(nextExecution.getMillis() <= now.getMillis()) {
            if(jobInstance.getBackfill() == 1)
                jobInstance.setLastTimeStarted(nextExecution.getMillis());
            else
                jobInstance.setLastTimeStarted(System.currentTimeMillis());
            System.out.println(new DateTime(jobInstance.getLastTimeStarted()));
            return true;
        }
        return false;

    }

    public void start() throws JobException{
        System.out.println("Starting JobInstance: " + jobInstance.getName());
        jobInstance.setTimeStarted(System.currentTimeMillis());
        jobInstance.setState(Job.RUNNING);
        Set<Operator> operators = getTop(this.graph);
        JobDao jobDao = factory.getJobDao();
        long id = jobDao.insert(jobInstance);
        jobDao.close();
        jobInstance.setId(id);
        System.out.println("Dag: " + this.graph);
        submit(operators);
    }


    private void submit(Set<Operator> operators) {
        for(final Operator operator : operators) {
            ((BaseOperator) operator).reset();
            System.out.println("Starting Operator : " + operator);
            final ListenableFuture<TASKSTATE> future = this.pool.submit(operator);
            future.addListener(()->{
                System.out.println("Completed Operator : " + operator);
                stateChange(operator);
            }, MoreExecutors.directExecutor());
        }
    }

    private void stateChange(Operator operator) {
        if(operator.getState() == TASKSTATE.SUCCEEDED) {
            Set<Operator> operators = getEdges(operator);
            if(operators.size() > 0) {
                Set<Operator> nextRun = new HashSet<>();
                for (Operator eachEdge : operators) {
                    if (isTaskReadyToRun(eachEdge)) {
                        nextRun.add(eachEdge);
                    }
                }
                submit(nextRun);
            } else {
                jobInstance.setState(Job.SUCCEEDED);
                jobInstance.setLastTimeEnded(System.currentTimeMillis());
                JobDao jobDao = factory.getJobDao();
                jobDao.update(jobInstance);
                jobDao.close();

            }
        }
    }

    private boolean isTaskReadyToRun(Operator edge) {
        boolean canRun = true;
        Set<Operator> srcNodes = getDependencies(edge);
        for(Operator operator : srcNodes) {
            if(operator.getState() != TASKSTATE.SUCCEEDED)
                return false;
        }
        return true;
    }

    @Override
    public void addOperator(Operator operator) {
        this.jobInstance.addOperator(operator);
    }

    @Override
    public void setSchedule(String cronExpression) {
        jobInstance.setSchedule(cronExpression);
    }

}
