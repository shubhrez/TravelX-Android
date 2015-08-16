package com.utils.model;


public class GalleryItem {
    String title="";
    String image="";

    public GalleryItem(String title,String image){
        this.title=title;
        this.image=image;
    }

    public String getTitle(){
        return this.title;
    }

    public String getImage(){
        return this.image;
    }
}
