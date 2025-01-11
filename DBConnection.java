package com.java.in;
import java.sql.Connection;
import java.sql.DriverManager;
public class DBConnection {
	
	    private static final String URL = "jdbc:mysql://localhost:3307/your_database_name";
	    private static final String USER = "root";
	    private static final String PASSWORD = "@24March2003";

	    public static Connection getConnection() throws Exception {
	        Class.forName("com.mysql.cj.jdbc.Driver");
	        return DriverManager.getConnection(URL, USER, PASSWORD);
	    }
	}


