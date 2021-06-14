package it.polimi.tiw.project.controllers;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.Duration;
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
import it.polimi.tiw.project.beans.Bid;
import it.polimi.tiw.project.beans.User;
import it.polimi.tiw.project.dao.AuctionDAO;
import it.polimi.tiw.project.dao.UserDAO;
import it.polimi.tiw.project.utils.ConnectionHandler;
import it.polimi.tiw.project.utils.ErrorHandler;

@WebServlet("/details")
public class DetailsController extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	private Connection connection = null;
	private TemplateEngine templateEngine; // Used for Thymeleaf
       
    public DetailsController() {
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

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// set request encoding to match the project character encoding (utf-8)
		request.setCharacterEncoding("UTF-8");
		
		ServletContext servletContext = getServletContext();
		String path = null; // Path to the right next page
		HttpSession session = request.getSession();
		String loginpath = getServletContext().getContextPath() + "/index.html";
		if (session.isNew() || session.getAttribute("user") == null) {
			response.sendRedirect(loginpath);
			return;
		}
		
		User user = (User) session.getAttribute("user");
		AuctionDAO auctionDao = new AuctionDAO(connection);
		
		try {
			Auction auction = auctionDao.getAuctionDetails(Integer.parseInt(request.getParameter("id")), user.getLoginTime());
			
			final WebContext context = new WebContext(request, response, servletContext, request.getLocale());
			auction.calculateTimeLeft(user.getLoginTime());
			System.out.println(auction.getCurrentPrice());
			context.setVariable("auction", auction);
			
			if (!auction.isOpen()) {
				Bid maxBid = auction.getMaxBid();
				User winner = null;
				if (maxBid != null) {
					int winnerId = maxBid.getBidderId();
					UserDAO userDao = new UserDAO(connection);
					winner = userDao.getUser(winnerId);
				}
				context.setVariable("winner", winner);
			}
			
			path = "/WEB-INF/Details.html";
			templateEngine.process(path, context, response.getWriter());
		} catch (Exception e) {
			e.printStackTrace();
			final WebContext webContext = new WebContext(request, response, getServletContext(), request.getLocale());
			ErrorHandler.displayErrorPage(webContext, response.getWriter(), templateEngine, "Cannot retrieve auction details.");
			return;
		}
		
	}

}
