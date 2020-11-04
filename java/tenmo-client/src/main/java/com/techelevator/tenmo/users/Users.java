package com.techelevator.tenmo.users;

public class Users {

	private int userId;
	private String username;
	private String passwordHash;
	
	public Users(int userId, String username, String passwordHash) {
		this.userId = userId;
		this.username = username;
		this.passwordHash = passwordHash;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPasswordHash() {
		return passwordHash;
	}

	
}
