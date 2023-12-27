package com.banking.app;

import java.sql.Connection;
import java.util.Scanner;

public class BankingAppMain {

	public static void main(String[] args) {
		
		Connection con = DBConnection.createDBconnection();
		Scanner sc = new Scanner(System.in);
		
		User user = new User(con,sc);
		Accounts account = new Accounts(con,sc);
		@SuppressWarnings("unused")
		AccountManager account_manager = new AccountManager(con, sc);
		
		String email;
		long account_number;
		
		while(true) {
			
			System.out.println("*******Welcome To Banking System*******\n");
			System.out.println("1.Register (New User) \n2.Login \n3.Exit");
			System.out.print("Enter your choice:");
			int choice1 = sc.nextInt();
			
			switch(choice1) {
			
				case 1:
					user.register();
					break;
				
				case 2:
					email = user.login();
                    if(email!=null){
                        System.out.println();
                        System.out.println("User Logged In!");
                        
                        if(!account.account_Exists(email)){
                        	
                            System.out.println();
                            System.out.println("1. Open a new Bank Account");
                            System.out.println("2. Exit");
                            if(sc.nextInt() == 1) {
                                account_number = account.openAccount(email);
                                System.out.println("Account Created Successfully");
                                System.out.println("Your Account Number is: " + account_number);
                            }
                            else
                            {
                                break;
                            }

                        }
                        account_number = account.get_AccountNumber(email);
                        int choice2 = 0;
                        while (choice2 != 5) {
                            System.out.println();
                            System.out.println("1. Debit Money");
                            System.out.println("2. Credit Money");
                            System.out.println("3. Transfer Money");
                            System.out.println("4. Check Balance");
                            System.out.println("5. Log Out");
                            System.out.println("Enter your choice: ");
                            choice2 = sc.nextInt();
                            switch (choice2) {
                                case 1:
                                    AccountManager.debutMoney(account_number);
                                    break;
                                case 2:
                                	AccountManager.creditMoney(account_number);
                                    break;
                                case 3:
                                	AccountManager.transferMoney(account_number);
                                    break;
                                case 4:
                                	AccountManager.getBalance(account_number);
                                    break;
                                case 5:
                                    break;
                                default:
                                    System.out.println("Enter Valid Choice!");
                                    break;
                            }
                        }

                    }
                    else{
                        System.out.println("Incorrect Email or Password!");
                    }
                case 3:
                	System.out.println("Exiting System!");
                	System.out.println("THANK YOU FOR USING BANKING SYSTEM!!!");
                    
                    return;
                default:
                    System.out.println("Enter Valid Choice");
                    break;
            }
        }
    }
				
}
		


