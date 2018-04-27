package com.revature.ersproject1.daos;

import java.util.List;

import com.revature.ersproject1.pojos.User;

/**
 * 
 * @author Brandon Semba
 * 
 * DAO interface for employee
 *
 */
public interface UserDAO {

	public User addUser(User user);
	
	public User addUser(String username, String password, String firstname, 
			String lastname, String email, int userRoleId);
	
	public User getUserByUsername(String username);
	
	public User getUserById(int userId);
	
	public User getUserByEmailAddress(String email);
	
	public User updateUser(User user);
	
	public boolean updateUserEmail(User userToUpdate);
	
	public boolean removeUser(User userToRemove);
	
	public boolean isManager(String username);
	
	List<User> getAllUsersByRole(int roleId);
	
	List<User> getAllUsers();
	
}
