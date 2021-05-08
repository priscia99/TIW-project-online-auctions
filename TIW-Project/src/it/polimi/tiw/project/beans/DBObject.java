package it.polimi.tiw.project.beans;

public abstract class DBObject {

    private int id = 0;
    
    public DBObject(int id) {
    	this.id = id;
    }
    
    public DBObject() {
    	
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
