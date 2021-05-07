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

import org.apache.commons.lang.StringEscapeUtils;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;

import it.polimi.tiw.project.dao.UserDAO;
import it.polimi.tiw.project.utils.ConnectionHandler;

/**
 * Servlet implementation class PerformSignup
 */
@WebServlet("/signup")
public class PerformSignup extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Connection connection = null;
	private TemplateEngine templateEngine;	// Used for Thymeleaf
	
    public PerformSignup() {
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
		
		// Declaration of parameters given by user
		String username = null;
		String password = null;
		String name = null;
		String email = null;
		String surname = null;
		String addressTown = null;
		String addressStreet = null;

		try {
			// Parsing parameters from user request
			username = StringEscapeUtils.escapeJava(request.getParameter("username"));
			password = StringEscapeUtils.escapeJava(request.getParameter("password"));
			name = StringEscapeUtils.escapeJava(request.getParameter("name"));
			surname = StringEscapeUtils.escapeJava(request.getParameter("surname"));
			email = StringEscapeUtils.escapeJava(request.getParameter("email"));		
			addressTown = StringEscapeUtils.escapeJava(request.getParameter("address-town"));
			addressStreet = StringEscapeUtils.escapeJava(request.getParameter("address-street"));
			
			badRequest = username.isEmpty() || password.isEmpty() || name.isEmpty() || surname.isEmpty() || email.isEmpty() || addressTown.isEmpty() || addressStreet.isEmpty();			
		}
		catch(Exception e){
			badRequest = true;
			e.printStackTrace();
		}
		
		// If the request is bad, then re-direct to signup page and show an error message and return
		if(badRequest) {
			final WebContext webContext = new WebContext(request, response, servletContext, request.getLocale());
			webContext.setVariable("signupInfoMsg", "Missing or empty parameters");
			path = "/signup.html";	// If it is a bad request, re-direct to sign up page and show an error
			templateEngine.process(path, webContext, response.getWriter());
			return;
		}
		
		UserDAO dao = new UserDAO(connection);
		
		// Check if the given username already exists using DAO
		try {
			if(dao.userAlreadyExists(username)) {
				// Another user exists with the same username -> redirect to signup page
				final WebContext webContext = new WebContext(request, response, servletContext, request.getLocale());
				webContext.setVariable("signupInfoMsg", "This username already exists!");
				path = "/signup.html";	// If the username already exists, re-direct to sign up page and show an error
				templateEngine.process(path, webContext, response.getWriter());
				return;
			}
		}catch(SQLException e) {
			// for further details : "Internal error while trying to create a new user: " + e.getMessage()
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Internal error while trying to create a new user");
		}
		
		// Create user in DB using UserDAO
		try {
			dao.createUser(username, password, name,  surname, email, addressTown, addressStreet);
		}catch(SQLException e) {
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Internal error while trying to create a new user");
			return;
		}
		
		// Finally, if everything is correct
		final WebContext webContext = new WebContext(request, response, servletContext, request.getLocale());
		webContext.setVariable("loginInfoMsg", "User created successfully!");
		path = "/index.html";	// Re-direct user to login page after successful signup
		templateEngine.process(path, webContext, response.getWriter());
	}

}
