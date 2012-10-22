package com.devbliss.doctest.items;

import java.util.Map;

public class RequestDocItem implements DocItem {

    protected final String http;
    protected final String uri;
    protected final JsonDocItem payload;
    protected Boolean isAnUploadRequest = false;
    protected Map<String, String> headers;
    protected Map<String, String> cookies;

    public RequestDocItem(
            String http,
            String uri,
            Map<String, String> headers,
            Map<String, String> cookies) {
        this(http, uri, new JsonDocItem(null), headers, cookies);
    }

    public RequestDocItem(
            String http,
            String uri,
            String payload,
            Map<String, String> headers,
            Map<String, String> cookies) {
        this(http, uri, new JsonDocItem(payload), headers, cookies);
    }

    private RequestDocItem(
            String http,
            String uri,
            JsonDocItem payload,
            Map<String, String> headers,
            Map<String, String> cookies) {
        this.http = http;
        this.uri = uri;
        this.payload = payload;
        this.headers = headers;
        this.cookies = cookies;
    }

    public String getHttp() {
        return http.toUpperCase();
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

    public Map<String, String> getCookies() {
        return this.cookies;
    }
}
