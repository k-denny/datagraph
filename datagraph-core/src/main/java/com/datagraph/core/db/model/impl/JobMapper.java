package com.datagraph.core.db.model.impl;

import com.datagraph.core.engine.job.JobInstance;
import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Denny Joseph on 6/19/16.
 */
public class JobMapper implements ResultSetMapper<JobInstance>
{
    public JobInstance map(int index, ResultSet r, StatementContext ctx) throws SQLException
    {
        JobInstance jobInstance = new JobInstance(r.getLong("id"), r.getString("name"), r.getString("schedule"), r.getInt("state"), r.getLong("lastTimeStarted"),
                r.getLong("lastTimeEnded"), r.getLong("timeStarted"), r.getLong("timeEnded"));

        return jobInstance;

    }
}