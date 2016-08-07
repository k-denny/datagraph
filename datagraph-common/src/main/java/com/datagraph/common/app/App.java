package com.datagraph.common.app;

import com.datagraph.common.Context;
import com.datagraph.common.api.Job;

/**
 * Created by Denny Joseph on 4/22/16.
 */
public interface App {

    public Job execute(Context context, Job job) throws Exception;

}
