package it.polimi.tiw.project.controllers;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

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

@WebServlet("/auctions")
public class AuctionController extends HttpServlet{

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
		int auctionId = Integer.parseInt(request.getParameter("id"));
		AuctionDAO auctionDao = new AuctionDAO(connection);
		Auction auctionDetail = new Auction();
		try {
			auctionDetail = auctionDao.getAuctionDetails(auctionId);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		
		String path = "/WEB-INF/Auction.html";
		ServletContext servletContext = getServletContext();
		final WebContext context = new WebContext(request, response, servletContext, request.getLocale());
		context.setVariable("user", user);
		context.setVariable("auctionDetail", auctionDetail);
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
