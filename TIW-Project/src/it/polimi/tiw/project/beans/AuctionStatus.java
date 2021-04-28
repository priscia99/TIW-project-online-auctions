package it.polimi.tiw.project.beans;

public enum AuctionStatus {
	
	OPEN("OPEN"),
	EXPIRED("EXPIRED"),
	CLOSE("CLOSED");
	
	private String string;
	
	private AuctionStatus(String string) {
		this.string = string;
	}
	
	public String toString() {
		return this.string;
	}
	
	public static AuctionStatus fromString(String string) {
		if (string.equals(OPEN.toString())) {
			return OPEN;
		} else if (string.equals(EXPIRED.toString())) {
			return EXPIRED;
		} else if (string.equals(CLOSE.toString())) {
			return CLOSE;
		} else {
			throw new IllegalArgumentException("Unknown string");
		}
	}
}
