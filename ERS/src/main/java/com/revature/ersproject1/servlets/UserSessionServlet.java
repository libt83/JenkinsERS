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
import com.revature.services.UserService;

/**
 * Servlet implementation class UserSessionServlet
 */
public class UserSessionServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	UserService us = new UserService();
	
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");

		response.setContentType("application/json");

		ObjectMapper om = new ObjectMapper();
		PrintWriter pw = response.getWriter();
		
		user = us.getUserById(user.getUserId());
		String reimbs = om.writeValueAsString(user);
		pw.write(reimbs);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
