package it.polimi.tiw.project.beans;

public class Auction {
	
	private int id;					// auction integer identifier
	private String title;			// auction title
	private String description; 	// auction description
	private float startingPrice; 	// auction starting price
	private float minimumRise; 		// auction minimum rise for offers
	private String expirationDatetime;		// auction expiration date-time (formatting IS08601)
	private AuctionStatus status;	// auction status (OPEN > EXPIRED > CLOSE)
	
	// create an Auction object with specified parameters except status, that will be OPEN
	public Auction (int id, String title, String description, float startingPrice, float minimumRise, String expirationDatetime) {
		this.id = id;
		this.title = title;
		this.description = description;
		this.startingPrice = startingPrice;
		this.minimumRise = minimumRise;
		this.expirationDatetime = expirationDatetime;
		this.status = AuctionStatus.OPEN;
	}
	
	// create an Auction object with specified parameters
	public Auction (int id, String title, String description, float startingPrice, float minimumRise, String expirationDatetime, AuctionStatus status) {
		this.id = id;
		this.title = title;
		this.description = description;
		this.startingPrice = startingPrice;
		this.minimumRise = minimumRise;
		this.expirationDatetime = expirationDatetime;
		this.status = status;
	}
	
	public int getId() {
		return this.id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	public String getTitle() {
		return this.title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getDescription() {
		return this.description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	public float getStartingPrice() {
		return this.startingPrice;
	}
	public void setStartingPrice(float startingPrice) {
		this.startingPrice = startingPrice;
	}
	
	public float getMinimumRise() {
		return this.minimumRise;
	}
	public void setMinimumRise(float minimumRise) {
		this.minimumRise = minimumRise;
	}
	
	public String getExpirationDatetime() {
		return this.expirationDatetime;
	}
	public void setExpirationDatetime(String expirationDatetime) {
		this.expirationDatetime = expirationDatetime;
	}
	
	public AuctionStatus getStatus() {
		return this.status;
	}
	public String getStatusString() {
		return this.status.toString();
	}
	public void setStatus(AuctionStatus status) {
		this.status = status;
	}
	public void setStatusFromString(String stringStatus) {
		this.status = AuctionStatus.fromString(stringStatus);
	}
}
