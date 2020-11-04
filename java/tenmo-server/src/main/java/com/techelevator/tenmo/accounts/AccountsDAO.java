package com.techelevator.tenmo.accounts;

import java.util.List;

public interface AccountsDAO {
	
	
	public double viewCurrentBalance(int id);
	
	public void withdraw(int id, double amountToWithdraw);

	public void deposit(int id, double amoountToDeposit);
	
	public List<Accounts> getAllAccounts();
	
	public Accounts searchAccountsByUserId(int id);
	
	public Accounts searchAccountsById(int id);

}
