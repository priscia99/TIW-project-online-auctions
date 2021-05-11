package it.polimi.tiw.project.beans;

import java.io.InputStream;
import java.util.Base64;

public class Item extends DBObject {

    private String name;
    private String description;
	private String image;

    public Item(int id, String name, String description, String image) {
    	super(id);
        this.name = name;
        this.description = description;
        this.image = image;
    }
    
    public Item(String name, String description, String image) {
        this.name = name;
        this.description = description;
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return this.image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
