package it.polimi.tiw.project.beans;

import java.sql.Timestamp;

public class Bid extends DBObject{

    private float price;
    private Timestamp timestamp;
    private int bidderId;

    public Bid(int id, float price, Timestamp timestamp, int bidderId) {
        super(id);
    	this.price = price;
        this.timestamp = timestamp;
        this.bidderId = bidderId;
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
}
