package it.polimi.tiw.project.beans;

import java.sql.Timestamp;
import java.util.ArrayList;

public class Auction extends  DBObject {

    private float startingPrice;
    private float minimumRise;
    private String endTimestamp;
    private boolean open = true;
    private Item item;
    private int sellerId;
    private ArrayList<Bid> bids = new ArrayList<>();
    private float currentPrice;
    
    private void calculateCurrentPrice() {
    	if (this.bids.size() == 0) {
    		this.currentPrice = this.startingPrice;
    	} else {
    		this.currentPrice = this.bids.get(this.bids.size() - 1).getPrice();
    	}
    }

    public Auction(int id, float startingPrice, float minimumRise, String endTimestamp, boolean open, Item item, int sellerId, ArrayList<Bid> bids) {
    	super(id);
        this.startingPrice = startingPrice;
        this.minimumRise = minimumRise;
        this.endTimestamp = endTimestamp;
        this.open = open;
        this.item = item;
        this.sellerId = sellerId;
        this.bids = bids;
        this.calculateCurrentPrice();
    }
    
    public Auction(float startingPrice, float minimumRise, String endTimestamp, Item item, int sellerId) {
        this.startingPrice = startingPrice;
        this.minimumRise = minimumRise;
        this.endTimestamp = endTimestamp;
        this.item = item;
        this.sellerId = sellerId;
        this.calculateCurrentPrice();
    }
    
    public Auction(int id, float startingPrice, float minimumRise, String endTimestamp, boolean open, Item item, int sellerId) {
    	super(id);
        this.startingPrice = startingPrice;
        this.minimumRise = minimumRise;
        this.endTimestamp = endTimestamp;
        this.item = item;
        this.sellerId = sellerId;
        this.calculateCurrentPrice();
    }
    
    public Auction(int id, float startingPrice, float minimumRise, String endTimestamp, boolean open, Item item, int sellerId, Bid bid) {
    	super(id);
        this.startingPrice = startingPrice;
        this.minimumRise = minimumRise;
        this.endTimestamp = endTimestamp;
        this.open = open;
        this.item = item;
        this.sellerId = sellerId;
        this.bids.add(bid);
        this.calculateCurrentPrice();
    }

    public float getStartingPrice() {
        return startingPrice;
    }

    public void setStartingPrice(float startingPrice) {
        this.startingPrice = startingPrice;
        this.calculateCurrentPrice();
    }

    public float getMinimumRise() {
        return minimumRise;
    }

    public void setMinimumRise(float minimumRise) {
        this.minimumRise = minimumRise;
    }

    public String getEndTimestamp() {
        return endTimestamp;
    }

    public void setEndTimestamp(String endTimestamp) {
        this.endTimestamp = endTimestamp;
    }

    public boolean isOpen() {
        return open;
    }

    public void setOpen(boolean open) {
        this.open = open;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public int getSellerId() {
        return sellerId;
    }

    public void setSellerId(int sellerId) {
        this.sellerId = sellerId;
    }
    
    public ArrayList<Bid> getBids() {
    	return this.bids;
    }
    
    public void setBids(ArrayList<Bid> bids) {
    	this.bids = bids;
    	this.calculateCurrentPrice();
    }
    
    public float getCurrentPrice() {
    	return this.currentPrice;
    }
}
