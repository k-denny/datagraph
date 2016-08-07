package com.datagraph.core.db.model;

import com.datagraph.core.engine.tasks.Task;

/**
 * Created by Denny Joseph on 6/17/16.
 */
public interface TaskDao {

    public int create(Task task);

    public boolean update(Task task);

    public Task find(long id);

    public boolean delete(long id);

}
