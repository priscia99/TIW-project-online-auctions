package it.polimi.tiw.project.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import it.polimi.tiw.project.beans.Item;
import it.polimi.tiw.project.beans.Auction;

public class AuctionDAO {
	
	private Connection connection;
	
	// Create a new AuctionDAO object given the current connection to the DB
	public AuctionDAO(Connection connection) {
		this.connection = connection;
	}
	
	// Create a new item into database
	public ResultSet createItem(Item item) throws SQLException{
		String query = "INSERT INTO `item` (`name`, `description`, `image_filename`) "
				+ "VALUES (?, ?, ?);";
		try (PreparedStatement statement = connection.prepareStatement(query);) {
			statement.setString(1, item.getName());
			statement.setString(2, item.getDescription());
			statement.setString(3, item.getImageFilename());
			ResultSet rs = statement.executeQuery();
			return rs;
		}
	}

	public ResultSet filterByArticleName(String query) throws SQLException {
		String sqlStatement = "SELECT * FROM auction INNER JOIN item WHERE item.name LIKE CONCAT( '%',?,'%')";
		try (PreparedStatement statement = connection.prepareStatement(sqlStatement);){
			statement.setString(1, query);
			ResultSet queryResult = statement.executeQuery();
			return queryResult;
		} 
	}
	
	// Create a new auction into database
	public ResultSet createAuction(Auction auction) throws SQLException{
		String query = "INSERT INTO `auction` (`id_item`, `id_seller`, `minimum_rise`, `starting_price`, `end`, 'creation', `open`) "
				+ "VALUES (?, ?, ?, ?, ?, ?, ?);";
		try (PreparedStatement statement = connection.prepareStatement(query);) {
			statement.setInt(1, auction.getItemId());
			statement.setInt(2, auction.getSellerId());
			statement.setFloat(3, auction.getMinimumRise());
			statement.setFloat(4, auction.getStartingPrice());
			statement.setTimestamp(5, auction.getEndTimestamp());
			statement.setTimestamp(6, auction.getCreationTimestamp());
			statement.setBoolean(7, auction.isOpen());
			ResultSet rs = statement.executeQuery();
			return rs;
		}
	}
	
	// Create a new item and its auction into database
	public void createItemAuction(Item item, Auction auction) throws SQLException{
		ResultSet rs = this.createItem(item);
		auction.setItemId(rs.getInt("id"));
		this.createAuction(auction);
	}
}
