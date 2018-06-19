package com.dr.betadapurrakyat.Model;


public class FreshUtils {

    private String uri;
    private String title;
    private String subtitle;


    public FreshUtils() {
    }

    public FreshUtils(String uri, String title, String subtitle) {
        this.uri = uri;
        this.title = title;
        this.subtitle = subtitle;
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
}

