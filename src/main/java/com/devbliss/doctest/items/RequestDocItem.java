package com.devbliss.doctest.items;

import java.util.HashMap;
import java.util.Map;

import de.devbliss.apitester.ApiTest.HTTP_REQUEST;

public class RequestDocItem implements DocItem {

    protected final HTTP_REQUEST http;
    protected final String uri;
    protected final JsonDocItem payload;
    protected Boolean isAnUploadRequest = false;
    protected Map<String, String> headers;

    public RequestDocItem(HTTP_REQUEST http, String uri, Map<String, String> headers) {
        this(http, uri, new JsonDocItem(null));
        this.headers = headers;
    }

    public RequestDocItem(HTTP_REQUEST http, String uri, String payload) {
        this(http, uri, new JsonDocItem(payload));
    }

    public RequestDocItem(HTTP_REQUEST http, String uri, JsonDocItem payload) {
        this.http = http;
        this.uri = uri;
        this.payload = payload;
        this.headers = new HashMap<String, String>();
        // headers.put("header1", "value1");
        // headers.put("header2", "value2");
        // headers.put("header3", "value3");
        // headers.put("header4", "value4");
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
