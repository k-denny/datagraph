package com.datagraph.common.api;

import java.util.List;

/**
 * Created by Denny Joseph on 4/27/16.
 */
public interface DAG {

    public List<Operator> getNextTasks();

}
