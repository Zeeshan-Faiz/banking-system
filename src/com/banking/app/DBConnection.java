package com.banking.app;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection{

	static Connection con;

	public static Connection createDBconnection() {
		
		try {
			
			Class.forName("com.mysql.cj.jdbc.Driver");
			
			final String url = "jdbc:mysql://localhost:3306/banking_system?useSSL=false";
			final String username = "root";
			final String password = "password";
			
			con = DriverManager.getConnection(url,username,password);
			
		}catch(Exception ex) {
			ex.printStackTrace();
		}
		return con;
	}

}
