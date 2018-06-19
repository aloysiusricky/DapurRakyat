package com.dr.betadapurrakyat.Model;


public class Fresh {

    private String uri;
    private String title;
    private String subtitle;
    private String price;
    private String satuan;

    public Fresh() {
    }

    public Fresh(String uri, String title, String subtitle, String price, String satuan) {
        this.uri = uri;
        this.title = title;
        this.subtitle = subtitle;
        this.price = price;
        this.satuan = satuan;
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

    public String getSatuan() {
        return satuan;
    }

    public void setSatuan(String satuan) {
        this.satuan = satuan;
    }
}

