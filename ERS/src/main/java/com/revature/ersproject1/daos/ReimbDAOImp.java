package com.revature.ersproject1.daos;

import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.revature.ersproject1.pojos.Receipt;
import com.revature.ersproject1.pojos.Reimb;
import com.revature.ersproject1.utils.ConnectionFactory;

public class ReimbDAOImp implements ReimbDAO {

	@Override
	public Reimb addReimb(Reimb reimb) {

		Reimb newReimb = null;

		try(Connection conn = ConnectionFactory.getInstance().getConnection()) {
			conn.setAutoCommit(false);
			
			String sql = "INSERT INTO ers_db.ers_reimbursement("
					+ "reimb_amount, reimb_description, reimb_author,"
					+ "reimb_status_id, reimb_type_id) "
					+ "VALUES(?, ?, ?, ?, ?)";
			
			String key[] = new String[1];
			key[0] = "reimb_id";
			
			PreparedStatement pstmt = conn.prepareStatement(sql, key);
			pstmt.setDouble(1, reimb.getAmount());
			pstmt.setString(2, reimb.getReimbDescription());
			pstmt.setInt(3, reimb.getReimbAuthor());
			pstmt.setInt(4, reimb.getReimbStatusId());
			pstmt.setInt(5, reimb.getReimbTypeId());

			int rowsUpdated = pstmt.executeUpdate();
			ResultSet rs = pstmt.getGeneratedKeys();
			
			if(rowsUpdated != 0) {
				newReimb = reimb;
				while(rs.next()) {
					newReimb.setReimbId(rs.getInt(1));
				}
				conn.commit();
			}
		}catch(SQLException sqle ) {
			sqle.printStackTrace();
		}
		return getReimbById(newReimb.getReimbId());
	}
	
	@Override
	public Reimb addReimb(double amount, String descr, int author, int type) {

		Reimb reimb = new Reimb();

		try(Connection conn = ConnectionFactory.getInstance().getConnection()) {
			conn.setAutoCommit(false);
			
			String sql = "INSERT INTO ers_db.ers_reimbursement("
					+ "reimb_amount, reimb_description, reimb_author,"
					+ "reimb_status_id, reimb_type_id) "
					+ "VALUES(?, ?, ?, ?, ?)";
			
			String key[] = new String[1];
			key[0] = "reimb_id";
			
			PreparedStatement pstmt = conn.prepareStatement(sql, key);
			pstmt.setDouble(1, amount);
			pstmt.setString(2, descr);
			pstmt.setInt(3, author);
			pstmt.setInt(4, 3);
			pstmt.setInt(5, type);

			int rowsUpdated = pstmt.executeUpdate();
			ResultSet rs = pstmt.getGeneratedKeys();
			
			if(rowsUpdated != 0) {
				while(rs.next()) {
					reimb.setReimbId(rs.getInt(1));
				}
				reimb.setAmount(amount);
				reimb.setReimbDescription(descr);
				reimb.setReimbAuthor(author);
				reimb.setReimbStatusId(3);
				reimb.setReimbTypeId(type);
		
				conn.commit();
			}
		}catch(SQLException sqle ) {
			sqle.printStackTrace();
		}
		return reimb;
	}
	
