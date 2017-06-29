package com.jwt.rest;
 
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.LinkedList;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

import com.google.gson.Gson;

  
@Path("/hello")
public class HelloWorldService {
	
	private String jdbcDriverStr = "com.mysql.jdbc.Driver";
    private String jdbcURL = "jdbc:mysql://localhost/db?"
            + "user=root&password=root";
    
 
    private Connection connection;
    private Statement statement;
    private ResultSet resultSet;
  
    @GET
    @Path("/{name}")
    public Response getMsg(@PathParam("name") String name) {
  
        String output = "Welcome   : " + name;
  
        return Response.status(200).entity(output).build();
  
    }
    
    @GET
    @Path("/db")
    public Response getDB() {
  
    	try{
    	Class.forName(jdbcDriverStr);
        connection = DriverManager.getConnection(jdbcURL);
        statement = connection.createStatement();
        resultSet = statement.executeQuery("select * from prova;");
        String output = "";  
        
        LinkedList<String> list = new LinkedList();
        
        while(resultSet.next()){
            String name = resultSet.getString("Name");
            int age = resultSet.getInt("Age");
            System.out.println("Name: "+name);
            System.out.println("Age: "+age);
         
            output = "Name: " + name + " - Age: " + age;
            list.add(output);
        }
        
        String json = new Gson().toJson(list);
        return Response.status(200).entity(json).build();
    	}catch(Exception ex) {ex.printStackTrace();
    						  return Response.status(500).build();}
    }
  
}