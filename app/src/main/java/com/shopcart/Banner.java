package com.shopcart;

public class Banner {
    private String header;
    private String body;
    private String photoUrl;

    public Banner() {
    }

    public Banner(String header, String body, String photoUrl) {
        this.header = header;
        this.body = body;
        this.photoUrl = photoUrl;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }
}
