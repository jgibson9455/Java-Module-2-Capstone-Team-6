package com.techelevator.tenmo.users;

import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;
@Component 
public class JDBCUsersDAO implements UsersDAO{
	
    private JdbcTemplate jdbcTemplate;
    
    public JDBCUsersDAO(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

    
	@Override
	public List<Users> listAll() {
		List<Users> users = new ArrayList<>();
		String query = "SELECT * FROM users";
		SqlRowSet rowSet = jdbcTemplate.queryForRowSet(query);
		
		while (rowSet.next()) {
			Users user = mapRowToUsers(rowSet);
			users.add(user);
		}
		return users;
	}
	
	public Users mapRowToUsers(SqlRowSet rowSet) {
		Users user = new Users();
		user.setUserId(rowSet.getInt("user_id"));
		user.setUsername(rowSet.getString("username"));
		user.setPasswordHash(rowSet.getString("password_hash"));
		
		return user;
	}
	

}
