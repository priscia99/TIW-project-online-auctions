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
					Item item = new Item();
					item.setId(rs.getInt("id_item"));
					item.setName(rs.getString("name"));
					item.setDescription(rs.getString("description"));
					item.setImage(Base64.getEncoder().encodeToString(rs.getBytes("image")));
					Auction auction = new Auction();
					auction.setId(rs.getInt("id_auction"));			
					auction.setStartingPrice(rs.getFloat("starting_price"));
					auction.setMinimumRise(rs.getFloat("minimum_rise"));
					auction.setEndTimestamp(rs.getTimestamp("end").toLocalDateTime());
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
	
	public Auction getAuctionDetail(String auctionId) throws SQLException {
		String query = "SELECT * FROM auction_open_details WHERE id_auction = ?";
		try(PreparedStatement statement = connection.prepareStatement(query);){
			statement.setString(1, auctionId);
			try (ResultSet rs = statement.executeQuery();) {
				Auction toReturn = null;
				ArrayList<Bid> bids = new ArrayList<Bid>();
				while(rs.next()) {
					Item item = new Item();
					item.setId(rs.getInt("id_item"));
					item.setName(rs.getString("name"));
					item.setDescription(rs.getString("description"));
					item.setImage(Base64.getEncoder().encodeToString(rs.getBytes("image")));
					if(toReturn == null) {
						toReturn = new Auction();
						toReturn.setId(rs.getInt("id_auction"));			
						toReturn.setStartingPrice(rs.getFloat("starting_price"));
						toReturn.setMinimumRise(rs.getFloat("minimum_rise"));
						toReturn.setEndTimestamp(rs.getTimestamp("end").toLocalDateTime());
						toReturn.setOpen(rs.getBoolean("open"));
						toReturn.setItem(item);
						toReturn.setSellerId(rs.getInt("id_seller"));
					}
					Bid bid = new Bid();
					bid.setId(rs.getInt("id_max_bid"));
					bid.setPrice(rs.getFloat("max_price"));
					bid.setTimestamp(rs.getTimestamp("max_bid_time").toLocalDateTime());
					bid.setBidderId(rs.getInt("id_max_bidder"));
					bids.add(bid);
				}
				toReturn.setBids(bids);
				return toReturn;
			}
		}
		
	}
	

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
					Item item = new Item();
					item.setId(rs.getInt("id_item"));
					item.setName(rs.getString("name"));
					item.setDescription(rs.getString("description"));
					item.setImage(Base64.getEncoder().encodeToString(rs.getBytes("image")));
					if (rs.getInt("id_max_bid") < 1) {
						Auction auction = new Auction();
						auction.setId(rs.getInt("id_auction"));			
						auction.setStartingPrice(rs.getFloat("starting_price"));
						auction.setMinimumRise(rs.getFloat("minimum_rise"));
						auction.setEndTimestamp(rs.getTimestamp("end").toLocalDateTime());
						auction.setOpen(rs.getBoolean("open"));
						auction.setItem(item);
						auction.setSellerId(rs.getInt("id_seller"));
						toReturn.add(auction);
					} else {
						Bid bid = new Bid();
						bid.setId(rs.getInt("id_max_bid"));
						bid.setPrice(rs.getFloat("max_price"));
						bid.setTimestamp(rs.getTimestamp("max_bid_time").toLocalDateTime());
						bid.setBidderId(rs.getInt("id_max_bidder"));
						Auction auction = new Auction();
						auction.setId(rs.getInt("id_auction"));			
						auction.setStartingPrice(rs.getFloat("starting_price"));
						auction.setMinimumRise(rs.getFloat("minimum_rise"));
						auction.setEndTimestamp(rs.getTimestamp("end").toLocalDateTime());
						auction.setOpen(rs.getBoolean("open"));
						auction.setItem(item);
						auction.setSellerId(rs.getInt("id_seller"));
						auction.addBid(bid);
						toReturn.add(auction);
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
					Item item = new Item();
					item.setId(rs.getInt("id_item"));
					item.setName(rs.getString("name"));
					item.setDescription(rs.getString("description"));
					item.setImage(Base64.getEncoder().encodeToString(rs.getBytes("image")));
					Bid bid = new Bid();
					bid.setId(rs.getInt("id_max_bid"));
					bid.setPrice(rs.getFloat("max_price"));
					bid.setTimestamp(rs.getTimestamp("max_bid_time").toLocalDateTime());
					bid.setBidderId(rs.getInt("id_max_bidder"));
					Auction auction = new Auction();
					auction.setId(rs.getInt("id_auction"));			
					auction.setStartingPrice(rs.getFloat("starting_price"));
					auction.setMinimumRise(rs.getFloat("minimum_rise"));
					auction.setEndTimestamp(rs.getTimestamp("end").toLocalDateTime());
					auction.setOpen(rs.getBoolean("open"));
					auction.setItem(item);
					auction.setSellerId(rs.getInt("id_seller"));
					auction.addBid(bid);
					toReturn.add(auction);
				}
				return toReturn;
			}
		}
	}
}
