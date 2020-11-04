package com.techelevator.tenmo.transfer_types;

public class TransferTypes {
	
	private int typeId;
	private String description;
	
	public TransferTypes(int id, String description) {
		this.typeId = id;
		this.description = description;
	}

	public int getTypeId() {
		return typeId;
	}

	public void setTypeId(int typeId) {
		this.typeId = typeId;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
