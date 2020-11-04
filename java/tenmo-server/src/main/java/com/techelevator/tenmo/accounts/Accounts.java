package com.techelevator.tenmo.accounts;

public class Accounts {
	
	private int accountId;
	private int userId;
	private double balance;
	
	public Accounts(int accountId, int userId, double balance) {
		this.accountId = accountId;
		this.userId = userId;
		this.balance = balance;
	}

	public Accounts() {
		// TODO Auto-generated constructor stub
	}

	public int getAccountId() {
		return accountId;
	}

	public void setAccountId(int accountId) {
		this.accountId = accountId;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public double getBalance() {
		return balance;
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}

}
