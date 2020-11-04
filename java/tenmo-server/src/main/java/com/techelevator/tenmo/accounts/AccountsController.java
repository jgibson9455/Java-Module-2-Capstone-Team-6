package com.techelevator.tenmo.accounts;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AccountsController {
private JDBCAccountsDAO accountsDao;
private final String BASE_URL = "http://localhost:8080";



public AccountsController (JDBCAccountsDAO accountsDao) {
	this.accountsDao = accountsDao;
}

@RequestMapping(path="/accounts/{id}", method = RequestMethod.GET)
public double viewCurrentBalance(@PathVariable int id) {
	return accountsDao.viewCurrentBalance(id);
	
}




}
