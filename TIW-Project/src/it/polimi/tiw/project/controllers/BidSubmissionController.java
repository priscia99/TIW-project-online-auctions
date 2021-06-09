package it.polimi.tiw.project.controllers;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDateTime;

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

import it.polimi.tiw.project.beans.Auction;
import it.polimi.tiw.project.beans.User;
import it.polimi.tiw.project.dao.AuctionDAO;
import it.polimi.tiw.project.utils.ConnectionHandler;
import it.polimi.tiw.project.utils.ErrorHandler;

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
			final WebContext webContext = new WebContext(request, response, servletContext, request.getLocale());
			ErrorHandler.displayErrorPage(webContext, response.getWriter(), templateEngine, "Missing or wrong paramethers, try again.");
			return;
		}

		// Create bid in DB using bidDAO
		try {
			AuctionDAO dao = new AuctionDAO(connection);
			Auction auction = dao.getAuctionDetails(auctionId, LocalDateTime.now());
			if(auction.getMaxBid() != null && auction.getMaxBid().getPrice() + auction.getMinimumRise() > price 
					|| auction.getStartingPrice() + auction.getMinimumRise()  > price ) {
				throw new Exception("Bid price does not pass validation");
			}
			dao.addBidToAuction(auctionId, bidderId, price);

		} catch (Exception e) {
			e.printStackTrace();
			final WebContext webContext = new WebContext(request, response, servletContext, request.getLocale());
			ErrorHandler.displayErrorPage(webContext, response.getWriter(), templateEngine, "Error while creating the bid, check your price and try again.");
			return;
		}

		// If everything is correct redirect to the auction page
		response.sendRedirect(request.getContextPath() + "/auctions?id=" + auctionId);
	}

}
