package com.techelevator.tenmo.accounts;

public interface AccountsDAO {
	
	
	public double viewCurrentBalance(int id);
	
	public void withdraw(int id);

	public void deposit(int id);
	
	public Accounts searchAccountsById(int id);

}
