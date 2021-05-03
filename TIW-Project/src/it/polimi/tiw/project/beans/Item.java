package it.polimi.tiw.project.beans;

public class Item extends DBObject {

    private String name;
    private String description;
    private String imageFilename;

    public Item(String name, String description, String imageFilename) {
        this.name = name;
        this.description = description;
        this.imageFilename = imageFilename;
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

    public String getImageFilename() {
        return imageFilename;
    }

    public void setImageFilename(String imageFilename) {
        this.imageFilename = imageFilename;
    }
}
