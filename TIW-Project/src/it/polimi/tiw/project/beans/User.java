package it.polimi.tiw.project.beans;

public class User extends DBObject{

    private String username;
    private String name;
    private String surname;
    private String email;
    private String address_street;
    private String address_town;

    public User(int id, String username, String name, String surname, String email, String address_street, String address_town) {
    	super(id);
        this.username = username;
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.address_street = address_street;
        this.address_town = address_town;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress_street() {
        return address_street;
    }

    public void setAddress_street(String address_street) {
        this.address_street = address_street;
    }

    public String getAddress_town() {
        return address_town;
    }

    public void setAddress_town(String address_town) {
        this.address_town = address_town;
    }
}
