package com.revature.ersproject1.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.revature.ersproject1.pojos.Reimb;
import com.revature.ersproject1.pojos.User;
import com.revature.services.ReimbService;

/**
 * Servlet implementation class AddReimbServlet
 */
public class AddReimbServlet extends HttpServlet {
	
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
		HttpSession session = request.getSession();
		User u = (User) session.getAttribute("user");
		
		String amount = request.getParameter("amount");
		String descript = request.getParameter("descript");
		String type = request.getParameter("type");
		
		int typeId;
		
		if(type.equals("Lodging")) {
			typeId = 1;
		}else if(type.equals("Travel")) {
			typeId = 2;
		}else if(type.equals("Food")) {
			typeId = 3;
		}else {
			typeId = 4;
		}
		
		Reimb reimb = new Reimb();
		reimb.setAmount(Double.parseDouble(amount));
		reimb.setReimbAuthor(u.getUserId());
		reimb.setReimbDescription(descript);
		reimb.setReimbTypeId(typeId);
		reimb.setReimbStatusId(3);
		rs.createReimb(reimb);
		response.sendRedirect("employee");
	}

}
