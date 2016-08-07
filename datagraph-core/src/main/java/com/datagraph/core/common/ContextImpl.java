package com.datagraph.core.common;

import com.datagraph.common.Context;
import com.datagraph.common.cons.DBType;
import com.datagraph.core.db.datasource.DatabaseManager;

import javax.sql.DataSource;

/**
 * Created by Denny Joseph on 6/4/16.
 */
public class ContextImpl extends Context {

    private DatabaseManager dbManager;
    private DBType dbType;

    public ContextImpl(DatabaseManager dbManager, DBType dbType) {
        this.dbManager = dbManager;
        this.dbType = dbType;
    }

    @Override
    public DataSource getDataSource(String name) {
        return dbManager.getDataSource(name);
    }

    public DBType getDBType() {
        return dbType;
    }


}
