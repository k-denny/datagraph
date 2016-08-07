package com.datagraph.core.common.operator;

import com.datagraph.common.Context;
import com.datagraph.common.api.Operator;

import java.util.List;

/**
 * Created by Denny Joseph on 5/12/16.
 */
public class DummyOperator extends BaseOperator {

    public DummyOperator(Context context, String name, List<Operator> dependencies) {
        super(context, name, dependencies);
    }

    @Override
    public int execute() {
        System.out.println("In Dummy Operator: " + name + ", State: " + getState());
        return 0;
    }
}
