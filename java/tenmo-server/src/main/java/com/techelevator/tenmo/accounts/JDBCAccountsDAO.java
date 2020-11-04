package com.techelevator.tenmo.accounts;

import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

@Component
public class JDBCAccountsDAO implements AccountsDAO {
	
	private JdbcTemplate jdbcTemplate;
	
	public JDBCAccountsDAO(DataSource datasource) {
		this.jdbcTemplate = new JdbcTemplate(datasource);
	}

	@Override
	public double viewCurrentBalance(int id) {
		String query = "SELECT balance FROM accounts WHERE user_id = ?";
		SqlRowSet rowSet = jdbcTemplate.queryForRowSet(query, id);
		
		if (rowSet.next()) {
			return rowSet.getDouble(1);
		}
		return 0.00;
	}

	@Override
	public void withdraw(int id, double amountToWithdraw) {
		Accounts account = searchAccountsById(id);
		double currentBalance = account.getBalance();
		currentBalance -= amountToWithdraw;
		String query = "UPDATE accounts SET balance = ? WHERE user_id = ?";
		jdbcTemplate.update(query, currentBalance, id);
	}


	@Override
	public void deposit(int id, double amountToDeposit) {
		Accounts account = searchAccountsById(id);
		double currentBalance = account.getBalance();
		currentBalance += amountToDeposit;
		String query = "UPDATE accounts SET balance = ? WHERE user_id = ?";
		jdbcTemplate.update(query, currentBalance, id);
	}
	@Override
    public Accounts searchAccountsById(int id) {
    	String query = "SELECT * FROM accounts WHERE account_id = ?";
    	SqlRowSet rowSet = jdbcTemplate.queryForRowSet(query, id);
    	if (rowSet.next()) {
    		Accounts account = new Accounts();
    		account = mapRowToAccounts(rowSet);
    		
    		return account;
    	}
    	return null;
    }
	
	@Override
	public Accounts searchAccountsByUserId(int id) {
		String query = "SELECT * FROM accounts WHERE user_id = ?";
		SqlRowSet rowSet = jdbcTemplate.queryForRowSet(query, id);
		if (rowSet.next()) {
			Accounts account = new Accounts();
			account = mapRowToAccounts(rowSet);
			return account;
			
		}
		return null;
	}
	
	@Override
	public List<Accounts> getAllAccounts() {
		List<Accounts> accountList = new ArrayList<>();
		String query = "SELECT * FROM accounts";
		SqlRowSet rowSet = jdbcTemplate.queryForRowSet(query);
		
		while (rowSet.next()) {
			Accounts account = mapRowToAccounts(rowSet);
			accountList.add(account);
		}
		return accountList;
	}
	
    private Accounts mapRowToAccounts(SqlRowSet rowSet) {
    	Accounts account = new Accounts();
    	account.setAccountId(rowSet.getInt("account_id"));
    	account.setUserId(rowSet.getInt("user_id"));
    	account.setBalance(rowSet.getDouble("balance"));
    	
    	return account;
    	
    }
}
