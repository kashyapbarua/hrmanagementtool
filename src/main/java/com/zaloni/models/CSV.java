package com.zaloni.models;

import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;

import com.zaloni.dao.EmployeeQuery;

public class CSV {
	public void generate() throws SQLException, IOException
	{
		EmployeeQuery emp = new EmployeeQuery();
		List<Employee> emplist = emp.getAllEmployee();
		FileWriter fw = new FileWriter("e:\\myjdbcfile.csv");
		Iterator<Employee> it = emplist.iterator();
		 fw.append("Name");
		  fw.append(',');
		  fw.append("Age");
		  fw.append('\n');
		while(it.hasNext())
		  {
			Employee temp = (Employee) it.next();
		  fw.append(temp.getName());
		  fw.append(',');
		  fw.append(temp.getAge());
		  fw.append('\n');
		  }
		fw.flush();
		  fw.close();
		  System.out.println("CSV File is created successfully.");
	}
  public static void main(String [] args) throws SQLException, IOException
  {
	  CSV cs = new CSV();
	  cs.generate();
  }
}
