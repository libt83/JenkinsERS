package com.revature.ersproject1.daos;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.revature.ersproject1.pojos.User;
import com.revature.ersproject1.utils.ConnectionFactory;

import oracle.jdbc.internal.OracleTypes;

public class UserDAOImp implements UserDAO {

	@Override
	public User addUser(User user) {
		
		User u = null;
		
		try(Connection conn = ConnectionFactory.getInstance().getConnection();) {
			
			conn.setAutoCommit(false);
			
			String sql = "INSERT INTO ers_db.ers_users (ers_username, ers_password, "
					+ "user_first_name, user_last_name, user_email, user_role_id)  "
					+ "VALUES (?, ?, ?, ?, ?, ?)";
			String[] keys = new String[1];
			keys[0] = "ers_users_id";
			
			PreparedStatement pstmt = conn.prepareStatement(sql, keys);
			pstmt.setString(1, user.getUsername());
			pstmt.setString(2, user.getPassword());
			pstmt.setString(3, user.getFirstname());
			pstmt.setString(4, user.getLastname());
			pstmt.setString(5, user.getEmail());
			pstmt.setInt(6, user.getUserRoleId());
			
			int rowsUpdated = pstmt.executeUpdate();
			
			ResultSet rs = pstmt.getGeneratedKeys();
			
			if(rowsUpdated != 0) {
				u = user;
				while(rs.next()) {
					u.setUserId(rs.getInt(1));
				}
			}
			
		}catch (SQLException sqle) {
			sqle.printStackTrace();
		}
		
		return user;
	}
	
	@Override
	public User addUser(String username, String password, String firstname, String lastname, String email, int userRoleId) {
		
		User user = new User();

		try(Connection conn = ConnectionFactory.getInstance().getConnection()) {
			conn.setAutoCommit(false);
			
			String sql = "INSERT INTO ers_db.ers_users "
					+ "(ers_username, ers_password, user_first_name,"
					+ "user_last_name, user_email, user_role_id) "
					+ "VALUES (?, ?, ?, ?, ?, ?)";
			
			String key[] = new String[1];
			key[0] = "ers_users_id";
			
			PreparedStatement pstmt = conn.prepareStatement(sql, key);
			pstmt.setString(1, username);
			pstmt.setString(2, password);
			pstmt.setString(3, firstname);
			pstmt.setString(4, lastname);
			pstmt.setString(5, email);
			pstmt.setInt(6, userRoleId);

			int rowsUpdated = pstmt.executeUpdate();
			ResultSet rs = pstmt.getGeneratedKeys();
			
			if(rowsUpdated != 0) {
				while(rs.next()) {
					user.setUserId(rs.getInt(1));
				}
				user.setUsername(username);
				user.setPassword(password);
				user.setFirstname(firstname);
				user.setLastname(lastname);
				user.setEmail(email);
				user.setUserRoleId(userRoleId);
				
				conn.commit();
			}
		}catch(SQLException sqle ) {
			sqle.printStackTrace();
		}
		return user;
	}

