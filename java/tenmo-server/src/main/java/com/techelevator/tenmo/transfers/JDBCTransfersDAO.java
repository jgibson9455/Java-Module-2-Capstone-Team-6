package com.techelevator.tenmo.transfers;

import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

@Component
public class JDBCTransfersDAO {
	private JdbcTemplate jdbcTemplate;

    public JDBCTransfersDAO(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}
    
    public void create(Transfers transfer) {
    	String query = "INSERT INTO transfers (transfer_type_id, transfer_status_id, account_from, account_to, amount) "
    			+ "VALUES (?, ?, ?, ?, ?)";
    	jdbcTemplate.update(query, transfer.getTypeId(), transfer.getStatusId(), transfer.getAccountFrom(), transfer.getAccountTo(), transfer.getAmount());
    }
    
    public List<Transfers> listAll(){
    	List<Transfers> transferList = null;
    	String query = "SELECT * FROM transfers";
    	SqlRowSet rowSet = jdbcTemplate.queryForRowSet(query);
    	while (rowSet.next()) {
    		Transfers transfer = mapRowToTransfer(rowSet);
    		transferList.add(transfer);
    	}
    	return transferList;
    	
    }
    
    public List<Transfers> listTransfersByFromId(int id){
    	List<Transfers> transferList = new ArrayList<>();
    	try {
    	String query = "SELECT * FROM transfers WHERE account_from = ?";
    	SqlRowSet rowSet = jdbcTemplate.queryForRowSet(query, id);
    	while (rowSet.next()) {
    		Transfers transfer = mapRowToTransfer(rowSet);
    		transferList.add(transfer);
    	}
    	}catch (NullPointerException ex) {
    		System.out.println("No transfers found!");
    		return transferList;
    	}
    	return transferList;
    	
    }
    
    
    public Transfers mapRowToTransfer(SqlRowSet rowSet) {
    	Transfers transfer = new Transfers();
    	transfer.setId(rowSet.getInt("transfer_id"));
    	transfer.setTypeId(rowSet.getInt("transfer_type_id"));
    	transfer.setStatusId(rowSet.getInt("transfer_status_id"));
    	transfer.setAccountFrom(rowSet.getInt("account_from"));
    	transfer.setAccountTo(rowSet.getInt("account_to"));
    	transfer.setAmount(rowSet.getDouble("amount"));
    	return transfer;
    	
    }

	
	
}
