package com.devbliss.doctest.items;

import java.util.Map;

import de.devbliss.apitester.ApiResponse;


public class ResponseDocItem implements DocItem {

    private int responseCode;
    private JsonDocItem payload;
    private Map<String, String> headers;

    public ResponseDocItem(ApiResponse response, Map<String, String> headers) {
        this.responseCode = response.httpStatus;
        this.payload = new JsonDocItem(response.payload);
        this.headers = headers;
    }

    public int getResponseCode() {
        return responseCode;
    }

    public JsonDocItem getPayload() {
        return payload;
    }

    public String getItemName() {
        return "response";
    }

    public Map<String, String> getHeaders() {
        return this.headers;
    }
}
