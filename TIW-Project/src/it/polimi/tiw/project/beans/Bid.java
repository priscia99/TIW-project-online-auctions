package it.polimi.tiw.project.beans;

import java.time.LocalDateTime;

public class Bid extends DBObject{

    private float price = 0;
    private LocalDateTime timestamp = null;
    private int bidderId = 0;

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
}
