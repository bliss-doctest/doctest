package com.devbliss.doctest.items;

import de.devbliss.apitester.ApiTest.HTTP_REQUEST;

public class RequestDocItem implements DocItem {

    protected final HTTP_REQUEST http;
    protected final String uri;
    protected final JsonDocItem payload;
    protected Boolean isAnUploadRequest = false;

    public RequestDocItem(HTTP_REQUEST http, String uri) {
        this(http, uri, new JsonDocItem(null));
    }

    public RequestDocItem(HTTP_REQUEST http, String uri, String payload) {
        this(http, uri, new JsonDocItem(payload));
    }

    public RequestDocItem(HTTP_REQUEST http, String uri, JsonDocItem payload) {
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

    public JsonDocItem getPayload() {
        return payload;
    }

    public Boolean getIsAnUploadRequest() {
        return isAnUploadRequest;
    }

    public String getItemName() {
        return "request";
    }
}
