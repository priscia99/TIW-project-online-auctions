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
    private long secondsLeft = 0;
    private String timeLeftFormatted = null;
    
    private void calculateCurrentPrice() {
    	Bid maxBid = this.getMaxBid();
    	if (maxBid == null) {
    		this.currentPrice = this.startingPrice;
    	} else {
    		this.currentPrice = maxBid.getPrice();
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
    
    public Bid getMaxBid() {
    	if (this.bids.isEmpty()) {
    		return null;
    	} else {
    		return this.bids.get(this.bids.size()-1);
    	}
    }
    
    public void setBids(ArrayList<Bid> bids) {
    	this.bids = bids;
    	this.calculateCurrentPrice();
    }
    
    public void addBid(Bid bid) {
    	this.bids.add(bid);
    	this.calculateCurrentPrice();
    }
    
    public float getCurrentPrice() {
    	return this.currentPrice;
    }
    
    public Duration getTimeLeft() {
    	return Duration.between(this.endTimestamp, LocalDateTime.now());
    }
    
    public void calculateTimeLeft(LocalDateTime reference) {
    	long secondsLeft = Duration.between(reference, this.endTimestamp).getSeconds();
    	this.secondsLeft = secondsLeft;
        this.updateTimeLeftFormatted();
    }

	public long getSecondsLeft() {
		return secondsLeft;
	}

	public void setSecondsLeft(long secondsLeft) {
		this.secondsLeft = secondsLeft;
        this.updateTimeLeftFormatted();
	}

    private void updateTimeLeftFormatted(){
        int days = (int) Math.floor(this.secondsLeft / 86400);
        int hours = (int) Math.floor((this.secondsLeft % 86400) / 3600);
        int minutes = (int) Math.floor((this.secondsLeft % 3600) / 60);;
        int seconds = (int) Math.floor(this.secondsLeft % 60);
        String message = "";
        if(days>0) message = message.concat(String.format("%dg ", days));
        if(hours>0) message = message.concat(String.format(" %dh ", hours));
        if(minutes>0) message = message.concat(String.format(" %dm ", minutes));
        if(seconds > 0) message = message.concat(String.format(" %ds", seconds));
        if(message.equals("")) message = "Expire date has passed.";
		this.timeLeftFormatted = message;
    }

	public String getTimeLeftFormatted() {
		return timeLeftFormatted;
	}
	
	public boolean isEnded() {
		return this.secondsLeft <= 0;
	}
}
