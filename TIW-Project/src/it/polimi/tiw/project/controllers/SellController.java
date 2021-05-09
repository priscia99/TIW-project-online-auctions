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
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;

import it.polimi.tiw.project.beans.Auction;
import it.polimi.tiw.project.beans.User;
import it.polimi.tiw.project.dao.AuctionDAO;
import it.polimi.tiw.project.utils.ConnectionHandler;

/**
 * Servlet implementation class GoToSellPage
 */
@WebServlet("/sell")
public class SellController extends HttpServlet {
	
	private static final long serialVersionUID = 1L;	// session id
	private TemplateEngine templateEngine;				// engine to display page
	private Connection connection = null;				// connection to DB

       
    public SellController() {
        super();
    }
    

    public void init() throws ServletException{
    	connection = ConnectionHandler.getConnection(getServletContext());
    	ServletContext servletContext = getServletContext();
    	ServletContextTemplateResolver templateResolver = new ServletContextTemplateResolver(servletContext);
    	templateResolver.setTemplateMode(TemplateMode.HTML);
		this.templateEngine = new TemplateEngine();
		this.templateEngine.setTemplateResolver(templateResolver);
		templateResolver.setSuffix(".html");
    }
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// If the user is not logged in (not present in session) redirect to the login
		String loginpath = getServletContext().getContextPath() + "/index.html";
		HttpSession session = request.getSession();
		if (session.isNew() || session.getAttribute("user") == null) {
			response.sendRedirect(loginpath);
			return;
		}
		// Retrieve user data
		User user = (User) session.getAttribute("user");
		
		// Get lists of open auctions and close auctions from DB
		AuctionDAO dao = new AuctionDAO(connection);
		ArrayList<Auction> openAuctions = new ArrayList<>();
		ArrayList<Auction> closeAuctions = new ArrayList<>();
		try {
			openAuctions = dao.getUserOpenAuctions(user);
		} catch (SQLException e) {
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Internal error while trying to retrieve user's open auctions");
			e.printStackTrace();	// REMOVE BEFORE FLIGHT
		}
		try {
			closeAuctions = dao.getUserCloseAuctions(user);
		} catch (SQLException e) {
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Internal error while trying to retrieve user's closed auctions");
			e.printStackTrace();	// REMOVE BEFORE FLIGHT
		}
		
		// Redirect to the Sell page and add auctions to the parameters
		String path = "/WEB-INF/Sell.html";
		ServletContext servletContext = getServletContext(); // REMOVED
		final WebContext context = new WebContext(request, response, servletContext, request.getLocale());
		// Pass page/servlet variables to context (for thymeleaf)
		context.setVariable("user", user);
		context.setVariable("openAuctions", openAuctions);
		context.setVariable("closeAuctions", closeAuctions);
		// Do redirect
		templateEngine.process(path, context, response.getWriter());
	}
	
	// Servlet only allow get requests
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
