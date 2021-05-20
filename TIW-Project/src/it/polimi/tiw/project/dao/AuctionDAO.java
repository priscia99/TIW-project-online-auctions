package it.polimi.tiw.project.dao;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;

import it.polimi.tiw.project.beans.Item;
import it.polimi.tiw.project.utils.TimezoneHelper;
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
					Item item = new Item();
					item.setId(rs.getInt("id_item"));
					item.setName(rs.getString("name"));
					item.setDescription(rs.getString("description"));
					item.setImage(Base64.getEncoder().encodeToString(rs.getBytes("image")));
					Auction auction = new Auction();
					auction.setId(rs.getInt("id_auction"));			
					auction.setStartingPrice(rs.getFloat("starting_price"));
					auction.setMinimumRise(rs.getFloat("minimum_rise"));
					auction.setEndTimestamp(rs.getTimestamp("end").toInstant().atZone(ZoneId.of("Etc/GMT+0")).toLocalDateTime());
					auction.setOpen(rs.getBoolean("open"));
					auction.setItem(item);
					auction.setSellerId(rs.getInt("id_seller"));
					toReturn.add(auction);
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

	public void createAuctionItem(String name, String description, InputStream image, int sellerId,
			float minimumRise, float startingPrice, String end) throws SQLException {
		this.createAuction(this.createItem(name, description, image), sellerId, minimumRise, startingPrice, end, true);
	}
	
	public void addBidToAuction(int auctionId, int bidderId, float price) throws SQLException {
		String query = "INSERT INTO bid(id_auction, id_bidder, price, bid_time) VALUES (?, ?, ?, CURRENT_TIMESTAMP);";
		try(PreparedStatement statement = connection.prepareStatement(query);){
			statement.setInt(1, auctionId);
			statement.setInt(2, bidderId);
			statement.setFloat(3, price);
			statement.executeUpdate();
		}
				
	}
	
	public ArrayList<Auction> getUserWonAuctionsList(int userId, LocalDateTime timeReference) throws SQLException {
		String query = "SELECT id_item, name, image, id_auction, starting_price, end, open, max_price FROM auctions_summary WHERE id_max_bidder = ? AND open = FALSE;";

		try (PreparedStatement statement = connection.prepareStatement(query);) {
			statement.setInt(1, userId);
			try (ResultSet rs = statement.executeQuery();) {
				ArrayList<Auction> toReturn = new ArrayList<>();
				while (rs.next()) {
					Item item = new Item();
					item.setId(rs.getInt("id_item"));
					item.setName(rs.getString("name"));
					item.setImage(Base64.getEncoder().encodeToString(rs.getBytes("image")));
					Bid bid = new Bid();
					bid.setPrice(rs.getFloat("max_price"));
					Auction auction = new Auction();
					auction.setId(rs.getInt("id_auction"));			
					auction.setStartingPrice(rs.getFloat("starting_price"));
					auction.setEndTimestamp(rs.getTimestamp("end").toInstant().atZone(TimezoneHelper.getCustomTimezoneID()).toLocalDateTime());
					auction.setOpen(rs.getBoolean("open"));
					auction.setItem(item);
					auction.addBid(bid);
					auction.calculateTimeLeft(timeReference);
					toReturn.add(auction);
				}
				return toReturn;
			}
		}
	}
	
	// Query lists of open and closed auctions for a specific username
	public Map<String, ArrayList<Auction>> getUserAuctionLists(int userId, LocalDateTime timeReference) throws SQLException {
		String query = "SELECT id_item, name, image, id_auction, starting_price, end, open, id_max_bid, max_price FROM auctions_summary WHERE id_seller = ?;";
		try (PreparedStatement statement = connection.prepareStatement(query);) {
			statement.setInt(1, userId);

			try (ResultSet rs = statement.executeQuery();) {
				ArrayList<Auction> openAuctions = new ArrayList<>();
				ArrayList<Auction> closeAuctions = new ArrayList<>();
				while (rs.next()) {
					Item item = new Item();
					item.setId(rs.getInt("id_item"));
					item.setName(rs.getString("name"));
					item.setImage(Base64.getEncoder().encodeToString(rs.getBytes("image")));
					if (rs.getInt("id_max_bid") < 1) {
						Auction auction = new Auction();
						auction.setId(rs.getInt("id_auction"));			
						auction.setStartingPrice(rs.getFloat("starting_price"));
						auction.setEndTimestamp(rs.getTimestamp("end").toInstant().atZone(TimezoneHelper.getCustomTimezoneID()).toLocalDateTime());
						auction.setOpen(rs.getBoolean("open"));
						auction.setItem(item);
						auction.calculateTimeLeft(timeReference);
						if (auction.isOpen()) openAuctions.add(auction);
						else closeAuctions.add(auction);
					} else {
						Bid bid = new Bid();
						bid.setPrice(rs.getFloat("max_price"));
						Auction auction = new Auction();
						auction.setId(rs.getInt("id_auction"));			
						auction.setStartingPrice(rs.getFloat("starting_price"));
						auction.setEndTimestamp(rs.getTimestamp("end").toInstant().atZone(TimezoneHelper.getCustomTimezoneID()).toLocalDateTime());
						auction.setOpen(rs.getBoolean("open"));
						auction.setItem(item);
						auction.addBid(bid);
						auction.calculateTimeLeft(timeReference);
						if (auction.isOpen()) openAuctions.add(auction);
						else closeAuctions.add(auction);
					}
				}
				Map<String, ArrayList<Auction>> toReturn = new HashMap<>();
				toReturn.put("open", openAuctions);
				toReturn.put("close", closeAuctions);
				return toReturn;
			}
		}
	}

	// Query list of open auction for a specific username
	public ArrayList<Auction> getUserOpenAuctions(int userId, LocalDateTime timeReference) throws SQLException {
		String query = "SELECT id_item, name, image, id_auction, starting_price, end, open, id_max_bid, max_price FROM auctions_summary WHERE id_seller = ? AND open=TRUE;";
		try (PreparedStatement statement = connection.prepareStatement(query);) {
			statement.setInt(1, userId);

			try (ResultSet rs = statement.executeQuery();) {
	
				ArrayList<Auction> toReturn = new ArrayList<>();
				while (rs.next()) {
					Item item = new Item();
					item.setId(rs.getInt("id_item"));
					item.setName(rs.getString("name"));
					item.setImage(Base64.getEncoder().encodeToString(rs.getBytes("image")));
					if (rs.getInt("id_max_bid") < 1) {
						Auction auction = new Auction();
						auction.setId(rs.getInt("id_auction"));			
						auction.setStartingPrice(rs.getFloat("starting_price"));
						auction.setEndTimestamp(rs.getTimestamp("end").toInstant().atZone(TimezoneHelper.getCustomTimezoneID()).toLocalDateTime());
						auction.setOpen(rs.getBoolean("open"));
						auction.setItem(item);
						auction.calculateTimeLeft(timeReference);
						toReturn.add(auction);
					} else {
						Bid bid = new Bid();
						bid.setPrice(rs.getFloat("max_price"));
						Auction auction = new Auction();
						auction.setId(rs.getInt("id_auction"));			
						auction.setStartingPrice(rs.getFloat("starting_price"));
						auction.setEndTimestamp(rs.getTimestamp("end").toInstant().atZone(TimezoneHelper.getCustomTimezoneID()).toLocalDateTime());
						auction.setOpen(rs.getBoolean("open"));
						auction.setItem(item);
						auction.addBid(bid);
						auction.calculateTimeLeft(timeReference);
						toReturn.add(auction);
					}
				}
				return toReturn;
			}
		}
	}

	// Query list of close auction for a specific username
	public ArrayList<Auction> getUserCloseAuctions(int userId, LocalDateTime timeReference) throws SQLException {
		String query = "SELECT id_item, name, image, id_auction, starting_price, end, open, max_price FROM auctions_summary WHERE id_seller = ? AND open=FALSE;";

		try (PreparedStatement statement = connection.prepareStatement(query);) {
			statement.setInt(1, userId);
			try (ResultSet rs = statement.executeQuery();) {
				ArrayList<Auction> toReturn = new ArrayList<>();
				while (rs.next()) {
					Item item = new Item();
					item.setId(rs.getInt("id_item"));
					item.setName(rs.getString("name"));
					item.setImage(Base64.getEncoder().encodeToString(rs.getBytes("image")));
					Bid bid = new Bid();
					bid.setPrice(rs.getFloat("max_price"));
					Auction auction = new Auction();
					auction.setId(rs.getInt("id_auction"));			
					auction.setStartingPrice(rs.getFloat("starting_price"));
					auction.setEndTimestamp(rs.getTimestamp("end").toInstant().atZone(TimezoneHelper.getCustomTimezoneID()).toLocalDateTime());
					auction.setOpen(rs.getBoolean("open"));
					auction.setItem(item);
					auction.addBid(bid);
					auction.calculateTimeLeft(timeReference);
					toReturn.add(auction);
				}
				return toReturn;
			}
		}
	}
	
	public Auction getAuctionDetails(int auctionId, LocalDateTime timeReference) throws SQLException {
		String query = "SELECT * FROM auctions_details WHERE id_auction = ?";
		
		try (PreparedStatement statement = connection.prepareStatement(query);) {
			statement.setInt(1,  auctionId);
			try (ResultSet rs = statement.executeQuery();) {				
				Auction auction = new Auction();
				if(rs.next()) {
					Item item = new Item();
					item.setId(rs.getInt("id_item"));
					item.setName(rs.getString("name"));
					item.setDescription(rs.getString("description"));
					item.setImage(Base64.getEncoder().encodeToString(rs.getBytes("image")));
					auction.setId(rs.getInt("id_auction"));			
					auction.setStartingPrice(rs.getFloat("starting_price"));
					auction.setMinimumRise(rs.getFloat("minimum_rise"));
					auction.setEndTimestamp(rs.getTimestamp("end").toInstant().atZone(TimezoneHelper.getCustomTimezoneID()).toLocalDateTime());
					auction.setOpen(rs.getBoolean("open"));
					auction.calculateTimeLeft(timeReference);
					auction.setItem(item);
					auction.setSellerId(rs.getInt("id_seller"));
					// parse bids if exist
					if (rs.getString("id_bid") != null) {
						ArrayList<Bid> bids = new ArrayList<>();
						// parse first bid
						Bid bid = new Bid();
						bid.setId(rs.getInt("id_bid"));
						bid.setBidderId(rs.getInt("id_bidder"));
						bid.setPrice(rs.getFloat("price"));
						bid.setTimestamp(rs.getTimestamp("bid_time").toInstant().atZone(TimezoneHelper.getCustomTimezoneID()).toLocalDateTime());
						bids.add(bid);
						// parse next bids
						while (rs.next()) {
							bid = new Bid();
							bid.setId(rs.getInt("id_bid"));
							bid.setBidderId(rs.getInt("id_bidder"));
							bid.setPrice(rs.getFloat("price"));
							bid.setTimestamp(rs.getTimestamp("bid_time").toInstant().atZone(TimezoneHelper.getCustomTimezoneID()).toLocalDateTime());
							bids.add(bid);
						}
						auction.setBids(bids);
					}
				}
				return auction;
			}
		}
	}
	
	public void closeAuction(int auctionId) throws SQLException {
		String query = "UPDATE auction SET open = FALSE WHERE id_auction = ?;";
		
		try (PreparedStatement statement = connection.prepareStatement(query);) {
			statement.setInt(1, auctionId);
			statement.executeUpdate();
		}
	}
}
