package com.revature.ersproject1.servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.revature.ersproject1.pojos.User;
import com.revature.services.UserService;

/**
 * Servlet implementation class AddUserServlet
 */
public class AddUserServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	private static final String MANAGER_PIN = "12345678";
	
	UserService us = new UserService();

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		
		String firstname = request.getParameter("firstname");
		String lastname = request.getParameter("lastname");
		String email = request.getParameter("email");
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String pin = request.getParameter("pin");
		
		// Checks if username and email are unique
		if(us.isEmailAvailable(email) && us.isUsernameAvailable(username)) {
			
			User user = new User();
			user.setFirstname(firstname);
			user.setLastname(lastname);
			user.setEmail(email);
			user.setUsername(username);
			user.setPassword(password);
			
			// Determine if manager PIN was entered into form
			if(MANAGER_PIN.equals(pin)) {
				user.setUserRoleId(2);
			}else {
				user.setUserRoleId(1);
			}
			
			us.createUser(user);
			response.sendRedirect("login");
		}else {
			response.sendRedirect("register");
		}
	}
}
