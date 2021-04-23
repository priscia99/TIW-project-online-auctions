package it.polimi.tiw.project.controllers;

import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringEscapeUtils;

import it.polimi.tiw.project.utils.ConnectionHandler;

import java.sql.Connection;

/**
 * Servlet implementation class PerformLogin
 */
@WebServlet("/PerformLogin")
public class PerformLogin extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private Connection connection = null;
    
    public PerformLogin() {
        super();
        // TODO Auto-generated constructor stub
    }

    public void init() throws ServletException{
    	// Get a new connection from ConnectionHandler class
    	connection = ConnectionHandler.getConnection(getServletContext());
    	ServletContext servletContext = getServletContext();
    }
    
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// Parameters declaration before reading them from the request
		String username = null;
		String password = null;
		
		try {
			username = StringEscapeUtils.escapeJava(request.getParameter("username"));
			password = StringEscapeUtils.escapeJava(request.getParameter("pwd"));
			// Check if some fields are missing or empty
			if(username==null || username.isEmpty() || password==null || password.isEmpty()) {
				throw new Exception("Missing or empty credentials");
			}
		} catch (Exception e) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing credential value");
			return;
		}
	}

}
