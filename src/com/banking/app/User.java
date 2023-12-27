package com.banking.app;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class User {
	
	private Connection con;
	private Scanner sc;

	public User(Connection con, Scanner sc) {
		this.con = con;
		this.sc = sc;
	}
	
	public void register() {
		
		sc.nextLine();
		System.out.print("Full Name:");
		String full_name = sc.next();
		System.out.print("\nEmail:");
		sc.nextLine();
		String email = sc.next();
		System.out.print("\nPassword:");
		String password = sc.next();
		
		if(user_Exists(email)) {
			System.out.println("User Already Exists For This Email. Please Login.");
			return;
		}
		
		String query = "insert into user (full_name,email,password)"
				+ "values('" + full_name + "' , '" + email + "' , '" + password + "' )";
		
		try {
			
			Statement statement = con.createStatement();
			int affected_rows = statement.executeUpdate(query);
			if(affected_rows>0)
				System.out.println("Registration Successfull!!!");
			else
				System.out.println("Registration Failed!!!");
			
		}catch(SQLException ex) {
			ex.printStackTrace();
		}
	
	}

	public String login() {
		
		sc.nextLine();
		System.out.print("Email:");
		String email = sc.next();
		System.out.print("\nPassword:");
		String password = sc.next();
		
		String query = "select * from user where email = '" + email + "' and password = '"+password+"'";
		
		try {
			
			Statement statement = con.createStatement();
			ResultSet result = statement.executeQuery(query);
			
			if(result.next())
				return email;
			else
				return null;

		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		
		return null;
		
	}
	
	private boolean user_Exists(String email) {
		
		String query = "select * from user where email= '" + email + "'";
		
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
