package it.polimi.tiw.project.controllers;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;

import it.polimi.tiw.project.beans.User;
import it.polimi.tiw.project.dao.AuctionDAO;
import it.polimi.tiw.project.utils.ConnectionHandler;

@WebServlet("/submit-bid")
public class BidSubmissionController extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private Connection connection = null;
	private TemplateEngine templateEngine; // Used for Thymeleaf

	public BidSubmissionController() {
		super();
	}

	public void init() throws ServletException {
		connection = ConnectionHandler.getConnection(getServletContext());
		ServletContext servletContext = getServletContext();
		ServletContextTemplateResolver templateResolver = new ServletContextTemplateResolver(servletContext);
		templateResolver.setTemplateMode(TemplateMode.HTML);
		this.templateEngine = new TemplateEngine();
		this.templateEngine.setTemplateResolver(templateResolver);
		templateResolver.setSuffix(".html");
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.sendError(HttpServletResponse.SC_BAD_REQUEST, "GET is not allowed");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		boolean badRequest = false;
		ServletContext servletContext = getServletContext();
		HttpSession session = request.getSession();
		if (session.isNew() || session.getAttribute("user") == null) {
			response.sendRedirect(getServletContext().getContextPath() + "/index.html");
			return;
		}

		User user = (User) session.getAttribute("user");

		// Declaration of parameters given by user
		float price = -1;
		int auctionId = -1;
		int bidderId = user.getId();

		// Parse parameters from user request
		try {
			price = Float.parseFloat(request.getParameter("price"));
			auctionId = Integer.parseInt(request.getParameter("auctionId"));
			badRequest = price == -1 ; 
		} catch (Exception e) {
			badRequest = true;
			e.printStackTrace();
		}

		// Respond to bad request
		if (badRequest) {
			final WebContext context = new WebContext(request, response, servletContext, request.getLocale());
			context.setVariable("signupInfoMsg", "Missing or empty parameters");
			templateEngine.process("/signup.html", context, response.getWriter());
			return;
		}

		// Create bid in DB using bidDAO
		try {
			AuctionDAO dao = new AuctionDAO(connection);
			dao.addBidToAuction(auctionId, bidderId, price);

		} catch (SQLException e) {
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
					"Internal error while trying to create a new bid: SQL error ");
			e.printStackTrace();
			return;
		}

		// If everything is correct redirect to the auction page
		response.sendRedirect(request.getContextPath() + "/auctions?id=" + auctionId);
	}

}
