package com.revature.ersproject1.daos;

import java.util.List;

import com.revature.ersproject1.pojos.Reimb;

public interface ReimbDAO {

	public Reimb addReimb(Reimb reimb);
	
	public Reimb addReimb(double amount, String descr, int author, int type); 
	
	public Reimb getReimbById(int reimbId);
	
	public boolean removeReimb(Reimb reimbToRemove);
	
	public boolean updateReimbStatus(Reimb reimbToUpdate);
	
	public boolean updateReimbType(Reimb reimbToUpdate);
	
	public Reimb updateReimb(Reimb reimbToUpdate);
	
	public List<Reimb> getAllUserReimbs();
	
	public List<Reimb> getAllReimbByAuthor(int authorId);
	
	public List<Reimb> getAllReimbByType(int typeId);
	
	public List<Reimb> getAllReimbByStatus(int statusId);
	
}
