package com.datagraph.core.db.model.impl;

import com.datagraph.core.db.model.TaskDao;
import com.datagraph.core.engine.tasks.Task;

/**
 * Created by Denny Joseph on 6/17/16.
 */
public class TaskJDBIDao implements TaskDao {

    @Override
    public int create(Task task) {
        return 0;
    }

    @Override
    public boolean update(Task task) {
        return false;
    }

    @Override
    public Task find(long id) {
        return null;
    }

    @Override
    public boolean delete(long id) {
        return false;
    }
}
