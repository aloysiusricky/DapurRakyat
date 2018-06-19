package com.dr.betadapurrakyat.Model;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * Created by ASUS on 1/28/2018.
 */

public class ProductData {

    private String uri;
    private String title;
    private String subtitle;
    private String price;
    private String stock;
    private String bahan;
    private String bumbu;
    private String videoUri;




    public ProductData() {
    }

    public ProductData(String uri, String title, String subtitle, String price, String stock, String bahan, String bumbu, String videoUri) {
        this.uri = uri;
        this.title = title;
        this.subtitle = subtitle;
        this.price = price;
        this.stock = stock;
        this.bahan = bahan;
        this.bumbu = bumbu;
        this.videoUri = videoUri;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getVideoUri () {
        return videoUri;
    }

    public void setVideoUri(String videoUri) {
        this.videoUri = videoUri;
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

    public String getBahan() {
        return bahan;
    }

    public void setBahan(String bahan) {
        bahan = bahan.replace(",","\n");
        this.bahan = bahan;
    }

    public String getBumbu() {

        return bumbu;
    }

    public void setBumbu(String bumbu) {
        bumbu = bumbu.replace(",","\n");



        this.bumbu = bumbu;
    }


}


