package com.datagraph.core.db;

import com.datagraph.core.db.model.JobDao;
import com.datagraph.core.db.model.impl.JobJDBIDao;
import org.skife.jdbi.v2.DBI;

import javax.sql.DataSource;

/**
 * Created by Denny Joseph on 6/12/16.
 */
public class DaoJDBIFactory extends DaoFactory {

    private DataSource dataSource;

    public DaoJDBIFactory(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public JobDao getJobDao() {
        DBI dbi = new DBI(this.dataSource);
        return dbi.open(JobJDBIDao.class);
    }
}
