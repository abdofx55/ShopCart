package com.shopcart;

public class Category {
    private String id;
    private String name;
    private String photoUrl;
    private Integer colorFilter;

    public Category() {
    }

    public Category(String name, String photoUrl, Integer colorFilter) {
        this.name = name;
        this.photoUrl = photoUrl;
        this.colorFilter = colorFilter;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public Integer getColorFilter() {
        return colorFilter;
    }

    public void setColorFilter(Integer colorFilter) {
        this.colorFilter = colorFilter;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}

