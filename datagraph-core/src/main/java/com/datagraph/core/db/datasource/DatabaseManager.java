package com.datagraph.core.db.datasource;

import org.apache.commons.dbcp2.BasicDataSource;

import javax.sql.DataSource;
import javax.xml.crypto.Data;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Denny Joseph on 6/12/16.
 */
public class DatabaseManager {

    public enum DBType {
        MYSQL
    }

    private static Map<String, DataSource> dsMap = new ConcurrentHashMap<>();

    protected DatabaseManager() {
        dsMap = new ConcurrentHashMap<>();
    }
    private static DatabaseManager dbManager = new DatabaseManager();

    public static DatabaseManager getInstance() {
        return dbManager;
    }


    public void createDataSource(String name, DBDetail detail) {

        if(detail.getType() == DBType.MYSQL) {
            BasicDataSource ds = new BasicDataSource();
            ds.setDriverClassName(detail.getDriverClass());
            ds.setUrl(detail.getUrl());
            ds.setUsername(detail.getUsername());
            ds.setPassword(detail.getPassword());
            ds.setInitialSize(detail.getPoolSize());
            dsMap.put(name,ds);
        }

    }

    public DataSource getDataSource(String dsName) {
        return dsMap.get(dsName);
    }


}
