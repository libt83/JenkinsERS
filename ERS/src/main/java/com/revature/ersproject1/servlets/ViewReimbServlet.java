package com.revature.ersproject1.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.ersproject1.pojos.Reimb;
import com.revature.ersproject1.pojos.User;
import com.revature.services.ReimbService;

/**
 * Servlet implementation class ViewReimb
 */
public class ViewReimbServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	ReimbService rs = new ReimbService();

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// Get current user from session in order to get all their
		// associated reimbursements
		HttpSession session = request.getSession();
		User u = (User) session.getAttribute("user");

		response.setContentType("application/json");

		ObjectMapper om = new ObjectMapper();
		PrintWriter pw = response.getWriter();
		
		List<Reimb> reimb = rs.getAllReimbByAuthor(u.getUserId());
		String reimbs = om.writeValueAsString(reimb);
		pw.write(reimbs);
	}
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		doGet(request, response);
	}

}
