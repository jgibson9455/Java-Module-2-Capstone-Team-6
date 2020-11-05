package com.techelevator.tenmo.transfers;

import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.techelevator.tenmo.accounts.JDBCAccountsDAO;

@RestController
public class TransfersController {

	private JDBCAccountsDAO jdbcAccountsDao;
	private JDBCTransfersDAO jdbcTransfersDao;
	
	
	public TransfersController(JDBCAccountsDAO jdbcAccountsDao, JDBCTransfersDAO jdbcTransfersDao) {
		this.jdbcAccountsDao = jdbcAccountsDao;
		this.jdbcTransfersDao = jdbcTransfersDao;
	}
	
	@RequestMapping(path="/transfer", method=RequestMethod.PUT)
	public void transfer(@RequestBody Transfers transfer) {
		jdbcTransfersDao.create(transfer);
	}
	
	@RequestMapping(path="/transfers", method=RequestMethod.GET)
	public List<Transfers> listAll(){
		return jdbcTransfersDao.listAll();
	}
	
	@RequestMapping(path="/transfers/users/{id}", method=RequestMethod.GET)
	public List<Transfers> listTransfersByFromId(@PathVariable int id){
		return jdbcTransfersDao.listTransfersByFromId(id);
	}
	
	@RequestMapping(path = "/transfers/{id}", method = RequestMethod.GET)
	public Transfers getTransfersById(@PathVariable int id) {
		return jdbcTransfersDao.listTransfersByTransferId(id);
	}
}