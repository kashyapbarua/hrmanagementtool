package com.zaloni.Database;

import java.sql.Connection;
import java.sql.DriverManager;

public class JdbcConnect {

	static Connection con = null;
    static
    {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/googledata","root","priya");
        }
        catch(Exception e)
        {
            System.out.println("Connection"+ e);
        }
    }
    
    public static Connection getCon()
    {
        return con;
    }
    
    
}
