package it.polimi.tiw.project.beans;

import java.sql.Timestamp;

public class Bid {

    private float price;
    private Timestamp timestamp;
    private int bidderId;
    private int auctionId;

    public Bid(float price, Timestamp timestamp, int bidderId, int auctionId) {
        this.price = price;
        this.timestamp = timestamp;
        this.bidderId = bidderId;
        this.auctionId = auctionId;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public int getBidderId() {
        return bidderId;
    }

    public void setBidderId(int bidderId) {
        this.bidderId = bidderId;
    }

    public int getAuctionId() {
        return auctionId;
    }

    public void setAuctionId(int auctionId) {
        this.auctionId = auctionId;
    }
}