	@Override
	public boolean removeUser(User empToRemove) {

		try(Connection conn = ConnectionFactory.getInstance().getConnection()) {
			conn.setAutoCommit(false);
			
			String sql = "DELETE FROM ers_db.ers_users "
						+ "WHERE ers_users_id = ?";
	
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, empToRemove.getUserId());

			int rowsUpdated = pstmt.executeUpdate();
			if(rowsUpdated != 0) {
				conn.commit();
				return true;
			}
		}catch(SQLException sqle ) {
			sqle.printStackTrace();
		}
		return false;
	}

	@Override
	public User getUserByUsername(String username) {

		User user = new User();
		
		try(Connection conn = ConnectionFactory.getInstance().getConnection()) {
			String sql = "SELECT * "
					   + "FROM ers_db.ers_users "
					   + "WHERE ers_username = ?";
			
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, username);
			ResultSet rs = pstmt.executeQuery();
			
			while(rs.next()) {
				user.setUserId(rs.getInt(1));
				user.setUsername(rs.getString(2));
				user.setPassword(rs.getString(3));
				user.setFirstname(rs.getString(4));
				user.setLastname(rs.getString(5));
				user.setEmail(rs.getString(6));
				user.setUserRoleId(rs.getInt(7));
			}
		}catch(SQLException sqle ) {
			sqle.printStackTrace();
		}		
		return user;
	}
	
	@Override
	public User getUserById(int userId) {

		User user = new User();
		
		try(Connection conn = ConnectionFactory.getInstance().getConnection()) {
			String sql = "SELECT * "
					   + "FROM ers_db.ers_users "
					   + "WHERE ers_users_id = ?";
			
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, userId);
			ResultSet rs = pstmt.executeQuery();
			
			while(rs.next()) {
				user.setUserId(userId);
				user.setUsername(rs.getString(2));
				user.setPassword(rs.getString(3));
				user.setFirstname(rs.getString(4));
				user.setLastname(rs.getString(5));
				user.setEmail(rs.getString(6));
				user.setUserRoleId(rs.getInt(7));
			}
		}catch(SQLException sqle ) {
			sqle.printStackTrace();
		}		
		return user;
	}

	@Override
	public User getUserByEmailAddress(String email) {

		User user = new User();
		
		try(Connection conn = ConnectionFactory.getInstance().getConnection()) {
			String sql = "SELECT * "
					   + "FROM ers_db.ers_users "
					   + "WHERE user_email = ?";
			
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, email);
			ResultSet rs = pstmt.executeQuery();
			
			while(rs.next()) {
				user.setUserId(rs.getInt(1));
				user.setUsername(rs.getString(2));
				user.setPassword(rs.getString(3));
				user.setFirstname(rs.getString(4));
				user.setLastname(rs.getString(5));
				user.setEmail(email);
				user.setUserRoleId(rs.getInt(7));
			}
		}catch(SQLException sqle ) {
			sqle.printStackTrace();
		}		
		return user;
	}

	@Override
	public User updateUser(User user) {
		
		User updatedUser = new User();

		try(Connection conn = ConnectionFactory.getInstance().getConnection()) {
			conn.setAutoCommit(false);
			
			String sql = "UPDATE ers_db.ers_users "
						+ "SET ers_users_id = ?, ers_username = ?, ers_password = ?, "
						+ "user_first_name = ?, user_last_name = ?, user_email = ?, "
						+ "user_role_id = ? "
						+ "WHERE ers_users_id = ?";
			
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, user.getUserId());
			pstmt.setString(2, user.getUsername());
			pstmt.setString(3,  user.getPassword());
			pstmt.setString(4, user.getFirstname());
			pstmt.setString(5, user.getLastname());
			pstmt.setString(6,  user.getEmail());
			pstmt.setInt(7, user.getUserRoleId());
			pstmt.setInt(8, user.getUserId());
			
			int rowsUpdated = pstmt.executeUpdate();
			
			if(rowsUpdated != 0) {
				conn.commit();
				updatedUser = getUserById(user.getUserId());
			}
		}catch(SQLException sqle ) {
			sqle.printStackTrace();
		}
		return updatedUser;
	}
	
	@Override
	public boolean updateUserEmail(User userToUpdate) {

		try(Connection conn = ConnectionFactory.getInstance().getConnection()) {
			conn.setAutoCommit(false);
			
			String sql = "UPDATE ers_db.ers_users "
						+ "SET user_email = ? "
						+ "WHERE ers_users_id = ?";
			
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, userToUpdate.getEmail());
			pstmt.setInt(2, userToUpdate.getUserId());

			int rowsUpdated = pstmt.executeUpdate();
			
			if(rowsUpdated != 0) {
				conn.commit();
				return true;
			}
		}catch(SQLException sqle ) {
			sqle.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean isManager(String username) {
		
		try(Connection conn = ConnectionFactory.getInstance().getConnection()) {

			String sql = "SELECT * "
						+ "FROM ers_db.ers_users "
					    + "WHERE user_role_id = ? AND ers_username = ?";
			
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1,2);
			pstmt.setString(2, username);
			ResultSet rs = pstmt.executeQuery();
			if(rs.next()) {
				return true;
			}
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		}
		return false;
	}
	
	@Override
	public List<User> getAllUsers() {
		
		List<User> listOfUsers = new ArrayList<User>();
		
		try(Connection conn = ConnectionFactory.getInstance().getConnection()) {

			String sql = "SELECT * "
						+ "FROM ers_db.ers_users";
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);

			while(rs.next()) {
				User temp = new User();
				temp.setUserId(rs.getInt(1));
				temp.setUsername(rs.getString(2));
				temp.setPassword(rs.getString(3));
				temp.setFirstname(rs.getString(4));
				temp.setLastname(rs.getString(5));
				temp.setEmail(rs.getString(6));
				temp.setUserRoleId(rs.getInt(7));
				listOfUsers.add(temp);
			}
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		}
		return (ArrayList<User>) listOfUsers;
	}

	@Override
	public List<User> getAllUsersByRole(int roleId) {

		List<User> listOfUsers = new ArrayList<User>();
		
		try(Connection conn = ConnectionFactory.getInstance().getConnection()) {

			String getAllUserByRoleCSql = "{call getAllUserByRole(?, ?)"; 
			CallableStatement cstmt = conn.prepareCall(getAllUserByRoleCSql);
			cstmt.setInt(1, roleId);
			cstmt.registerOutParameter(1, OracleTypes.CURSOR);
			cstmt.executeQuery();
			ResultSet rs = (ResultSet)cstmt.getObject(1);

			while(rs.next()) {
				User temp = new User();
				temp.setUserId(rs.getInt(1));
				temp.setUsername(rs.getString(2));
				temp.setPassword(rs.getString(3));
				temp.setFirstname(rs.getString(4));
				temp.setLastname(rs.getString(5));
				temp.setEmail(rs.getString(6));
				temp.setUserRoleId(rs.getInt(7));
				listOfUsers.add(temp);
			}
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		}
		return (ArrayList<User>) listOfUsers;
	}
}
