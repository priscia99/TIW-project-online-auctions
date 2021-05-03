package it.polimi.tiw.project.beans;

public class User extends DBObject{

    private String username;
    private String name;
    private String surname;
    private String addressStreet;
    private String addressTown;

    public User(String username, String name, String surname, String addressStreet, String addressTown) {
        this.username = username;
        this.name = name;
        this.surname = surname;
        this.addressStreet = addressStreet;
        this.addressTown = addressTown;
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

    public String getAddressStreet() {
        return addressStreet;
    }

    public void setAddressStreet(String addressStreet) {
        this.addressStreet = addressStreet;
    }

    public String getAddressTown() {
        return addressTown;
    }

    public void setAddressTown(String addressTown) {
        this.addressTown = addressTown;
    }

    public String getCompleteAddress() {
        return this.addressStreet + ',' + this.addressTown;
    }
}
