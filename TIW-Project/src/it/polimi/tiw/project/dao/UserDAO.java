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
		String query = 	"SELECT id_user, username, name, surname FROM user "
						+ "WHERE username = ? AND password = ?";
		try (PreparedStatement statement = connection.prepareStatement(query);) {
			statement.setString(1, username);
			statement.setBytes(2, password.getBytes());
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
	public void createUser(String username, String password, String name, String surname, String email, String addressTown, String addressStreet)
	throws SQLException{
		String query = "INSERT into user (username, password, name, surname, email, address_town, address_street) VALUES(?, ?, ?, ?, ?, ?, ?)";
		try (PreparedStatement statement = connection.prepareStatement(query);) {
			statement.setString(1, username);
			statement.setBytes(2, password.getBytes());
			statement.setString(3, name);
			statement.setString(4, surname);
			statement.setString(5, email);
			statement.setString(6, addressTown);
			statement.setString(7, addressStreet);
			statement.executeUpdate();
		}
	}
	
	// Check if another user exists with the same username given by user
	public boolean userAlreadyExists(String username) throws SQLException{
		String query = 	"SELECT id_user FROM user WHERE username = ?";
		try (PreparedStatement statement = connection.prepareStatement(query);) {
			statement.setString(1, username);
			try (ResultSet result = statement.executeQuery();) {
				return result.isBeforeFirst();
			}
		}
	}
	
	
	
}
