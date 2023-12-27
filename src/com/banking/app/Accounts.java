package com.banking.app;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class Accounts {

	private Connection con;
	private Scanner sc;

	public Accounts(Connection con, Scanner sc) {
		this.con = con;
		this.sc = sc;
	}
	
	public long openAccount(String email) {
		
		if(!account_Exists(email)) {
			
			sc.nextLine();
			System.out.print("Enter Full Name:");
			String full_name = sc.nextLine();
			System.out.print("Enter Initial Amount:");
			double balance = sc.nextDouble();
			sc.nextLine();
			System.out.print("Enter 4-Digit Security Pin:");
			String security_pin = sc.next();
			long account_number = generateAccountNumber();
			
			String query = "insert into accounts(account_number,full_name,email,balance,security_pin)"
					+ "values(" + account_number + " , '" 
					+ full_name + "' , '" 
					+ email + "' , " 
					+ balance +  " , '" 
					+ security_pin+ "')";
			
			
			try {
				
				Statement statement = con.createStatement();
				int affected_rows = statement.executeUpdate(query);
				if(affected_rows>0)
					return account_number;
				else
					throw new RuntimeException("Account Creation Failed");
				
			}
			catch(SQLException ex) {
				ex.printStackTrace();
			}
			
			
			throw new RuntimeException("Account Creation Failed");
			
		}
		return 000000000000000;
	}
	
	
	
	public long get_AccountNumber(String email) {
		
		String query = "select account_number from accounts where email= '" + email +"'";
		
		try {
			Statement statement = con.createStatement();
			ResultSet result = statement.executeQuery(query);
			
			if(result.next())
				return result.getLong(1);
				
		}
		catch(SQLException ex) {
			ex.printStackTrace();
		}
		
		throw new RuntimeException("Account Doesn't Exist!!");
	
	}
	
	
	
	private long generateAccountNumber() {
		
		String query = "select account_number from accounts order by account_number DESC limit 1";
		try {
			Statement statement = con.createStatement();
			ResultSet result = statement.executeQuery(query);
			
			if(result.next()) {
				long last_account_number = result.getLong(1);
				return last_account_number + 1;
			}
			else
				return 10000100;
			
		}
		catch(SQLException ex) {
			ex.printStackTrace();
		}
		
		return 10000100;
	}
	
	public boolean account_Exists(String email) {
		
		String query = "select * from accounts where email= '" + email +"'";
		
		try {
			Statement statement = con.createStatement();
			ResultSet result = statement.executeQuery(query);
			
			if(result.next())
				return true;
			else
				return false;
				
		}
		catch(SQLException ex) {
			ex.printStackTrace();
		}
		
		return false;
	}
	
	
}
