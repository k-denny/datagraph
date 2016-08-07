package com.datagraph.core.db.model;

import com.datagraph.core.engine.job.JobInstance;

/**
 * Created by Denny Joseph on 6/17/16.
 */
public interface JobDao {

    public long insert(JobInstance jobInstance);

    public void update(JobInstance task);

    public JobInstance findLatestByName(String name);

    //public boolean delete(long id);

    public void close();

}
