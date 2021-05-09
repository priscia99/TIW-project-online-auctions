package it.polimi.tiw.project.beans;

import java.io.InputStream;

public class Item extends DBObject {

    private String name;
    private String description;
    private InputStream image;

    public Item(int id, String name, String description, InputStream image) {
    	super(id);
        this.name = name;
        this.description = description;
        this.image = image;
    }
    
    public Item(String name, String description, InputStream image) {
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

    public InputStream getImage() {
        return this.image;
    }

    public void setImageFilename(InputStream image) {
        this.image = image;
    }
}
