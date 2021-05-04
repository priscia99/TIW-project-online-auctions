package it.polimi.tiw.project.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import it.polimi.tiw.project.beans.*;

public class UserDAO {
	
	private Connection connection;
	
	// Create a new UserDAO object given the current connection to the DB
	public UserDAO(Connection connection) {
		this.connection = connection;
	}
	
	// Return User information after trying to authenticate him
	public User performUserLogin(String username, String password) throws SQLException {
		String query = 	"SELECT id, username, name, surname, address FROM utente "
						+ "WHERE username = ? AND password = ?";
		try (PreparedStatement statement = connection.prepareStatement(query);) {
			statement.setString(1, username);
			statement.setString(2, password);
			try (ResultSet result = statement.executeQuery();) {
				if (!result.isBeforeFirst()) // no results, credential check failed
					return null;
				else {
					result.next();
					User user = new User(
								result.getString("username"),
								result.getString("name"),
								result.getString("surname"),
								result.getString("address_street"),
								result.getString("address_town")
							);
					return user;
				}
			}
		}
	}
	
	// Create a new user into database
	public void createUser(String username, String password, String name, String surname, String address)
	throws SQLException{
		String query = "INSERT into utente (username, password, name, surname, address) VALUES(?, ?, ?, ?, ?)";
		try (PreparedStatement statement = connection.prepareStatement(query);) {
			statement.setString(1, username);
			statement.setString(2, password);
			statement.setString(3, name);
			statement.setString(4, surname);
			statement.setString(5, address);
			statement.executeUpdate();
		}
	}
	
	// Check if another user exists with the same username given by user
	public boolean userAlreadyExists(String username) throws SQLException{
		String query = 	"SELECT id FROM utente WHERE username = ?";
		try (PreparedStatement statement = connection.prepareStatement(query);) {
			statement.setString(1, username);
			try (ResultSet result = statement.executeQuery();) {
				return result.isBeforeFirst();
			}
		}
	}
	
	
	
}
