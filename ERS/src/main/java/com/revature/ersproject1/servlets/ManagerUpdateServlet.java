package com.revature.ersproject1.servlets;

import java.io.IOException;
import java.sql.Timestamp;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.revature.ersproject1.pojos.Reimb;
import com.revature.ersproject1.pojos.User;
import com.revature.services.ReimbService;

/**
 * Servlet implementation class ManagerUpdateServlet
 */
public class ManagerUpdateServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	ReimbService rs = new ReimbService();

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(true);
		User u = (User) session.getAttribute("user");
		
		String id = request.getParameter("ids");
		String update = request.getParameter("update");
		int statusId;
		
		if(update.equals("Approved")) {
			statusId = 1;
		}else {
			statusId = 2;
		}
		
		Reimb reimb = rs.getReimbByReimbId(Integer.parseInt(id));
		reimb.setReimbStatusId(statusId);
		reimb.setReimbResolver(u.getUserId());
		reimb.setReimbResolved(new Timestamp(System.currentTimeMillis()));
		rs.updateReimb(reimb);
		response.sendRedirect("manager");
	}
}
