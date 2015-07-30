package com.utils.model;

public class Category {
    private String name;
    private String image;
    private int id;


    public Category(String name,String image,int id){
        this.name= name;
        this.image=image;
        this.id=id;
    }

    public String getName(){
        return this.name;
    }

    public String getImage(){
        return this.image;
    }

    public int getId(){
        return this.id;
    }

}