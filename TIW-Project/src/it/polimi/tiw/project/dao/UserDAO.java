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
		String query = 	"SELECT id_user, username, name, surname, email, address_street, address_town FROM user "
						+ "WHERE username = ? AND password = sha2(?, 256)";
		try (PreparedStatement statement = connection.prepareStatement(query);) {
			statement.setString(1, username);
			statement.setBytes(2, password.getBytes());
			try (ResultSet result = statement.executeQuery();) {
				if (!result.isBeforeFirst()) // no results, credential check failed
					return null;
				else {
					result.next();
					User user = new User();
					user.setId(result.getInt("id_user"));
					user.setUsername(result.getString("username"));
					user.setName(result.getString("name"));
					user.setSurname(result.getString("surname"));
					user.setEmail(result.getString("email"));
					user.setAddress_street(result.getString("address_street"));
					user.setAddress_town(result.getString("address_town"));
					return user;
				}
			}
		}
	}

	
	// Create a new user into database 
	public void createUser(String username, String password, String name, String surname, String email, String addressTown, String addressStreet)  throws SQLException {
		// String query = "INSERT INTO user (username, password, name, surname, email, address_street, address_town) VALUES (?, sha2(?, 256), ?, ?, ?, ?, ?);";
		String query = "CALL signup(?, ?, ?, ?, ?, ?, ?);";
		try (PreparedStatement statement = connection.prepareStatement(query);) {
			statement.setString(1, username);
			statement.setString(2, password);
			statement.setString(3, name);
			statement.setString(4, surname);
			statement.setString(5, email);
			statement.setString(6, addressTown);
			statement.setString(7, addressStreet);
			statement.executeQuery();
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
	
	public User getUser(int userId) throws SQLException {
		String query = "SELECT * FROM user WHERE id_user = ?;";
		try (PreparedStatement statement = connection.prepareStatement(query);) {
			statement.setInt(1, userId);
			try (ResultSet rs = statement.executeQuery();) {
				User toReturn = new User();
				while (rs.next()) {
					toReturn.setId(userId);
					toReturn.setUsername(rs.getString("username"));
					toReturn.setName(rs.getString("name"));
					toReturn.setSurname(rs.getString("surname"));
					toReturn.setEmail(rs.getString("email"));
					toReturn.setAddress_street(rs.getString("address_street"));
					toReturn.setAddress_town(rs.getString("address_town"));
				}
				return toReturn;
			}
		}
	}
}
