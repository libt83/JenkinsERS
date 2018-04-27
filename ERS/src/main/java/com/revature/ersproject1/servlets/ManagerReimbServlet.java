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
 * Servlet implementation class ManagerReimbServlet
 */
public class ManagerReimbServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	ReimbService rs = new ReimbService();
       
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		response.setContentType("application/json");
		User user = (User) session.getAttribute("user");
		ObjectMapper om = new ObjectMapper();
		PrintWriter pw = response.getWriter();
		
		List<Reimb> reimb = rs.getAllUserReimbs();
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
