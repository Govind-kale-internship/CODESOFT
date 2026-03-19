package task3;

import java.util.Scanner;

class BankAccount
{
	private double balance;
	
	public BankAccount(double initialBalance)
	{
		this.balance = initialBalance;
	}
	
	public double getBalance()
	{
		return balance;
	}
	
	public boolean deposit(double amount)
	{
		if(amount > 0)
		{
			balance += amount;
			return true;
		}
		return false;
	}
	
	public boolean withdraw(double amount)
	{
		if(amount > 0 && amount <= balance)
		{
			balance -= amount;
			return true;
		}
		return false;
	}	
}



class ATM 
{
	private BankAccount account;
	private Scanner sc;
	
	public ATM(BankAccount account)
	{
		this.account = account;
		this.sc = new Scanner(System.in);
	}
	
	public void Start()
	{
		while(true)
		{
			displayMenu();
			int choice = getUserChoice();
			
			if(choice == 4)
			{
				System.out.println("\nThank you for using our ATM. GoodBye!");
				break;
			}
			
			processChoice(choice);
		}
		
		sc.close();
	}
	
	private void displayMenu()
	{
		System.out.println("_____________________________________________");
		System.out.println("\n               ATM MACHINE              "); 
		System.out.println(" 1. Check Balance ");
		System.out.println(" 2. Deposit Money ");
		System.out.println(" 3. Withdraw Money ");
		System.out.println(" 4. Exit ");
		System.out.println("_____________________________________________");
		System.out.print(" Select Option : ");
		
	}
	
	private int getUserChoice()
	{
		while(!sc.hasNextInt())
		{
			System.out.println("Invalid Enter Number Between 1 - 4 : ");
			sc.next();
		}
		
		int choice = sc.nextInt();
		sc.nextLine();
		return choice;
		
	}
	
	private void processChoice(int choice)
	{
		switch(choice)
		{
		case 1:
			checkBalance();
			break;
			
		case 2: 
			deposit();
			break;
			
		case 3:
			withdraw();
			break;
			
		default:
			System.out.println("Invalid option! Choose 1-4");
		}	
	}
	
	private void checkBalance()
	{
		System.out.println("\nCurrent Balance : " + account.getBalance());
	}
	
	private void deposit()
	{
		System.out.println("Enter amount to deposit :");
		double amount = getValidAmount();
		
		if(account.deposit(amount))
		{
			System.out.println("Successfully deposited: " + amount);
			System.out.println("New Balance: " + account.getBalance());
		}
		else
		{
			System.out.println("Invalid deposit amount!");
		}
	}
	
	private void withdraw()
	{
		System.out.println("\nEnter amount to withdraw: ");
		double amount = getValidAmount();
		
		if(account.withdraw(amount))
		{
			System.out.println("Successfully withdrawn : " + amount);
			System.out.println("Remaining Balance : " + account.getBalance());
		}
		else if(amount > account.getBalance())
		{
			System.out.println("Insufficient Balance!");
			System.out.println("Your Balance: " + account.getBalance());
		}
		else
		{
			System.out.println("Invalid withdrawal amount!");
		}
		
	}
	
	private double getValidAmount()
	{
		while(!sc.hasNextDouble())
		{
			System.out.println("Invalid! Enter valid amount: ");
			sc.next();
		}
		double amount = sc.nextDouble();
		sc.nextLine();
		return amount;
		
	}	
	
}


public class ATMInterface 
{

	public static void main(String[] args) 
	{
		System.out.println("||      WELCOME TO THE CODESOFT BANK      ||");
		System.out.println("_____________________________________________");
		
		BankAccount userAccount = new BankAccount(1000.00);
		
		ATM atm = new ATM(userAccount);
		atm.Start();	 

	}
}