	@Override
	public Reimb getReimbById(int reimbId) {

		Reimb reimb = new Reimb();
		
		try(Connection conn = ConnectionFactory.getInstance().getConnection()) {
			String sql = "SELECT * "
					   + "FROM ers_db.ers_reimbursement "
					   + "WHERE reimb_id = ?";
			
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, reimbId);
			ResultSet rs = pstmt.executeQuery();
			
			while(rs.next()) {
				reimb.setReimbId(reimbId);
				reimb.setAmount(rs.getDouble(2));
				reimb.setReimbSubmitted(rs.getTimestamp(3));
				reimb.setReimbResolved(rs.getTimestamp(4));
				reimb.setReimbDescription(rs.getString(5));
				reimb.setReimReceipt((Receipt) rs.getBlob(6));
				reimb.setReimbAuthor(rs.getInt(7));
				reimb.setReimbResolver(rs.getShort(8));
				reimb.setReimbStatusId(rs.getInt(9));
				reimb.setReimbTypeId(rs.getInt(10));
			}
		}catch(SQLException sqle ) {
			sqle.printStackTrace();
		}		
		return reimb;
	}

	@Override
	public boolean removeReimb(Reimb reimbToRemove) {

		try(Connection conn = ConnectionFactory.getInstance().getConnection()) {
			conn.setAutoCommit(false);
			
			String sql = "DELETE FROM ers_db.ers_reimbursement "
						+ "WHERE reimb_id = ?";
	
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, reimbToRemove.getReimbId());

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
	public Reimb updateReimb(Reimb reimbToUpdate) {
		
		Reimb updateReimb = new Reimb();

		try(Connection conn = ConnectionFactory.getInstance().getConnection()) {
			conn.setAutoCommit(false);
			
			String sql = "UPDATE ers_db.ers_reimbursement "
					+ "SET reimb_id = ?, reimb_amount = ?, "
					+ "reimb_resolved = ?, reimb_description = ?, reimb_receipt = ?, "
					+ "reimb_author = ?, reimb_resolver = ?, reimb_status_id = ?, "
					+ "reimb_type_id = ? "
					+ "WHERE reimb_id = ?";
			
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, reimbToUpdate.getReimbId());
			pstmt.setDouble(2, reimbToUpdate.getAmount());
			pstmt.setTimestamp(3, reimbToUpdate.getReimbResolved());
			pstmt.setString(4, reimbToUpdate.getReimbDescription());
			pstmt.setBlob(5, (Blob) reimbToUpdate.getReimReceipt());
			pstmt.setInt(6, reimbToUpdate.getReimbAuthor());
			pstmt.setInt(7, reimbToUpdate.getReimbResolver());
			pstmt.setInt(8, reimbToUpdate.getReimbStatusId());
			pstmt.setInt(9, reimbToUpdate.getReimbTypeId());
			pstmt.setInt(10, reimbToUpdate.getReimbId());

			int rowsUpdated = pstmt.executeUpdate();
			
			if(rowsUpdated != 0) {
				conn.commit();
				updateReimb = getReimbById(reimbToUpdate.getReimbId());
			}
		}catch(SQLException sqle ) {
			sqle.printStackTrace();
		}
		return updateReimb;
	}
	
	@Override
	public boolean updateReimbStatus(Reimb reimbToUpdate) {

		try(Connection conn = ConnectionFactory.getInstance().getConnection()) {
			conn.setAutoCommit(false);
			
			String sql = "UPDATE ers_db.ers_reimbursement "
						+ "SET reimb_status_id = ?, reimb_resolver = ?, reimb_resolved = ? "
						+ "WHERE reimb_id = ?";
			
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, reimbToUpdate.getReimbStatusId());
			pstmt.setInt(2,  reimbToUpdate.getReimbResolver());
			pstmt.setTimestamp(3, reimbToUpdate.getReimbResolved());
			pstmt.setInt(4, reimbToUpdate.getReimbId());

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
	public boolean updateReimbType(Reimb reimbToUpdate) {
		
		try(Connection conn = ConnectionFactory.getInstance().getConnection()) {
			conn.setAutoCommit(false);
			
			String sql = "UPDATE ers_db.ers_reimbursement "
						+ "SET reimb_type_id = ? "
						+ "WHERE reimb_id = ?";
			
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, reimbToUpdate.getReimbTypeId());
			pstmt.setInt(2,  reimbToUpdate.getReimbId());

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
	public List<Reimb> getAllReimbByAuthor(int authorId) {
		
		List<Reimb> listOfReimbByAuthor = new ArrayList<Reimb>();

		try(Connection conn = ConnectionFactory.getInstance().getConnection()) {

			String sql = "SELECT * "
						+ "FROM ers_db.ers_reimbursement "
					    + "WHERE reimb_author = ?";
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, authorId);
			ResultSet rs = pstmt.executeQuery();

			while(rs.next()) {
				Reimb temp = new Reimb();
				temp.setReimbId(rs.getInt(1));
				temp.setAmount(rs.getDouble(2));
				temp.setReimbSubmitted(rs.getTimestamp(3));
				temp.setReimbResolved(rs.getTimestamp(4));
				temp.setReimbDescription(rs.getString(5));
				temp.setReimbAuthor(authorId);
				temp.setReimbResolver(rs.getInt(8));
				temp.setReimbStatusId(rs.getInt(9));
				temp.setReimbTypeId(rs.getInt(10));
				listOfReimbByAuthor.add(temp);
			}
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		}
		return (ArrayList<Reimb>) listOfReimbByAuthor;
	}

	@Override
	public List<Reimb> getAllReimbByType(int typeId) {

		List<Reimb> listOfReimbByType = new ArrayList<Reimb>();

		try(Connection conn = ConnectionFactory.getInstance().getConnection()) {

			String sql = "SELECT * "
						+ "FROM ers_db.ers_reimbursement "
					    + "WHERE reimb_type_id = ?";
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, typeId);
			ResultSet rs = pstmt.executeQuery();

			while(rs.next()) {
				Reimb temp = new Reimb();
				temp.setReimbId(rs.getInt(1));
				temp.setAmount(rs.getDouble(2));
				temp.setReimbSubmitted(rs.getTimestamp(3));
				temp.setReimbResolved(rs.getTimestamp(4));
				temp.setReimbDescription(rs.getString(5));
				temp.setReimbAuthor(rs.getInt(7));
				temp.setReimbResolver(rs.getInt(8));
				temp.setReimbStatusId(rs.getInt(9));
				temp.setReimbTypeId(typeId);
				listOfReimbByType.add(temp);
			}
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		}
		return (ArrayList<Reimb>) listOfReimbByType;
	}

	@Override
	public List<Reimb> getAllReimbByStatus(int statusId) {

		List<Reimb> listOfReimbByStatus = new ArrayList<Reimb>();

		try(Connection conn = ConnectionFactory.getInstance().getConnection()) {

			String sql = "SELECT * "
						+ "FROM ers_db.ers_reimbursement "
					    + "WHERE reimb_status_id = ?";
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, statusId);
			ResultSet rs = pstmt.executeQuery();

			while(rs.next()) {
				Reimb temp = new Reimb();
				temp.setReimbId(rs.getInt(1));
				temp.setAmount(rs.getDouble(2));
				temp.setReimbSubmitted(rs.getTimestamp(3));
				temp.setReimbResolved(rs.getTimestamp(4));
				temp.setReimbDescription(rs.getString(5));
				temp.setReimbAuthor(rs.getInt(7));
				temp.setReimbResolver(rs.getInt(8));
				temp.setReimbStatusId(statusId);
				temp.setReimbTypeId(rs.getInt(10));
				listOfReimbByStatus.add(temp);
			}
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		}
		return (ArrayList<Reimb>) listOfReimbByStatus;
	}

	@Override
	public List<Reimb> getAllUserReimbs() {
		
		List<Reimb> listOfReimbs = new ArrayList<Reimb>(); 
		
		try(Connection conn = ConnectionFactory.getInstance().getConnection()) {

			String sql = "SELECT * "
						+ "FROM ers_db.ers_reimbursement";
			
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
		
			while(rs.next()) {
				Reimb temp = new Reimb();
				temp.setReimbId(rs.getInt(1));
				temp.setAmount(rs.getDouble(2));
				temp.setReimbSubmitted(rs.getTimestamp(3));
				temp.setReimbResolved(rs.getTimestamp(4));
				temp.setReimbDescription(rs.getString(5));
				temp.setReimbAuthor(rs.getInt(7));
				temp.setReimbResolver(rs.getInt(8));
				temp.setReimbStatusId(rs.getInt(9));
				temp.setReimbTypeId(rs.getInt(10));
				listOfReimbs.add(temp);
			}
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		}
		return (ArrayList<Reimb>) listOfReimbs;
	}
}
