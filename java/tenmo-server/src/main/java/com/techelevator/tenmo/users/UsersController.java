package com.techelevator.tenmo.users;

import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.techelevator.tenmo.accounts.Accounts;
import com.techelevator.tenmo.accounts.JDBCAccountsDAO;

@RestController
@PreAuthorize("isAuthenticated()")
public class UsersController {
	
     private JDBCUsersDAO jdbcUsersDao;
     private JDBCAccountsDAO jdbcAccountsDao;
     private final String BASE_URL = "http://localhost:8080";
     
     public UsersController(JDBCUsersDAO jdbcUsersDao, JDBCAccountsDAO jdbcAccountsDao) {
    	 this.jdbcUsersDao = jdbcUsersDao;
    	 this.jdbcAccountsDao = jdbcAccountsDao;
    	 
     }
	@RequestMapping(path="/users", method= RequestMethod.GET)
	public List<Users> listAllUsers() {
		
		return jdbcUsersDao.listAll();  
	}
	
	@RequestMapping(path="/users/accounts/searchUserId", method=RequestMethod.GET)
	public Accounts findAccountByUserId(@RequestParam(value="userId") int userId) {
		return jdbcAccountsDao.searchAccountsByUserId(userId);
	}
	

}
