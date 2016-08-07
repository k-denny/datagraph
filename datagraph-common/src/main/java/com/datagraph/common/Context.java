package com.datagraph.common;

import com.datagraph.common.cons.DBType;

import javax.sql.DataSource;

/**
 * Created by Denny Joseph on 6/4/16.
 *
 * this class provides the global state
 *
 */
public abstract class Context {

    public abstract DataSource getDataSource(String name);

    public abstract DBType getDBType() ;

}
