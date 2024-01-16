package com.amdocs.jdbc; 

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement; 

public class Test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		try 
		{
			// Loading the Database Driver
			Class.forName("com.mysql.cj.jdbc.Driver");
	
			// Establish the Connection
			Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/bank", "root", "APOcet10");
			
			JDBC jdbc = new JDBC(connection);
			jdbc.displayBankStatement();
			jdbc.updateBankStatement();
			jdbc.insertStatement();
			
			// Close Connection
			connection.close();
		} 
		
		catch(Exception e)
		{
			e.printStackTrace();
		}

	}

}
