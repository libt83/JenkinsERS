package com.revature.services;

import java.util.List;

import com.revature.ersproject1.daos.ReimbDAO;
import com.revature.ersproject1.daos.ReimbDAOImp;
import com.revature.ersproject1.pojos.Reimb;

public class ReimbService {

	public static ReimbDAO reimbDAO = new ReimbDAOImp();
	
	public Reimb createReimb(Reimb reimb) {
		return reimbDAO.addReimb(reimb);
	}
	
	public Reimb getReimbByReimbId(int reimbId) {
		return reimbDAO.getReimbById(reimbId);
	}
	
	public Reimb updateReimb(Reimb reimb) {
		return reimbDAO.updateReimb(reimb);
	}
	
	public List<Reimb> getAllUserReimbs() {
		return reimbDAO.getAllUserReimbs();
	}
	
	public List<Reimb> getAllReimbByAuthor(int authorId) {
		return reimbDAO.getAllReimbByAuthor(authorId);
	}
	
	public List<Reimb> getAllReimbByStatus(int statusId) {
		return reimbDAO.getAllReimbByStatus(statusId);
	}
	
	

}
