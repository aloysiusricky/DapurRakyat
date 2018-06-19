package com.dr.betadapurrakyat.Model;


public class NewMenu {

    private String uri;
    private String title;
    private String subtitle;
    private String price;
    private String stock;

    public NewMenu() {
    }

    public NewMenu(String uri, String title, String subtitle, String price, String stock) {
        this.uri = uri;
        this.title = title;
        this.subtitle = subtitle;
        this.price = price;
        this.stock = stock;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getStock() {
        return stock;
    }

    public void setStock(String stock) {
        this.stock = stock;
    }
}

