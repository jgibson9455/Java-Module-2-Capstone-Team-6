package com.techelevator.tenmo.transfers;

import com.techelevator.tenmo.accounts.Accounts;

public class TransfersDTO {

	
	private Accounts toAccount;
	private Accounts fromAccount;
	private Transfers transfer;
	
	public TransfersDTO(Accounts toAccount, Accounts fromAccount, Transfers transfer) {
		this.toAccount = toAccount;
		this.fromAccount = fromAccount;
		this.transfer = transfer;
	}

	public Accounts getToAccount() {
		return toAccount;
	}

	public Accounts getFromAccount() {
		return fromAccount;
	}

	public Transfers getTransfer() {
		return transfer;
	}
}
