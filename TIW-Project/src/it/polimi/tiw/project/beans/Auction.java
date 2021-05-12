package it.polimi.tiw.project.beans;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class Auction extends  DBObject {

    private float startingPrice = 0;
    private float minimumRise = 0;
    private LocalDateTime endTimestamp = null;
    private boolean open = true;
    private Item item = null;
    private int sellerId = 0;
    private ArrayList<Bid> bids = new ArrayList<>();
    private float currentPrice = 0;
    
    private void calculateCurrentPrice() {
    	if (this.bids.size() == 0) {
    		this.currentPrice = this.startingPrice;
    	} else {
    		this.currentPrice = this.bids.get(this.bids.size() - 1).getPrice();
    	}
    }
    
    public Auction() {
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

    public LocalDateTime getEndTimestamp() {
        return endTimestamp;
    }

    public void setEndTimestamp(LocalDateTime endTimestamp) {
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
    
    public Bid getBid(int index) {
    	return this.bids.get(index);
    }
    
    public void setBids(ArrayList<Bid> bids) {
    	this.bids = bids;
    	this.calculateCurrentPrice();
    }
    
    public void addBid(Bid bid) {
    	this.bids.add(bid);
    }
    
    public float getCurrentPrice() {
    	return this.currentPrice;
    }
    
    public Duration getTimeLeft() {
    	return Duration.between(this.endTimestamp, LocalDateTime.now());
    }
}
