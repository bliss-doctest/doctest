package com.devbliss.doctest.templates;

import de.devbliss.apitester.ApiTest.HTTP_REQUEST;

public class Request implements Item {

    public final HTTP_REQUEST http;
    public final String uri;
    public final String payload;

    public Request(HTTP_REQUEST http, String uri, String payload) {
        this.http = http;
        this.uri = uri;
        this.payload = payload;
    }

}
