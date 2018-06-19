package com.dr.betadapurrakyat.Model;


public class NewTools {

    private String uri;
    private String title;
    private String subtitle;
    private String harga;
    private String deskripsi;

    public NewTools() {
    }

    public NewTools(String uri, String title, String subtitle, String harga, String deskripsi) {
        this.uri = uri;
        this.title = title;
        this.subtitle = subtitle;
        this.harga = harga;
        this.deskripsi = deskripsi;
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

    public String getHarga() {
        return harga;
    }

    public void setHarga(String harga) {
        this.harga = harga;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }
}

