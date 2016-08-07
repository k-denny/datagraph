package com.datagraph.core.db.model.impl;

import com.datagraph.core.db.model.JobDao;
import com.datagraph.core.engine.job.JobInstance;
import org.skife.jdbi.v2.sqlobject.*;
import org.skife.jdbi.v2.sqlobject.customizers.Mapper;

/**
 * Created by Denny Joseph on 6/17/16.
 */
public interface JobJDBIDao extends JobDao {

    @SqlUpdate("insert into jobInstance (name, schedule, state, lastTimeStarted, lastTimeEnded, timeStarted, timeEnded) " +
            "values (:name, :schedule, :state, :lastTimeStarted, :lastTimeEnded, :timeStarted, :timeEnded)")
    @GetGeneratedKeys
    long insert(@BindBean JobInstance jobInstance);

    @SqlUpdate("update jobInstance set state = :state, lastTimeEnded = :lastTimeEnded where id = :id ")
    void update(@BindBean JobInstance jobInstance);

    @Override
    @SqlQuery("select jobInstance.id as id, name, schedule, state, lastTimeStarted, lastTimeEnded, timeStarted, " +
            "timeEnded from jobInstance, (select max(id) as id from jobInstance where name = :name group by name) maxjob" +
            " where jobInstance.id = maxjob.id")
    @Mapper(JobMapper.class)
    JobInstance findLatestByName(@Bind("name") String name);

    @Override
    void close();

}
