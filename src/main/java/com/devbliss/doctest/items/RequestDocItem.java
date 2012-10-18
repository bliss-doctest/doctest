package com.devbliss.doctest.items;

import java.util.Map;

public class RequestDocItem implements DocItem {

    protected final String http;
    protected final String uri;
    protected final JsonDocItem payload;
    protected Boolean isAnUploadRequest = false;
    protected Map<String, String> headers;

    public RequestDocItem(String http, String uri, Map<String, String> headers) {
        this(http, uri, new JsonDocItem(null), headers);
    }

    public RequestDocItem(String http, String uri, String payload, Map<String, String> headers) {
        this(http, uri, new JsonDocItem(payload), headers);
    }

    private RequestDocItem(String http, String uri, JsonDocItem payload, Map<String, String> headers) {
        this.http = http;
        this.uri = uri;
        this.payload = payload;
        this.headers = headers;
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
}
