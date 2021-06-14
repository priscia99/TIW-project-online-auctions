package it.polimi.tiw.project.controllers;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringEscapeUtils;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;

import it.polimi.tiw.project.beans.Auction;
import it.polimi.tiw.project.beans.User;
import it.polimi.tiw.project.dao.AuctionDAO;
import it.polimi.tiw.project.utils.ConnectionHandler;
import it.polimi.tiw.project.utils.ErrorHandler;

@WebServlet("/buy")
public class BuyController extends HttpServlet{

	private static final long serialVersionUID = 1L;
	private TemplateEngine templateEngine;				// engine to display page
	private Connection connection = null;
	
	public void init() throws ServletException {
		ServletContext servletContext = getServletContext();													// get servlet context
		ServletContextTemplateResolver templateResolver = new ServletContextTemplateResolver(servletContext);	// get template resolver
		templateResolver.setTemplateMode(TemplateMode.HTML);
		this.templateEngine = new TemplateEngine();																// get template engine
		this.templateEngine.setTemplateResolver(templateResolver);
		templateResolver.setSuffix(".html");
		connection = ConnectionHandler.getConnection(getServletContext());
	}
	
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// If the user is not logged in (not present in session) redirect to the login
		HttpSession session = request.getSession();
		if (session.isNew() || session.getAttribute("user") == null) {
			String loginpath = getServletContext().getContextPath() + "/index.html";
			response.sendRedirect(loginpath);
			return;
		}
		
		User user = (User) session.getAttribute("user");
	
		AuctionDAO actionDao = new AuctionDAO(connection);
		ArrayList<Auction> wonAuctions = new ArrayList<>();
		try {
			wonAuctions = actionDao.getUserWonAuctionsList(user.getId(), user.getLoginTime());
		}catch(Exception e) {
			e.printStackTrace();
			final WebContext webContext = new WebContext(request, response, getServletContext(), request.getLocale());
			ErrorHandler.displayErrorPage(webContext, response.getWriter(), templateEngine, "Error while retrieving won auctions, try again.");
			return;
		}
		
		String path = "/WEB-INF/Buy.html";
		ServletContext servletContext = getServletContext();
		final WebContext context = new WebContext(request, response, servletContext, request.getLocale());
		context.setVariable("user", user);
		context.setVariable("wonAuctions", wonAuctions);
		templateEngine.process(path, context, response.getWriter());
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// If the user is not logged in (not present in session) redirect to the login
		HttpSession session = request.getSession();
		if (session.isNew() || session.getAttribute("user") == null) {
			String loginpath = getServletContext().getContextPath() + "/index.html";
			response.sendRedirect(loginpath);
			return;
		}
		
		User user = (User) session.getAttribute("user");
		String query = StringEscapeUtils.escapeJava(request.getParameter("query"));
		AuctionDAO actionDao = new AuctionDAO(connection);
		ArrayList<Auction> openAuctions = new ArrayList<>();
		ArrayList<Auction> wonAuctions = new ArrayList<>();
		try {
			wonAuctions = actionDao.getUserWonAuctionsList(user.getId(), user.getLoginTime());
		}catch(Exception e) {
			e.printStackTrace();
			final WebContext webContext = new WebContext(request, response, getServletContext(), request.getLocale());
			ErrorHandler.displayErrorPage(webContext, response.getWriter(), templateEngine, "Error while retrieving won auctions, try again.");
			return;
		}
		try {
			// the expiration date is later than the time of sending
			openAuctions = actionDao.filter(query,LocalDateTime.now());	
			for(Auction auction : openAuctions) {
				System.out.println("Price: " + auction.getCurrentPrice());
			}
		}catch(Exception e) {
			e.printStackTrace();
			final WebContext webContext = new WebContext(request, response, getServletContext(), request.getLocale());
			ErrorHandler.displayErrorPage(webContext, response.getWriter(), templateEngine, "Error while filtering auctions, try again.");
			return;
		}
		
		String path = "/WEB-INF/Buy.html";
		ServletContext servletContext = getServletContext();
		final WebContext context = new WebContext(request, response, servletContext, request.getLocale());
		context.setVariable("user", user);
		context.setVariable("query", query);
		context.setVariable("openAuctions", openAuctions);
		context.setVariable("wonAuctions", wonAuctions);
		templateEngine.process(path, context, response.getWriter());
	}
	
	public void destroy() {
		try {
			ConnectionHandler.closeConnection(connection);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
