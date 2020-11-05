package com.techelevator.tenmo.users;

import java.util.List;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UsersController {
	
     private JDBCUsersDAO jdbcUsersDao;
     private final String BASE_URL = "http://localhost:8080";
     
     public UsersController(JDBCUsersDAO jdbcUsersDao) {
    	 this.jdbcUsersDao = jdbcUsersDao;
    	 
     }
	@RequestMapping(path="/users", method= RequestMethod.GET)
	public List<Users> listAllUsers() {
		
		return jdbcUsersDao.listAll();  
	}
	

}
