package it.polimi.tiw.project.controllers;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;

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
import it.polimi.tiw.project.beans.Bid;
import it.polimi.tiw.project.beans.Item;
import it.polimi.tiw.project.beans.User;
import it.polimi.tiw.project.dao.AuctionDAO;
import it.polimi.tiw.project.dao.UserDAO;
import it.polimi.tiw.project.utils.ConnectionHandler;

/**
 * Servlet implementation class AuctionSubmissionController
 */
@WebServlet("/submit-auction")
public class AuctionSubmissionController extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	private Connection connection = null;
	private TemplateEngine templateEngine;	// Used for Thymeleaf
	
    public AuctionSubmissionController() {
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
		response.sendError(HttpServletResponse.SC_BAD_REQUEST, "GET is not allowed");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ServletContext servletContext = getServletContext();
		boolean badRequest = false;
		String path = null;	// Path to the right next page
		HttpSession session = request.getSession();
		
		User user = (User) session.getAttribute("user");
		Auction auction = null;
		Item item = null;
		
		// Declaration of parameters given by user
		String itemName = null;
		String itemDescription = null;
		String itemImgFilename = null;
		String auctionStartingPrice = null;
		String auctionMinimumRise = null;
		String auctionEndTimestamp = null;
		
		try {
			// Parsing parameters from user request
			itemName = StringEscapeUtils.escapeJava(request.getParameter("item-name"));
			itemDescription = StringEscapeUtils.escapeJava(request.getParameter("item-descritpion"));
			//itemImgFilename = StringEscapeUtils.escapeJava(request.getParameter("item-img-filename"));
			itemImgFilename = "unknown";
			auctionStartingPrice = StringEscapeUtils.escapeJava(request.getParameter("auction-starting-price"));
			auctionMinimumRise = StringEscapeUtils.escapeJava(request.getParameter("auction-minimum-rise"));		
			auctionEndTimestamp = StringEscapeUtils.escapeJava(request.getParameter("auction-end-timestamp"));
			item = new Item(itemName, itemDescription, itemImgFilename);
			auction = new Auction(Float.parseFloat(auctionStartingPrice), Float.parseFloat(auctionMinimumRise), auctionEndTimestamp, item, user.getId());
			
			badRequest = itemName.isEmpty() || itemDescription.isEmpty() || itemImgFilename.isEmpty() || 
					auctionStartingPrice.isEmpty() || auctionMinimumRise.isEmpty() || auctionEndTimestamp.isEmpty();			
		}
		catch(Exception e){
			badRequest = true;
			e.printStackTrace();
		}
		
		// Responde with bad request
		if(badRequest) {
			final WebContext context = new WebContext(request, response, servletContext, request.getLocale());
			context.setVariable("signupInfoMsg", "Missing or empty parameters");
			path = "/signup.html";	// If it is a bad request, re-direct to sign up page and show an error
			templateEngine.process(path, context, response.getWriter());
			return;
		}
		
		AuctionDAO dao = new AuctionDAO(connection);
		// Create user in DB using UserDAO
		try {
			auction = dao.createItemAuction(auction, item);
		}catch(SQLException e) {
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Internal error while trying to create a new auction " + e.getMessage());
			e.printStackTrace();
			return;
		}
				
		// Finally, if everything is correct
		final WebContext webContext = new WebContext(request, response, servletContext, request.getLocale());
		webContext.setVariable("loginInfoMsg", "User created successfully!");
		path = "/index.html";	// Re-direct user to login page after successful signup
		templateEngine.process(path, webContext, response.getWriter());
	}

}
