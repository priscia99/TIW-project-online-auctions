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
import it.polimi.tiw.project.utils.ErrorHandler;

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
    
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// set request encoding to match the project character encoding (utf-8)
		request.setCharacterEncoding("UTF-8");
	
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
			username = request.getParameter("username");
			password = request.getParameter("password");
			name = request.getParameter("name");
			surname = request.getParameter("surname");
			email = request.getParameter("email");		
			addressTown = request.getParameter("address-town");
			addressStreet = request.getParameter("address-street");
			
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
			e.printStackTrace();
			final WebContext webContext = new WebContext(request, response, getServletContext(), request.getLocale());
			ErrorHandler.displayErrorPage(webContext, response.getWriter(), templateEngine, "Error while creating user, try again.");
			return;
		}
		
		// Create user in DB using UserDAO
		try {
			dao.createUser(username, password, name,  surname, email, addressTown, addressStreet);
		}catch(SQLException e) {
			e.printStackTrace();
			final WebContext webContext = new WebContext(request, response, getServletContext(), request.getLocale());
			ErrorHandler.displayErrorPage(webContext, response.getWriter(), templateEngine, "Error while creating user, try again.");
			return;
		}
		
		// Finally, if everything is correct
		final WebContext webContext = new WebContext(request, response, servletContext, request.getLocale());
		webContext.setVariable("loginInfoMsg", "User created successfully!");
		path = "/index.html";	// Re-direct user to login page after successful signup
		templateEngine.process(path, webContext, response.getWriter());
	}

}
