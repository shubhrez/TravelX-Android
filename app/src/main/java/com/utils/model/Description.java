package com.utils.model;


public class Description {
    String title="";
    String text="";

    public Description(String title,String text){
        this.title=title;
        this.text=text;
    }

    public String getTitle(){
        return this.title;
    }

    public String getText(){
        return this.text;
    }
}
