package com.datagraph.core.db.datasource;

/**
 * Created by Denny Joseph on 6/12/16.
 */
public class DBDetail {

    private String url;
    private String driverClass;
    private String username;
    private String password;
    private DatabaseManager.DBType type;
    private int poolSize = 1;

    public DBDetail(String url, String username, String password, String driverClass,
                    int poolSize, DatabaseManager.DBType type) {
        this.url = url;
        this.username = username;
        this.password = password;
        this.driverClass = driverClass;
        this.type = type;
        this.poolSize = poolSize;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public DatabaseManager.DBType getType() {
        return type;
    }

    public void setType(DatabaseManager.DBType type) {
        this.type = type;
    }

    public int getPoolSize() {
        return poolSize;
    }

    public void setPoolSize(int poolSize) {
        this.poolSize = poolSize;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDriverClass() {
        return driverClass;
    }

    public void setDriverClass(String driverClass) {
        this.driverClass = driverClass;
    }
}
