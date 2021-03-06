package com.zaloni.GoogleSheets;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.sheets.v4.SheetsScopes;
import com.google.api.services.sheets.v4.model.*;
import com.google.gson.Gson;
import com.zaloni.Database.JdbcConnect;
import com.zaloni.dao.EmployeeQuery;
import com.zaloni.models.Employee;
import com.zaloni.models.MajorDimension;
import com.google.api.services.sheets.v4.Sheets;

import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

public class Quickstart {
    /** Application name. */
    private static final String APPLICATION_NAME =
        "Google Sheets API Java Quickstart";

    /** Directory to store user credentials for this application. */
    private static final java.io.File DATA_STORE_DIR = new java.io.File(
        System.getProperty("user.home"), ".credentials/sheets.googleapis.com-java-quickstart");

    /** Global instance of the {@link FileDataStoreFactory}. */
    private static FileDataStoreFactory DATA_STORE_FACTORY;

    /** Global instance of the JSON factory. */
    private static final JsonFactory JSON_FACTORY =
        JacksonFactory.getDefaultInstance();

    /** Global instance of the HTTP transport. */
    private static HttpTransport HTTP_TRANSPORT;

    /** Global instance of the scopes required by this quickstart.
     *
     * If modifying these scopes, delete your previously saved credentials
     * at ~/.credentials/sheets.googleapis.com-java-quickstart
     */
    private static final List<String> SCOPES =
        Arrays.asList(SheetsScopes.SPREADSHEETS_READONLY);

    static {
        try {
            HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
            DATA_STORE_FACTORY = new FileDataStoreFactory(DATA_STORE_DIR);
        } catch (Throwable t) {
            t.printStackTrace();
            System.exit(1);
        }
    }

    /**
     * Creates an authorized Credential object.
     * @return an authorized Credential object.
     * @throws IOException
     */
    public static Credential authorize() throws IOException {
        // Load client secrets.
        InputStream in =
            Quickstart.class.getResourceAsStream("/client_secret.json");
        GoogleClientSecrets clientSecrets =
            GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));

        // Build flow and trigger user authorization request.
        GoogleAuthorizationCodeFlow flow =
                new GoogleAuthorizationCodeFlow.Builder(
                        HTTP_TRANSPORT, JSON_FACTORY, clientSecrets, SCOPES)
                .setDataStoreFactory(DATA_STORE_FACTORY)
                .setAccessType("offline")
                .build();
        Credential credential = new AuthorizationCodeInstalledApp(
            flow, new LocalServerReceiver()).authorize("user");
        System.out.println(
                "Credentials saved to " + DATA_STORE_DIR.getAbsolutePath());
        return credential;
    }

    /**
     * Build and return an authorized Sheets API client service.
     * @return an authorized Sheets API client service
     * @throws IOException
     */
    public static Sheets getSheetsService() throws IOException {
        Credential credential = authorize();
        return new Sheets.Builder(HTTP_TRANSPORT, JSON_FACTORY, credential)
                .setApplicationName(APPLICATION_NAME)
                .build();
    }

    public static void main(String[] args) throws IOException, SQLException {
        // Build a new authorized API client service.
        Sheets service = getSheetsService();

        // Prints the names and majors of students in a sample spreadsheet:
        // https://docs.google.com/spreadsheets/d/1BxiMVs0XRA5nFMdKvBdBZjgmUUqptlbs74OgvE2upms/edit
        String spreadsheetId = "1JK572MTzjKhNewfm2JTqjGu3dvkN2lJGS2YQ0s6Sjeg";
        String range = "Sheet1!A2:B";
        ValueRange response = service.spreadsheets().values()
            .get(spreadsheetId, range)
            .execute();
        System.out.println(response.toPrettyString());
        List<List<Object>> values = response.getValues();

        EmployeeQuery empQ = new EmployeeQuery(); 
        Employee emp = new Employee();
        FileWriter fw = new FileWriter("/home/xodiac/Documents/GitKraken/resource_manager_tool/employee.json");
        MajorDimension md= new MajorDimension();
        HashSet<Object> hs = new HashSet<Object>();
        List <Employee> EmployeeList = new ArrayList<Employee>();
        Connection con = JdbcConnect.getCon();
                	EmployeeList = empQ.getAllEmployee();
        for(Employee e: EmployeeList)
        {
            hs.add(e.getName());
        }
        
        try {
			con.setAutoCommit(false);
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
        Gson gson=new Gson();
        md.setMajorDimensions(response.getMajorDimension());
        md.setRange(response.getRange());
        md.setValues(values);
        if (values == null || values.size() == 0) {
            System.out.println("No data found.");
        } else {
          gson.toJson(md,fw);
          System.out.println("Name, Age");
          for (@SuppressWarnings("rawtypes") List row : values)
          {
        	  String Name = row.get(0).toString();
        	  String Age = row.get(1).toString();
              emp.setName(Name);
              emp.setAge(Age);
              
        	  try {
        		 
        		if(!hs.contains(row.get(0)))
        				{
        		   empQ.setEmployee(Name,Age);
				//fw.write("\n");
        				}
        		else
        			empQ.update(Name,Age);
        		
			} catch (SQLException e) {
				fw.close();
				 try
				 {
					 con.rollback();
					 
				 }
				 catch(SQLException ee)
				 {
					 System.out.println(ee);
				 }
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        	  
          }
          try
          {
        	  con.commit();
        	  con.close();
        	  
          }
          catch(SQLException e)
          {
        	  System.out.println(e);
          }
          fw.close();
          
          //for (@SuppressWarnings("rawtypes") List row : values) {
            // Print columns A and E, which correspond to indices 0 and 4.
            //System.out.printf("%s, %s\n", row.get(0), row.get(1));
        //}
        }
    }


}
