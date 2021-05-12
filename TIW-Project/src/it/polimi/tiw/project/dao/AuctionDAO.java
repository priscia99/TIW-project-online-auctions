package it.polimi.tiw.project.dao;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Base64;

import it.polimi.tiw.project.beans.Item;
import it.polimi.tiw.project.beans.Auction;
import it.polimi.tiw.project.beans.Bid;

public class AuctionDAO {

	private Connection connection;

	// Create a new AuctionDAO object given the current connection to the DB
	public AuctionDAO(Connection connection) {
		this.connection = connection;
	}

	public ArrayList<Auction> filterByArticleName(String query) throws SQLException {
		String sqlStatement = "SELECT id_item, name, description, image, id_auction, starting_price, minimum_rise, end, open, id_seller "
				+ "FROM auction_item WHERE name LIKE CONCAT( '%',?,'%')";
		try (PreparedStatement statement = connection.prepareStatement(sqlStatement);) {
			statement.setString(1, query);
			try (ResultSet rs = statement.executeQuery();) {
				ArrayList<Auction> toReturn = new ArrayList<>();
				while (rs.next()) {
					Item item = new Item(
							rs.getInt("id_item"), 
							rs.getString("name"), 
							rs.getString("description"),
							Base64.getEncoder().encodeToString(rs.getBytes("image"))
							);
					toReturn.add(new Auction(
							rs.getInt("id_auction"),
							rs.getFloat("starting_price"),
							rs.getFloat("minimum_rise"),
							rs.getTimestamp("end").toLocalDateTime(),
							rs.getBoolean("open"),
							item,
							rs.getInt("id_seller"))
							);
				}
				return toReturn;
			}
		}
	}

	// Create a new item into database
	private int createItem(String name, String description, InputStream image) throws SQLException {
		String query = "INSERT INTO item (name, description, image) VALUES (?, ?, ?);";
		try (PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);) {
			statement.setString(1, name);
			statement.setString(2, description);
			statement.setBlob(3, image);
			statement.executeUpdate();
			ResultSet keys = statement.getGeneratedKeys();
			int id = -1;
			if (keys.next()) {
				id = keys.getInt(1);
			}
			return id;
		}
	}

	// Create a new auction into database
	private int createAuction(int itemId, int sellerId, float minimumRise, float startingPrice, String end,
			boolean open) throws SQLException {
		String query = "INSERT INTO auction(id_item, id_seller, minimum_rise, starting_price, end, open) VALUES (?, ?, ?, ?, STR_TO_DATE(?, '%Y-%m-%dT%T'), ?);";
		try (PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);) {
			statement.setInt(1, itemId);
			statement.setInt(2, sellerId);
			statement.setFloat(3, minimumRise);
			statement.setFloat(4, startingPrice);
			statement.setString(5, end);
			statement.setBoolean(6, open);
			statement.executeUpdate();
			ResultSet keys = statement.getGeneratedKeys();
			int id = -1;
			if (keys.next()) {
				id = keys.getInt(1);
			}
			return id;
		}
	}
	/*
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
								rs.getString("end"),
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
	
*/
	public void createAuctionItem(String name, String description, InputStream image, int sellerId,
			float minimumRise, float startingPrice, String end) throws SQLException {
		this.createAuction(this.createItem(name, description, image), sellerId, minimumRise, startingPrice, end, true);
	}

	// Query list of open auction for a specific username
	public ArrayList<Auction> getUserOpenAuctions(int userId) throws SQLException {
		String query = "SELECT * FROM open_auctions WHERE id_seller = ?;";
		try (PreparedStatement statement = connection.prepareStatement(query);) {
			statement.setInt(1, userId);

			try (ResultSet rs = statement.executeQuery();) {
				ArrayList<Auction> toReturn = new ArrayList<>();
				while (rs.next()) {
					Item item = new Item(rs.getInt("id_item"), rs.getString("name"), rs.getString("description"),
							Base64.getEncoder().encodeToString(rs.getBytes("image")));
					if (rs.getInt("id_max_bid") < 1) {
						toReturn.add(new Auction(
								rs.getInt("id_auction"), 
								rs.getFloat("starting_price"),
								rs.getFloat("minimum_rise"), 
								rs.getTimestamp("end").toLocalDateTime(), 
								rs.getBoolean("open"), item,
								rs.getInt("id_seller")));
					} else {
						Bid bid = new Bid(rs.getInt("id_max_bid"), rs.getFloat("max_price"),
							rs.getTimestamp("max_bid_time"), rs.getInt("id_max_bidder"));
						toReturn.add(new Auction(
								rs.getInt("id_auction"),
								rs.getFloat("starting_price"),
								rs.getFloat("minimum_rise"), 
								rs.getTimestamp("end").toLocalDateTime(),
								rs.getBoolean("open"), 
								item,
								rs.getInt("id_seller"), 
								bid));
					}
				}
				return toReturn;
			}
		}
	}

	// Query list of close auction for a specific username
	public ArrayList<Auction> getUserCloseAuctions(int userId) throws SQLException {
		String query = "SELECT * FROM close_auctions WHERE id_seller = ?;";

		try (PreparedStatement statement = connection.prepareStatement(query);) {
			statement.setInt(1, userId);
			try (ResultSet rs = statement.executeQuery();) {
				ArrayList<Auction> toReturn = new ArrayList<>();
				while (rs.next()) {
					Item item = new Item(rs.getInt("id_item"), rs.getString("name"), rs.getString("description"),
							Base64.getEncoder().encodeToString(rs.getBytes("image")));
					Bid bid = new Bid(rs.getInt("id_max_bid"), rs.getFloat("max_price"),
							rs.getTimestamp("max_bid_time"), rs.getInt("id_max_bidder"));
					toReturn.add(new Auction(
							rs.getInt("id_auction"),
							rs.getFloat("starting_price"),
							rs.getFloat("minimum_rise"),
							rs.getTimestamp("end").toLocalDateTime(),
							rs.getBoolean("open"),
							item,
							rs.getInt("id_seller"),
							bid));
				}
				return toReturn;
			}
		}
	}
}
