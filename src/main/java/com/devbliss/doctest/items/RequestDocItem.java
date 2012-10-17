package com.devbliss.doctest.items;

import java.util.Map;

import de.devbliss.apitester.ApiTest.HTTP_REQUEST;

public class RequestDocItem implements DocItem {

    protected final HTTP_REQUEST http;
    protected final String uri;
    protected final JsonDocItem payload;
    protected Boolean isAnUploadRequest = false;
    protected Map<String, String> headers;

    public RequestDocItem(HTTP_REQUEST http, String uri, Map<String, String> headers) {
        this(http, uri, new JsonDocItem(null), headers);
    }

    public RequestDocItem(HTTP_REQUEST http, String uri, String payload, Map<String, String> headers) {
        this(http, uri, new JsonDocItem(payload), headers);
    }

    private RequestDocItem(
            HTTP_REQUEST http,
            String uri,
            JsonDocItem payload,
            Map<String, String> headers) {
        this.http = http;
        this.uri = uri;
        this.payload = payload;
        this.headers = headers;
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

    public Map<String, String> getHeaders() {
        return this.headers;
    }
}
