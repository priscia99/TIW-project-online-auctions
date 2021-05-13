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

import it.polimi.tiw.project.dao.AuctionDAO;
import it.polimi.tiw.project.utils.ConnectionHandler;

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

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.sendError(HttpServletResponse.SC_BAD_REQUEST, "GET is not allowed here");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		boolean badRequest = false;
		ServletContext servletContext = getServletContext();
		HttpSession session = request.getSession();
		if (session.isNew() || session.getAttribute("user") == null) {
			response.sendRedirect(getServletContext().getContextPath() + "/index.html");
			return;
		}

		// Respond to bad request
		if (badRequest) {
			final WebContext context = new WebContext(request, response, servletContext, request.getLocale());
			context.setVariable("signupInfoMsg", "Missing or empty parameters");
			templateEngine.process("/signup.html", context, response.getWriter());
			return;
		}

		// Update auctionin DB using UserDAO
		try {
			AuctionDAO dao = new AuctionDAO(connection);
			dao.closeAuction(Integer.parseInt(request.getParameter("id")));
		} catch (SQLException e) {
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
					"Internal error while trying to create a new auction: SQL error ");
			e.printStackTrace();
			return;
		}

		// If everything is correct redirect to the sell page
		response.sendRedirect(request.getContextPath() + "/sell");
	}

}
