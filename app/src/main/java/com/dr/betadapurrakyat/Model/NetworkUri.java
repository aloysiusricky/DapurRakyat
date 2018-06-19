package com.dr.betadapurrakyat.Model;

/**
 * Created by ASUS on 2/10/2018.
 */

public class NetworkUri {

    public NetworkUri() {
    }

    private String uri;

    public NetworkUri(String uri) {
        this.uri = uri;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        if (uri==null){
            uri = "https://ecs7.tokopedia.net/img/cache/300/product-1/2016/4/29/361760/361760_5edccebc-0b8d-4d60-93a5-fda60d84caa7.jpg";
        }

        this.uri = uri;

    }
}