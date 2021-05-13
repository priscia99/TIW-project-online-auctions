package it.polimi.tiw.project.beans;

import java.time.LocalDateTime;

public class User extends DBObject{

    private String username = null;
    private String name = null;
    private String surname = null;
    private String email = null;
    private String address_street = null;
    private String address_town = null;
    private LocalDateTime loginTime;

    public User() {
    	loginTime = LocalDateTime.now();
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
    
    public LocalDateTime getLoginTime() {
    	return loginTime;
    }
}
