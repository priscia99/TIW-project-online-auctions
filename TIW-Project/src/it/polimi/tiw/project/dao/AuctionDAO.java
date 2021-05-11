package it.polimi.tiw.project.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
	public Item createItem(Item item) throws SQLException{
		String query = "INSERT INTO `item` (`name`, `description`, `image_filename`) VALUES (?, ?, ?);";
		try (PreparedStatement statement = connection.prepareStatement(query);) {
			statement.setString(1, item.getName());
			statement.setString(2, item.getDescription());
			statement.setString(3, item.getImageFilename());
			statement.execute();
			
			item.setId(statement.getGeneratedKeys().getInt(1));
			return item;
		}
	}

	public ArrayList<Auction> filterByArticleName(String query) throws SQLException {
		String sqlStatement = "SELECT id_item, name, description, image_filename, id_auction, starting_price, minimum_rise, DATE_FORMAT(end, '%Y-%m-%dT%T') as end, open, id_seller "
				+ "FROM auction_item WHERE name LIKE CONCAT( '%',?,'%')";
		try (PreparedStatement statement = connection.prepareStatement(sqlStatement);){
			statement.setString(1, query);
			try (ResultSet rs = statement.executeQuery();) {
				ArrayList<Auction> toReturn = new ArrayList<>();
				while(rs.next()) {
					Item item = new Item(
						rs.getInt("id_item"), 
						rs.getString("name"), 
						rs.getString("description"), 
						rs.getString("image_filename")
						);
				toReturn.add(
						new Auction(
							rs.getInt("id_auction"),
							rs.getFloat("starting_price"),
							rs.getFloat("minimum_rise"),
							rs.getString("end"),
							rs.getBoolean("open"),
							item,
							rs.getInt("id_seller")
							)
						);
				}
				return toReturn;
			}
		} 
	}
	
	// Create a new auction into database
	public void createAuction(Auction auction) throws SQLException{
		String query = "INSERT INTO `auction` (`id_item`, `id_seller`, `minimum_rise`, `starting_price`, STR_TO_DATE(?, '%Y-%m-%dT%T'), `open`) "
				+ "VALUES (?, ?, ?, ?, ?, ?, ?);";
		try (PreparedStatement statement = connection.prepareStatement(query);) {
			statement.setInt(1, auction.getItem().getId());
			statement.setInt(2, auction.getSellerId());
			statement.setFloat(3, auction.getMinimumRise());
			statement.setFloat(4, auction.getStartingPrice());
			statement.setString(5, auction.getEndTimestamp());
			statement.setBoolean(7, auction.isOpen());
			statement.execute();
		}
	}
	
	// Create a new item and its auction into database
	public Auction createItemAuction(Auction auction, Item item) throws SQLException{
		String query = "INSERT INTO item (name, description, image_filename) VALUES (?, ?, ?);";
		try(PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);) {
			statement.setString(1, item.getName());
			statement.setString(2, item.getDescription());
			statement.setString(3, item.getImageFilename());
			statement.executeUpdate();
			ResultSet generatedKeys = statement.getGeneratedKeys();
			while (generatedKeys.next())
			{
				item.setId(generatedKeys.getInt(1));
			}
			
		}
		query = "INSERT INTO auction(id_item, id_seller, minimum_rise, starting_price, end) VALUES (?, ?, ?, ?, STR_TO_DATE(?, '%Y-%m-%dT%T'));";
		try (PreparedStatement statement = connection.prepareStatement(query)) {
			statement.setInt(1, item.getId());
			statement.setInt(2, auction.getSellerId());
			statement.setFloat(3, auction.getMinimumRise());
			statement.setFloat(4, auction.getStartingPrice());
			statement.setString(5, auction.getEndTimestamp());
			statement.executeUpdate();
		}
		auction.setItem(item);
		return auction;
	}
	
	public Auction getAuctionDetail(String auctionId) throws SQLException {
		String query = "SELECT * FROM auction_open_details WHERE id_auction = ?";
		try(PreparedStatement statement = connection.prepareStatement(query);){
			statement.setString(1, auctionId);
			try (ResultSet rs = statement.executeQuery();) {
				Auction toReturn = null;
				ArrayList<Bid> bids = new ArrayList<Bid>();
				while(rs.next()) {
					Item item = new Item(
						rs.getInt("id_item"), 
						rs.getString("name"), 
						rs.getString("description"), 
						rs.getString("image_filename")
						);
					if(toReturn == null) {
						toReturn = new Auction(rs.getInt("id_auction"),
								rs.getFloat("starting_price"),
								rs.getFloat("minimum_rise"),
								rs.getTimestamp("end"),
								rs.getTimestamp("creation"),
								rs.getBoolean("open"),
								item,
								rs.getInt("id_seller")
								);
					}
					bids.add(new Bid(
						rs.getInt("id_max_bid"), 
						rs.getFloat("max_price"), 
						rs.getTimestamp("max_bid_time"), 
						rs.getInt("id_max_bidder")
						));
				}
				toReturn.setBids(bids);
				return toReturn;
			}
		}
		
	}
	
	// Query list of open auction for a specific username
	public ArrayList<Auction> getUserOpenAuctions(User user) throws SQLException {
		String query = "SELECT * FROM open_auctions WHERE id_seller = ?;";
		try (PreparedStatement statement = connection.prepareStatement(query);) {
			statement.setInt(1, user.getId());
			
			try (ResultSet rs = statement.executeQuery();) {
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
							rs.getString("end"),
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
	
	// Query list of close auction for a specific username
	public ArrayList<Auction> getUserCloseAuctions(User user) throws SQLException {
		String query = "SELECT * FROM close_auctions WHERE id_seller = ?;";
		
		try (PreparedStatement statement = connection.prepareStatement(query);) {
			statement.setInt(1, user.getId());
			
			try (ResultSet rs = statement.executeQuery();) {
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
							rs.getString("end"),
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
