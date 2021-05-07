package it.polimi.tiw.project.controllers;

import java.io.IOException;

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

import it.polimi.tiw.project.beans.User;
import it.polimi.tiw.project.dao.UserDAO;
import it.polimi.tiw.project.utils.ConnectionHandler;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Servlet implementation class PerformLogin
 */
@WebServlet("/login")
public class PerformLogin extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private Connection connection = null;
    private TemplateEngine templateEngine;
    
    public PerformLogin() {
        super();
        // TODO Auto-generated constructor stub
    }

    public void init() throws ServletException{
    	// Get a new connection from ConnectionHandler class
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
		
		// Parameters declaration before reading them from the request
		String username = null;
		String password = null;
		
		try {
			username = StringEscapeUtils.escapeJava(request.getParameter("username"));
			password = StringEscapeUtils.escapeJava(request.getParameter("password"));
			// Check if some fields are missing or empty
			if(username==null || username.isEmpty() || password==null || password.isEmpty()) {
				throw new Exception("Missing or empty credentials");
			}
		} catch (Exception e) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing credential value");
			return;
		}
		
		// Perform user authentication using DAO
		UserDAO userDao = new UserDAO(connection);
		User user = null; // User declaration before getting an object from DAO
		try {
			user = userDao.performUserLogin(username, password);
		}catch (SQLException e) {
			//  "Not Possible to check credentials : " + e.getMessage()
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Not Possible to check credentials");
			return;
		}
		
		// Check if user exists
		
		String path;	// Used for re-directing user to proper page (based on authentication response)
		if(user == null) {
			// Username or password incorrect -> return to login page
			ServletContext servletContext = getServletContext();
			final WebContext webContext = new WebContext(request, response, servletContext, request.getLocale());
			webContext.setVariable("loginInfoMsg", "Incorrect username or password. Try again.");
			path = "/index.html";	//Re-direct to login page again
			templateEngine.process(path, webContext, response.getWriter());
		}
		else{
			// User is an actual object -> user authenticated successfully
			request.getSession().setAttribute("user", user);	// Create a new session giving the user object as an attribute
			path = getServletContext().getContextPath() + "/home";	// Re-direct to home page
			response.sendRedirect(path);
		}
	}
	
	public void destroy() {
		// Destroys all objects and close connection with DB
		try {
			ConnectionHandler.closeConnection(connection);
		}catch( Exception e) {
			e.printStackTrace();
		}
	}

}
