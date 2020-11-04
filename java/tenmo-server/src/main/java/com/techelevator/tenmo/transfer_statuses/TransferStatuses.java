package com.techelevator.tenmo.transfer_statuses;

public class TransferStatuses {
	
	private int statusId;
	private String description;
	
	public TransferStatuses(int id, String description) {
		this.statusId = id;
		this.description = description;
	}

	public int getStatusId() {
		return statusId;
	}

	public void setStatusId(int statusId) {
		this.statusId = statusId;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
