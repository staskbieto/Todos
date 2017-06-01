package com.example.stask.myapplication;

/**
 * Created by stask on 31/05/17.
 */

public class Item {

    private String title;
    protected int id;
    private boolean done;


    public Item(String title,boolean itsdone, int id) {
        super();
        this.title = title;
        done = itsdone;
        this.id = id;
    }

    public boolean getDone(){
        return done;
    }

    public boolean setDone(boolean d){
        return done = d;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

}