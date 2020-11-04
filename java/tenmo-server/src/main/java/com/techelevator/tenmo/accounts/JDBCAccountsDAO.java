package com.techelevator.tenmo.accounts;

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
	public void withdraw(int id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deposit(int id) {
		// TODO Auto-generated method stub
		
	}

}
