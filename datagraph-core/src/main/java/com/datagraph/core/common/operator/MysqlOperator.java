package com.datagraph.core.common.operator;

import com.datagraph.common.Context;
import com.datagraph.common.api.Operator;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

/**
 * Created by Denny Joseph on 4/24/16.
 */
public class MysqlOperator extends BaseOperator {

    DataSource dataSource;
    private String query;

    public MysqlOperator(Context context, String name, int numTries, List<Operator> dependency, int retryWait, String email,
                         String query, String datasource){
        super(context, name, dependency, numTries, retryWait, email);
        dataSource = getContext().getDataSource(datasource);
        this.query = query;
    }

    public int execute() throws Exception{
        try {
            Connection conn = dataSource.getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(this.query);
            while(rs.next()){
                //Retrieve by column name
                int id  = rs.getInt("category_id");
                String first = rs.getString("name");

                //Display values
                System.out.print("ID: " + id);
                System.out.print(", name: " + first);

            }
        }catch (Exception e) {
            System.out.println(e);
            throw e;
        }
        return 0;

    }
}
