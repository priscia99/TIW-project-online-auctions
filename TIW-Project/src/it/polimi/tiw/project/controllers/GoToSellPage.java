package it.polimi.tiw.project.controllers;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;

import it.polimi.tiw.project.beans.Auction;
import it.polimi.tiw.project.beans.User;
import it.polimi.tiw.project.utils.ConnectionHandler;

/**
 * Servlet implementation class GoToSellPage
 */
@WebServlet("/GoToSellPage")
public class GoToSellPage extends HttpServlet {
	
	private static final long serialVersionUID = 1L;	// session id
	private TemplateEngine templateEngine;				// engine to display page
	private Connection connection = null;				// connection to DB

       
    public GoToSellPage() {
        super();
    }
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// If the user is not logged in (not present in session) redirect to the login
		String loginpath = getServletContext().getContextPath() + "/index.html";
		HttpSession session = request.getSession();
		if (session.isNew() || session.getAttribute("user") == null) {
			response.sendRedirect(loginpath);
			return;
		}
		User user = (User) session.getAttribute("user");
		
		// Get lists of open auctions and close auctions from DB
		
		
		// Redirect to the Sell page and add missions to the parameters
		String path = "/WEB-INF/Sell.html";
		ServletContext servletContext = getServletContext();
		final WebContext context = new WebContext(request, response, servletContext, request.getLocale());
		context.setVariable("user", user);
		templateEngine.process(path, context, response.getWriter());
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.sendError(HttpServletResponse.SC_BAD_REQUEST, "POST is not allowed");
	}
	
	public void destroy() {
		try {
			ConnectionHandler.closeConnection(connection);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	

}
