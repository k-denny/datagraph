package com.datagraph.core;

import com.datagraph.common.exception.JobException;
import com.datagraph.common.Context;
import com.datagraph.core.common.ContextImpl;
import com.datagraph.common.cons.DBType;
import com.datagraph.core.db.datasource.DBDetail;
import com.datagraph.core.db.datasource.DatabaseManager;
import com.datagraph.core.engine.job.Manager;
import com.datagraph.core.engine.scheduler.Scheduler;
import com.datagraph.core.utils.Config;
import org.apache.commons.configuration.ConfigurationException;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by Denny Joseph on 6/4/16.
 */
public class Bootstrap {

    public Bootstrap() {

    }

    public void start() {

        //get all configuration data from properties file

        try {

            // load properties file
            Config config = new Config("datagraph.properties");
            DBDetail dbDetail = config.getDBDetail();

            DatabaseManager dbManager = DatabaseManager.getInstance();
            dbManager.createDataSource("DATAGRAPH",dbDetail);

            DBDetail toplist = new DBDetail("jdbc:mysql://localhost:3306/helloworld",
                    "root","password", "com.mysql.jdbc.Driver",2,DatabaseManager.DBType.MYSQL);
            dbManager.createDataSource("TOPLIST", toplist);

            //Create Context object
            Context context = new ContextImpl(dbManager, DBType.JDBI);

            //Start Manager
            Manager manager = new Manager(context);
            manager.loadJobs();
            manager.start();

            // start scheduler as daemon
            int sleepDelay = 1;
            //ExecutorService schedulerExec = Executors.newFixedThreadPool(1, new ThreadFactoryBuilder().setDaemon(true).build());
            Scheduler scheduler = new Scheduler(manager, sleepDelay);
            ExecutorService schedulerExec = Executors.newSingleThreadExecutor();
            schedulerExec.submit(scheduler);
            schedulerExec.shutdown();


        } catch (Exception e) {
            System.out.println(e);
        }

        // create context object and point to taskmanager
        //start

    }

    public static void main(String[] args) {
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.start();
    }

}
