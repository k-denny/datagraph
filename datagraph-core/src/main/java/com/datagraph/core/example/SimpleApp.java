package com.datagraph.core.example;

import com.datagraph.common.Context;
import com.datagraph.common.api.Job;
import com.datagraph.common.api.Operator;
import com.datagraph.common.app.App;
import com.datagraph.core.common.operator.DummyOperator;
import com.datagraph.core.common.operator.MysqlOperator;

import java.util.Arrays;

/**
 * Created by Denny Joseph on 4/22/16.
 */

public class SimpleApp implements App {

    @Override
    public Job execute(Context context, Job job) {

        job.setSchedule("* * * * *");

        Operator truncate = new DummyOperator(context, "truncate", Arrays.asList());
        job.addOperator(truncate);

        Operator getCount = new MysqlOperator(context, "get_count", 3, Arrays.asList(truncate),10, "ttt","select * from category", "TOPLIST");
        job.addOperator(getCount);

        Operator getCount1 = new DummyOperator(context, "get_count_1", Arrays.asList(truncate));
        job.addOperator(getCount1);

        Operator email_operator = new DummyOperator(context, "email_users", Arrays.asList(getCount, getCount1));
        job.addOperator(email_operator);

        return job;
    }

}
