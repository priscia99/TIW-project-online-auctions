package it.polimi.tiw.project.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import it.polimi.tiw.project.beans.*;

public class UserDAO {
	Connection connection;
	
	// Create a new UserDAO object given the current connection to the DB
	public UserDAO(Connection connection) {
		this.connection = connection;
	}
	
	// Return User information after trying to authenticate him
	public User performUserLogin(String username, String password) throws SQLException {
		String query = 	"SELECT id, username, name, surname FROM utente "
						+ "WHERE name= ? AND password = ?";
		try (PreparedStatement statement = connection.prepareStatement(query);) {
			statement.setString(1, username);
			statement.setString(2, password);
			try (ResultSet result = statement.executeQuery();) {
				if (!result.isBeforeFirst()) // no results, credential check failed
					return null;
				else {
					result.next();
					User user = new User(result.getInt("id"),
								result.getString("username"),
								result.getString("name"),
								result.getString("surname"));
					return user;
				}
			}
		}
	}
	
}
