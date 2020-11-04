package com.techelevator.tenmo.accounts;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
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
public Accounts viewAccount(@PathVariable int id) {
	return accountsDao.searchAccountsById(id);
	
}

@RequestMapping(path="/accounts", method = RequestMethod.GET)
public List<Accounts> getAllAccounts(){
	return accountsDao.getAllAccounts();
}

@RequestMapping(path="/withdraw/{fromId}", method = RequestMethod.PUT)
public Accounts withdraw(@PathVariable int fromId, @RequestBody double amountToSend){
	accountsDao.withdraw(fromId, amountToSend);
	return accountsDao.searchAccountsById(fromId);	
}

@RequestMapping(path="/deposit/{toId}", method = RequestMethod.PUT)
public Accounts deposit(@PathVariable int toId, @RequestBody double amountToReceive) {
	accountsDao.deposit(toId, amountToReceive);
	return accountsDao.searchAccountsById(toId);
}




}
