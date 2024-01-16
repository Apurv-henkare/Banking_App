package com.amdocs.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class JDBC {

	private Connection connection;
	private Statement statement;
	private ResultSet resultset;
	private PreparedStatement preparedstatement;

	JDBC(Connection connection) {
		this.connection = connection;

	} 
	
	public void initializeDisplayStatement() throws SQLException 
	{
		statement = this.connection.createStatement();
		resultset = this.statement.executeQuery("SELECT * FROM BankTrans"); 
		
		/* 
		   resultset = this.connection.createStatement().executeQuery("String");
		 */
	}

	public void displayBankStatement() throws SQLException {
        
		this.initializeDisplayStatement();

		while (resultset.next()) {

			String transID = resultset.getString(1);
			int accNo = resultset.getInt(2);
			double oldBal = resultset.getDouble(3);
			String transType = resultset.getString(4);
			double transAmt = resultset.getDouble(5);

			System.out.println(transID + " " + accNo + " " + oldBal + " " + transType + " " + transAmt);

		}

	}

	public void updateBankStatement() throws SQLException {
		
		this.initializeDisplayStatement();

		while (resultset.next()) {

			String transID = resultset.getString(1);
			int accNo = resultset.getInt(2);
			double oldBal = resultset.getDouble(3);
			String transType = resultset.getString(4);
			double transAmt = resultset.getDouble(5);

			double tempNewBal = 0;
			String tempValidity = "";

			tempNewBal = this.getNewBal(oldBal, transAmt, transType);
			tempValidity = this.getStatus(tempNewBal);

			this.preparedstatement = this.connection.prepareStatement("UPDATE BankTrans SET NewBal = ?, TransStat = ? WHERE TransID = ?");
			this.preparedstatement.setDouble(1, tempNewBal);
			this.preparedstatement.setString(2, tempValidity);
			this.preparedstatement.setString(3, transID);

			int rowsInserted = this.preparedstatement.executeUpdate();
			System.out.println(rowsInserted + "rows Updated");

		}
	}

	public double getNewBal(double oldBal, double transAmt, String transType) {
		if (transType.equals("W")) {
			return oldBal - transAmt;
		}

		return oldBal + transAmt;
	}

	public String getStatus(double tempNewBal) {
		if (tempNewBal < 0) {
			return "Invalid";
		}

		return "Valid";
	}

	public void insertStatement() throws SQLException {
	    
		this.initializeDisplayStatement();

		while (resultset.next()) {

			String transID = resultset.getString(1);
			String transStat = resultset.getString(7);
			String transType = resultset.getString(4);
			double transAmt = resultset.getDouble(5);

			if (transStat.equals("Valid")) {
				this.insertValidTrans(transID, transType, transAmt, transStat);
			}

			else {

				this.insertInValidTrans(transID, transType, transAmt, transStat);
			}
		}
	}

	public void insertValidTrans(String transID, String transType, double transAmt, String transStat)
			throws SQLException {
		this.preparedstatement = this.connection.prepareStatement("INSERT INTO ValidTrans Values (?,?,?,?)");
		this.preparedstatement.setString(1, transID);
		this.preparedstatement.setString(2, transType);
		this.preparedstatement.setDouble(3, transAmt);
		this.preparedstatement.setString(4, transStat);

		int rowsInserted = this.preparedstatement.executeUpdate();
		System.out.println(rowsInserted + "rows Inserted");
	}

	public void insertInValidTrans(String transID, String transType, double transAmt, String transStat)
			throws SQLException {
		this.preparedstatement = this.connection.prepareStatement("INSERT INTO InValidTrans Values (?,?,?,?)");
		this.preparedstatement.setString(1, transID);
		this.preparedstatement.setString(2, transType);
		this.preparedstatement.setDouble(3, transAmt);
		this.preparedstatement.setString(4, transStat);

		int rowsInserted = this.preparedstatement.executeUpdate();
		System.out.println(rowsInserted + "rows Inserted");

	}

}
