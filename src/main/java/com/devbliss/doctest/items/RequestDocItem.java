package com.devbliss.doctest.items;

import de.devbliss.apitester.ApiTest.HTTP_REQUEST;

public class RequestDocItem implements DocItem {

    public final HTTP_REQUEST http;
    public final String uri;
    public final String payload;

    public RequestDocItem(HTTP_REQUEST http, String uri, String payload) {
        this.http = http;
        this.uri = uri;
        this.payload = payload;
    }

}
