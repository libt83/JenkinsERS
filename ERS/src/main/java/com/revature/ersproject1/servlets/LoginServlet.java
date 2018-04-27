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
 * Servlet implementation class LoginServlet
 */
public class LoginServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	private static UserService userServ = new UserService();
       

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getRequestDispatcher("Views/index.html").include(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		
		String username = request.getParameter("username");
		String password = request.getParameter("password");
	
		User user = new User();
		
		user.setUsername(username);
		user.setPassword(password);
		
		user = userServ.loginUser(user);
		if(user != null) {
			session.setAttribute("user", user);
			if(userServ.isUserAManager(user.getUsername())) {
				response.sendRedirect("manager");					
			}else {
				response.sendRedirect("employee");				
			}
		}else {
			session.invalidate();
			response.sendRedirect("login");	
		}
	}
}
