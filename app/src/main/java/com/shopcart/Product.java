package com.shopcart;

import java.util.ArrayList;

public class Product {

    private String id;
    private String name;
    private Double price;
    private ArrayList<String> photosUrl;

    public Product() {
    }

    public Product(String name, Double price, ArrayList<String> photosUrl) {
        this.name = name;
        this.price = price;
        this.photosUrl = photosUrl;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public ArrayList<String> getPhotosUrl() {
        return photosUrl;
    }

    public void setPhotosUrl(ArrayList<String> photosUrl) {
        this.photosUrl = photosUrl;
    }
}
