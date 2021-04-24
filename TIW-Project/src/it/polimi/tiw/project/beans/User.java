package it.polimi.tiw.project.beans;

public class User {
	
	private int userId; 		// user's identifier
	private String username; 	// user's username
	private String name; 		// user's name
	private String surname;		// user's surname
	private String address;		// user's home address
	
	// Main constructor
	public User(int uId, String u, String n, String s, String a) {
		this.userId = uId;
		this.username = u;
		this.name = n;
		this.surname = s;
		this.address = a;
	}
	
	// Setters 
	public void setUserId(int uId) {
		this.userId = uId;
	}
	public void setUsername(String u) {
		this.username = u;
	}
	public void setName(String n) {
		this.name = n;
	}
	public void setSurname(String s) {
		this.surname = s;
	}
	public void setAddress(String a) {
		this.address = a;
	}
	
	// Getters
	public int getUserId() { return userId; }
	public String getUsername() { return username; }
	public String getName() { return name; }
	public String getSurname() { return surname; }
	public String getAddress() { return address; }
	
}
