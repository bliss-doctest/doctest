package com.devbliss.doctest.items;

import de.devbliss.apitester.ApiTest.HTTP_REQUEST;

public class RequestDocItem implements DocItem {

    private final HTTP_REQUEST http;
    private final String uri;
    private final String payload;

    public RequestDocItem(HTTP_REQUEST http, String uri, String payload) {
        this.http = http;
        this.uri = uri;
        this.payload = payload;
    }

    public HTTP_REQUEST getHttp() {
        return http;
    }

    public String getUri() {
        return uri;
    }

    public String getPayload() {
        return payload;
    }
}
