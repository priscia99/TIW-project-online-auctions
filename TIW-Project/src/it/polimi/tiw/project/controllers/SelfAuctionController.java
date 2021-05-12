package it.polimi.tiw.project.controllers;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.time.LocalDateTime;
import java.util.ArrayList;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.thymeleaf.TemplateEngine;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;

import it.polimi.tiw.project.beans.User;
import it.polimi.tiw.project.dao.AuctionDAO;
import it.polimi.tiw.project.utils.ConnectionHandler;

@WebServlet("/sell-auction")
public class SelfAuctionController extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	private Connection connection = null;
	private TemplateEngine templateEngine; // Used for Thymeleaf
       
    public SelfAuctionController() {
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
		int auctionId = Integer.parseInt(request.getParameter("id"));
		
		AuctionDAO dao = new AuctionDAO(connection);
		
		
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.sendError(HttpServletResponse.SC_BAD_REQUEST, "POST is not allowed here");
	}

}
