package com.datagraph.common.api;

import com.datagraph.common.cons.TASKSTATE;

import java.util.List;
import java.util.concurrent.Callable;

/**
 * Created by Denny Joseph on 5/4/16.

 This interface is used by operator to store its
 run and other details in db

 */


public interface Operator extends Callable<TASKSTATE>{

    public List<Operator> getDependencies();

    public TASKSTATE getState();

    public void setName(String name);

    public String getName();

}
