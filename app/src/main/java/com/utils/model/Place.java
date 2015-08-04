package com.utils.model;

public class Place {

    String name="";
    String image="";
    String location="";
    String short_description="";
    int id;

    public Place(int id,String name,String image,String location,String short_description){
        this.id=id;
        this.name=name;
        this.image=image;
        this.location=location;
        this.short_description=short_description;
    }

    public int getId(){
        return this.id;
    }

    public String getName(){
        return this.name;
    }

    public String getImage(){
        return this.image;
    }

    public String getLocation(){
        return this.location;
    }

    public String getShort_description(){
        return this.short_description;
    }
}
