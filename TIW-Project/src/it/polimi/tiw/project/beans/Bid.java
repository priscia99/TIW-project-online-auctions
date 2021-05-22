package it.polimi.tiw.project.beans;

import java.time.LocalDateTime;

public class Bid extends DBObject{

    private float price = 0;
    private LocalDateTime timestamp = null;
    private int bidderId = 0;
    private String bidderName = "";

    public Bid() {}

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public int getBidderId() {
        return bidderId;
    }

    public void setBidderId(int bidderId) {
        this.bidderId = bidderId;
    }

    public String getBidderName(){
        return bidderName;
    }

    public void setBidderName(String bidderName){
        this.bidderName = bidderName;
    }
}
