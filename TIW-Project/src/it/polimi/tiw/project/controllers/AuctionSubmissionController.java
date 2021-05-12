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

@WebServlet("/submit-auction")
@MultipartConfig
public class AuctionSubmissionController extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private Connection connection = null;
	private TemplateEngine templateEngine; // Used for Thymeleaf

	public AuctionSubmissionController() {
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
		ServletContext servletContext = getServletContext();
		boolean badRequest = false;
		String path = null; // Path to the right next page
		HttpSession session = request.getSession();
		String loginpath = getServletContext().getContextPath() + "/index.html";
		if (session.isNew() || session.getAttribute("user") == null) {
			response.sendRedirect(loginpath);
			return;
		}

		User user = (User) session.getAttribute("user");

		// Declaration of parameters given by user
		String itemName = null;
		String itemDescription = null;
		InputStream imageStream = null;
		String auctionStartingPrice = null;
		String auctionMinimumRise = null;
		String auctionEndTimestamp = null;

		try {
			// Parsing parameters from user request
			itemName = request.getParameter("item-name");
			itemDescription = request.getParameter("item-descritpion");
			Part imagePart = request.getPart("item-image");
			String mimeType = null;
			if (imagePart != null) {
				imageStream = imagePart.getInputStream();
				String filename = imagePart.getSubmittedFileName();
				mimeType = getServletContext().getMimeType(filename);
			}

			auctionStartingPrice = request.getParameter("auction-starting-price");
			auctionMinimumRise = request.getParameter("auction-minimum-rise");
			auctionEndTimestamp = request.getParameter("auction-end-timestamp");

			badRequest = itemName == null || itemName.isEmpty() || itemDescription == null || itemDescription.isEmpty()
					|| imageStream == null || (imageStream.available() == 0) || !mimeType.startsWith("image/")
					|| auctionStartingPrice.isEmpty() || auctionMinimumRise.isEmpty() || auctionEndTimestamp.isEmpty();
		} catch (Exception e) {
			badRequest = true;
			e.printStackTrace();
		}

		// Responde with bad request
		if (badRequest) {
			final WebContext context = new WebContext(request, response, servletContext, request.getLocale());
			context.setVariable("signupInfoMsg", "Missing or empty parameters");
			path = "/signup.html"; // If it is a bad request, re-direct to sign up page and show an error
			templateEngine.process(path, context, response.getWriter());
			return;
		}

		AuctionDAO dao = new AuctionDAO(connection);
		// Create user in DB using UserDAO
		try {
			dao.createAuctionItem(itemName, itemDescription, imageStream, user.getId(), Float.parseFloat(auctionMinimumRise),
					Float.parseFloat(auctionStartingPrice), auctionEndTimestamp);
		} catch (SQLException e) {
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
					"Internal error while trying to create a new auction: SQL error ");
			e.printStackTrace();
			return;
		}

		// Finally, if everything is correct		
		response.sendRedirect(request.getContextPath() + "/sell");
	}

}
