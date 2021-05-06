package it.polimi.tiw.project.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;

import it.polimi.tiw.project.beans.Item;
import it.polimi.tiw.project.beans.User;
import it.polimi.tiw.project.beans.Auction;
import it.polimi.tiw.project.beans.Bid;

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
	
	// Create a new auction into database
	public void createAuction(Auction auction) throws SQLException{
		String query = "INSERT INTO `auction` (`id_item`, `id_seller`, `minimum_rise`, `starting_price`, `end`, 'creation', `open`) "
				+ "VALUES (?, ?, ?, ?, ?, ?, ?);";
		try (PreparedStatement statement = connection.prepareStatement(query);) {
			statement.setInt(1, auction.getItem().getId());
			statement.setInt(2, auction.getSellerId());
			statement.setFloat(3, auction.getMinimumRise());
			statement.setFloat(4, auction.getStartingPrice());
			statement.setTimestamp(5, auction.getEndTimestamp());
			statement.setTimestamp(6, auction.getCreationTimestamp());
			statement.setBoolean(7, auction.isOpen());
			statement.execute();
		}
	}
	
	// Create a new item and its auction into database
	public Auction createItemAuction(Auction auction, Item item) throws SQLException{
		String query = "INSERT INTO item (name, description, image_filename) VALUES (?, ?, ?);";
		try(PreparedStatement statement = connection.prepareStatement(query);) {
			statement.setString(1, item.getName());
			statement.setString(2, item.getDescription());
			statement.setString(3, item.getImageFilename());
			ResultSet generatedKeys = statement.getGeneratedKeys();
			item.setId(generatedKeys.getInt(1));
		}
		query = "INSERT INTO auction(id_item, id_seller, minimum_rise, starting_price, end, creation) VALUES (? , ?, ?, ?, ?, ?);";
		try (PreparedStatement statement = connection.prepareStatement(query)) {
			statement.setInt(1, item.getId());
			statement.setInt(2, auction.getSellerId());
			statement.setFloat(3, auction.getMinimumRise());
			statement.setFloat(4, auction.getStartingPrice());
			statement.setTimestamp(5, auction.getEndTimestamp());
			statement.setTimestamp(6, auction.getCreationTimestamp());
		}
		auction.setItem(item);
		return auction;
	}
	
	// Query list of open auction for a specific username
	public ArrayList<Auction> getUserOpenAuction(User user) throws SQLException {
		String query = "SELECT * FROM open_auctions WHERE id_seller = ?;";
		try (PreparedStatement statement = connection.prepareStatement(query);) {
			statement.setInt(1, user.getId());
			
			try (ResultSet rs = statement.executeQuery();) {
				if (!rs.isBeforeFirst()) // no results, credential check failed
					return null;
				else {
					ArrayList<Auction> toReturn = new ArrayList<>();
					while(rs.next()) {
						Item item = new Item(
							rs.getInt("id_item"), 
							rs.getString("name"), 
							rs.getString("description"), 
							rs.getString("image_filename")
							);
					Bid bid = new Bid(
							rs.getInt("id_max_bid"), 
							rs.getFloat("max_price"), 
							rs.getTimestamp("max_bid_time"), 
							rs.getInt("id_max_bidder")
							);
					toReturn.add(
							new Auction(
								rs.getInt("id_auction"),
								rs.getFloat("starting_price"),
								rs.getFloat("minimum_rise"),
								rs.getTimestamp("end"),
								rs.getTimestamp("creation"),
								rs.getBoolean("open"),
								item,
								rs.getInt("id_seller"),
								bid
								)
							);
					}
					return toReturn;
				}
			}
		}
	}
	
	// Query list of close auction for a specific username
	public ArrayList<Auction> getUserCloseAuction(User user) throws SQLException {
		String query = "SELECT * FROM close_auctions WHERE id_seller = ?;";
		try (PreparedStatement statement = connection.prepareStatement(query);) {
			statement.setInt(1, user.getId());
			
			try (ResultSet rs = statement.executeQuery();) {
				if (!rs.isBeforeFirst()) // no results, credential check failed
					return null;
				else {
					ArrayList<Auction> toReturn = new ArrayList<>();
					while(rs.next()) {
						Item item = new Item(
							rs.getInt("id_item"), 
							rs.getString("name"), 
							rs.getString("description"), 
							rs.getString("image_filename")
							);
					Bid bid = new Bid(
							rs.getInt("id_max_bid"), 
							rs.getFloat("max_price"), 
							rs.getTimestamp("max_bid_time"), 
							rs.getInt("id_max_bidder")
							);
					toReturn.add(
							new Auction(
								rs.getInt("id_auction"),
								rs.getFloat("starting_price"),
								rs.getFloat("minimum_rise"),
								rs.getTimestamp("end"),
								rs.getTimestamp("creation"),
								rs.getBoolean("open"),
								item,
								rs.getInt("id_seller"),
								bid
								)
							);
					}
					return toReturn;
				}
			}
		}
	}
}
