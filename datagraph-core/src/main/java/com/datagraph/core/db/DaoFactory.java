package com.datagraph.core.db;

import com.datagraph.common.cons.DBType;
import com.datagraph.common.Context;
import com.datagraph.core.db.model.JobDao;

import javax.sql.DataSource;

/**
 * Created by Denny Joseph on 6/11/16.
 */
public abstract class DaoFactory {

    public enum TYPE {
        JDBI, HIBERNATE
    }

    protected DataSource dataSource;

  public static DaoFactory getInstance(Context context) {
      DaoFactory daoFactory = null;

        if(context.getDBType() == DBType.JDBI) {
            daoFactory = new DaoJDBIFactory(context.getDataSource("DATAGRAPH"));
        }
      return daoFactory;

    }

    public abstract JobDao getJobDao();



}
