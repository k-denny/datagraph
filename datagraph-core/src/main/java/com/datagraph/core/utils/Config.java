package com.datagraph.core.utils;

import com.datagraph.core.db.datasource.DBDetail;
import com.datagraph.core.db.datasource.DatabaseManager;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;

/**
 * Created by Denny Joseph on 6/18/16.
 */
public class Config {

    private String propertyFile;
    private PropertiesConfiguration config;

    public Config(String propertyFile) throws ConfigurationException{
        this.propertyFile = propertyFile;
        config = new PropertiesConfiguration(propertyFile);
    }

    public DBDetail getDBDetail() {
        String dbUrl = config.getString("datagraph.datasource.url");
        String username = config.getString("datagraph.datasource.username");
        String password = config.getString("datagraph.datasource.password");
        String driverClassName = config.getString("datagraph.datasource.driverClassName");
        int poolSize = config.getInt("datagraph.datasource.poolSize");

        DBDetail dbDetail = new DBDetail(dbUrl, username, password, driverClassName, poolSize, DatabaseManager.DBType.MYSQL);
        return dbDetail;

    }

}
