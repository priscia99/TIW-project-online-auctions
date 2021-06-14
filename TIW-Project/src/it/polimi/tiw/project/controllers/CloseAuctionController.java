package it.polimi.tiw.project.controllers;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDateTime;

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
import it.polimi.tiw.project.utils.ErrorHandler;

@WebServlet("/close-auction")
public class CloseAuctionController extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	private Connection connection = null;
	private TemplateEngine templateEngine; // Used for Thymeleaf

    public CloseAuctionController() {
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

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		ServletContext servletContext = getServletContext();
		HttpSession session = request.getSession();
		if (session.isNew() || session.getAttribute("user") == null) {
			response.sendRedirect(getServletContext().getContextPath() + "/index.html");
			return;
		}
		
		User user = (User) session.getAttribute("user");
		int auctionId = Integer.parseInt(request.getParameter("id"));

		// Update auctionin DB using UserDAO
		try {
			AuctionDAO dao = new AuctionDAO(connection);
			Auction auction = dao.getAuctionDetails(auctionId,user.getLoginTime());
			// check if seller and user IDs match
			if (auction.getSellerId() != user.getId()) {
				throw new Exception("User is not the seller.");
			}
			dao.closeAuction(auctionId);
		} catch (SQLException e) {
			e.printStackTrace();
			final WebContext webContext = new WebContext(request, response, getServletContext(), request.getLocale());
			ErrorHandler.displayErrorPage(webContext, response.getWriter(), templateEngine, "Error while closing the auction, try again.");
			return;
		} catch (Exception e) {
			e.printStackTrace();
			final WebContext webContext = new WebContext(request, response, getServletContext(), request.getLocale());
			ErrorHandler.displayErrorPage(webContext, response.getWriter(), templateEngine, "Error while closing the auction, try again.");
			return;
		}

		// If everything is correct redirect to the sell page
		response.sendRedirect(request.getContextPath() + "/sell");
		// response.sendRedirect(request.getContextPath() + "/details?id=" + auctionId);
	}

}
