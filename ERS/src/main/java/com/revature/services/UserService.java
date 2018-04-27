package com.revature.services;

import java.util.List;

import com.revature.ersproject1.daos.UserDAO;
import com.revature.ersproject1.daos.UserDAOImp;
import com.revature.ersproject1.pojos.User;

public class UserService {

	public static UserDAO userDAO = new UserDAOImp();

	public User createUser(User user) {
		
		if(isEmailAvailable(user.getEmail()) && isUsernameAvailable(user.getUsername())) {
			return userDAO.addUser(user);
		}
		
		return null;
	}
	
	public User getUserById(int userId) {
		return userDAO.getUserById(userId);
	}
	
	public User getUserByUsername(String username) {
		return userDAO.getUserByUsername(username);
	}
	
	public User getUserByEmailAddress(String email) {
		return userDAO.getUserByEmailAddress(email);
	}
	
	public User updateUser(User user) {
		return userDAO.updateUser(user);
	}
	
	public User loginUser(User user) {
		User userMatch = userDAO.getUserByUsername(user.getUsername());
		if(userMatch.getPassword() == null) {
			return null;
		}
		if(userMatch != null) {
			if(userMatch.getPassword().equals(user.getPassword())) {
				return userMatch;
			}
		}
		return null;	
	}
	
	public boolean isEmailAvailable(String email) {
		
		for(User user : userDAO.getAllUsers()) {
			if(user.getEmail().equalsIgnoreCase(email)) {
				return false;
			}
		}
		return true;
	}
	
	public boolean isUsernameAvailable(String username) {
		
		for(User user : userDAO.getAllUsers()) {
			if(user.getUsername().equalsIgnoreCase(username)) {
				return false;
			}
		}	
		return true;	
	}
	
	public boolean isUserAManager(String username) {
		return userDAO.isManager(username);
	}
	
	public List<User> getAllUsersByRole(int roleId) {
		return userDAO.getAllUsersByRole(roleId);
	}
}


