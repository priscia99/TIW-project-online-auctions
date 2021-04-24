package it.polimi.tiw.project.controllers;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import it.polimi.tiw.project.beans.User;

/**
 * Servlet implementation class GoToHomePage
 */
@WebServlet("/Home")
public class GoToHomePage extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public GoToHomePage() {
        super();
        // TODO Auto-generated constructor stub
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		User user = null;
		// Check if user is logged in; if not, then re-direct him to login page
		HttpSession session = request.getSession();
		if (session.isNew() || session.getAttribute("user") == null) {
			String loginpath = getServletContext().getContextPath() + "/index.html";
			response.sendRedirect(loginpath);
			return;
		}
		
		try {
			user = (User) session.getAttribute("user");
		}catch(Exception e) {
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "General error");
		}
		
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
		response.getWriter().append("\n\nThis is the Main Page\n\n");
		response.getWriter().append("Waiting for Il Pizza to create the main page template\n");
		response.getWriter().append("I wish it will not be too much rounded\n");
		response.getWriter().append("Anyway, just few information to check that il server non abbia scazzato:\n\n");
		response.getWriter().append("Username: ").append(user.getUsername());
		response.getWriter().append("\nName: ").append(user.getName());
		response.getWriter().append("\nSurname: ").append(user.getSurname());
		response.getWriter().append("\nAddress: ").append(user.getAddress());
		
	
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
	
	

}
