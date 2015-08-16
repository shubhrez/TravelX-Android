package com.utils.model;

import java.util.ArrayList;
import java.util.HashMap;

public class Place {

    String name="";
    String image="";
    String location="";
    String short_description="";
    int budget=0;
    String duration="";
    ArrayList<GalleryItem> image_list = new ArrayList<GalleryItem>();
    ArrayList<Description> description_list = new ArrayList<Description>();
//    HashMap<String,String> image_list = new HashMap<String,String>();
//    HashMap<String,String> description_list = new HashMap<String,String>();
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

    public ArrayList<Description> getDescriptionList(){
        return this.description_list;
    }

    public void setDescriptionList(ArrayList<Description> description_list){
        this.description_list = description_list;
    }

    public ArrayList<GalleryItem> getGalleryList(){
        return this.image_list;
    }

    public void setGalleryList(ArrayList<GalleryItem> image_list){
        this.image_list = image_list;
    }

    public int getBudget(){
        return this.budget;
    }

    public void setBudget(int budget){
        this.budget=budget;
    }

    public String getDuration(){
        return this.duration;
    }

    public void setDuration(String duration){
        this.duration=duration;
    }
}
