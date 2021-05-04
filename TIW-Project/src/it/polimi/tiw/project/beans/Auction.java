package it.polimi.tiw.project.beans;

import java.sql.Timestamp;

public class Auction extends  DBObject {

    private float startingPrice;
    private float minimumRise;
    private Timestamp creationTimestamp;
    private Timestamp endTimestamp;
    private boolean open;
    private int itemId;
    private int sellerId;

    public Auction(float startingPrice, float minimumRise, Timestamp endDateTime, boolean open, int itemId, int sellerId) {
        this.startingPrice = startingPrice;
        this.minimumRise = minimumRise;
        this.endTimestamp = endDateTime;
        this.open = open;
        this.itemId = itemId;
        this.sellerId = sellerId;
    }

    public float getStartingPrice() {
        return startingPrice;
    }

    public void setStartingPrice(float startingPrice) {
        this.startingPrice = startingPrice;
    }

    public float getMinimumRise() {
        return minimumRise;
    }

    public void setMinimumRise(float minimumRise) {
        this.minimumRise = minimumRise;
    }
    
    public Timestamp getCreationTimestamp() {
    	return this.creationTimestamp;
    }
    
    public void setCreaetionTimestamp(Timestamp creationTimestamp) {
    	this.creationTimestamp = creationTimestamp;
    }

    public Timestamp getEndTimestamp() {
        return endTimestamp;
    }

    public void setEndTimestamp(Timestamp endTimestamp) {
        this.endTimestamp = endTimestamp;
    }

    public boolean isOpen() {
        return open;
    }

    public void setOpen(boolean open) {
        this.open = open;
    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public int getSellerId() {
        return sellerId;
    }

    public void setSellerId(int sellerId) {
        this.sellerId = sellerId;
    }
}
