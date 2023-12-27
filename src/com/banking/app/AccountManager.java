package com.banking.app;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class AccountManager {

	private static Connection con;
	private static Scanner sc;

	public AccountManager(Connection con, Scanner sc) {
		AccountManager.con = con;
		AccountManager.sc = sc;
	}
	
public static void creditMoney(long account_number) {
		
		sc.nextLine();
		System.out.print("Enter Amount:");
		double credit_amount = sc.nextDouble();
		System.out.print("Enter 4-Digit Security Pin:");
		int security_pin = sc.nextInt();
		
		try {
			con.setAutoCommit(false);
			
			if(account_number!=0) {
				
				String query = "select * from accounts where account_number='"
						+ account_number + "' and security_pin='"
						+security_pin + "'";
				
				
				Statement statement = con.createStatement();
				ResultSet result = statement.executeQuery(query);
				
				if(result.next()) {
						
					String credit_query = "update accounts set balance = balance + '" 
					+ credit_amount +"' where account_number = " +account_number; 
						
					Statement statement1 = con.createStatement();
					int affected_rows = statement1.executeUpdate(credit_query);
						
					if(affected_rows > 0) {
						System.out.println("Rs." + credit_amount + " credited Successfully!!!");
						con.commit();
						con.setAutoCommit(true);
					}
					else {
						System.out.println("Transaction Failed!!!");
						con.rollback();
						con.setAutoCommit(true);
					}	
				}
			}
			else 
				System.out.println("Invalid Pin!!!");
			
		}
		catch(SQLException ex) {
			ex.printStackTrace();
		}
	
	}
	
	public static void debutMoney(long account_number) {
		
		sc.nextLine();
		System.out.print("Enter Amount:");
		double debit_amount = sc.nextDouble();
		System.out.print("Enter 4-Digit Security Pin:");
		int security_pin = sc.nextInt();
		
		try {
			con.setAutoCommit(false);
			if(account_number!=0) {
				
				String query = "select * from accounts where account_number='"
						+ account_number + "' and security_pin='"
						+security_pin + "'";
				
				
				Statement statement = con.createStatement();
				ResultSet result = statement.executeQuery(query);
				
				if(result.next()) {
					double current_balance = result.getDouble(4);
					if(current_balance>=debit_amount) {
						
						String debit_query = "update accounts set balance = balance - '" 
						+ debit_amount +"' where account_number = " +account_number; 
						
						Statement statement1 = con.createStatement();
						int affected_rows = statement1.executeUpdate(debit_query);
						
						if(affected_rows > 0) {
							System.out.println("Rs." + debit_amount + " debited Successfully!!!");
							con.commit();
							con.setAutoCommit(true);
						}
						else {
							System.out.println("Transaction Failed!!!");
							con.rollback();
							con.setAutoCommit(true);
						}	
					}
					else
						System.out.println("Insufficient Balance!!!");
					
					}
			}
			else 
				System.out.println("Invalid Pin!!!");
			
			
		}
		catch(SQLException ex) {
			ex.printStackTrace();
		}
	
	}

	public static void transferMoney(long sender_account_number) {
		
		sc.nextLine();
		System.out.print("Enter Reciever Account Number:");
		long receiver_account_number = sc.nextLong();
		System.out.print("Enter Amount To Be Transferred:");
		double transfer_amount = sc.nextDouble();
		System.out.print("Enter 4-Digit Security Pin:");
		int security_pin = sc.nextInt();
		
		try {
			
			con.setAutoCommit(false);
			if(sender_account_number!=0 && receiver_account_number!=0) {
				
				String query = "select * from accounts where account_number='"
						+ sender_account_number + "' and security_pin='"
						+security_pin + "'";
				
				Statement statement = con.createStatement();
				ResultSet result = statement.executeQuery(query);
				
				if(result.next()) {
					
					double current_balance_sender = result.getDouble(4);
					if(transfer_amount<=current_balance_sender) {
						
						String debit_query = "update accounts set balance = balance - '" 
								+ transfer_amount +"' where account_number = " + sender_account_number; 
								
						String credit_query = "update accounts set balance = balance + '" 
								+ transfer_amount +"' where account_number = " + receiver_account_number; 
								
						Statement debit_statement = con.createStatement();
						int debit_result = debit_statement.executeUpdate(debit_query);
						
						Statement credit_statement = con.createStatement();
						int credit_result = credit_statement.executeUpdate(credit_query);
						
						if(credit_result>0 && debit_result>0) {
							System.out.print("Transaction Successful!!!\n");
							System.out.print("Rs." + transfer_amount + " Transferred Successfully!!!");
							con.commit();
							con.setAutoCommit(true);
						}
						else {
							System.out.println("Transaction Failed!!!");
							con.rollback();
							con.setAutoCommit(true);
						}
						
					}
					else {
						System.out.println("Insufficient Balance!!!");
					}
				}
				else
					System.out.println("Invalid Pin!!!");
			}
			else
				System.out.println("Invalid Account Number!!!");
			
		}catch(SQLException ex) {
			ex.printStackTrace();
		}
		
		
	}
	
	public static void getBalance(long account_number) {
		
		sc.nextLine();
		System.out.print("Enter 4-Digit Security Pin:");
		int security_pin = sc.nextInt();
		String query = "select * from accounts where account_number='"
				+ account_number + "' and security_pin='"
				+security_pin + "'";
		
		try {
			
			Statement statement = con.createStatement();
			ResultSet result = statement.executeQuery(query);
			
			if(result.next()) {
				double balance = result.getDouble(4);
				System.out.println("Balance: "+ balance);
			}
			else
				System.out.println("Invalid Pin!!!");
			
		}catch(SQLException ex) {
			ex.printStackTrace();
		}
		
	}
	
}
