package com.zaloni.dao;
import com.zaloni.Database.*;
import com.zaloni.models.Employee;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;



public class EmployeeQuery {
	String InsertEmployee = "insert into Employee values(?,?)";
	String getEmployee = "select * from Employee ";
	public void update(String Name, String Age) throws SQLException
	{
		Connection con = JdbcConnect.getCon();
		String UpdateEmployee = "update Employee set Age = ? where Name = ?";
		PreparedStatement ps = (PreparedStatement) con.prepareStatement(UpdateEmployee);
	    ps.setString(1, Age);
	    ps.setString(2, Name);
	    ps.executeUpdate();
	}
	public void setEmployee(String Name, String Age) throws SQLException
	{   Connection con = JdbcConnect.getCon();
		PreparedStatement ps = (PreparedStatement) con.prepareStatement(InsertEmployee);
	    ps.setString(1, Name);
	    ps.setString(2, Age);
	    ps.executeUpdate();
	}
	public List<Employee> getAllEmployee() throws SQLException
	{
		List<Employee> ls = new ArrayList<Employee>();
		Connection con = JdbcConnect.getCon();
        Statement ps = con.createStatement();
        ResultSet rs = ps.executeQuery(getEmployee);
        while(rs.next())
        {
        	Employee emp = new Employee();
        	emp.setName(rs.getString(1));
        	emp.setAge(rs.getString(2));
        	ls.add(emp);
        }
        return ls;
	}
}
