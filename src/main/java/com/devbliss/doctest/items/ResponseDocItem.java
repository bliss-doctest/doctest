package com.devbliss.doctest.items;

import java.util.List;

import com.devbliss.doctest.Response;



public class ResponseDocItem implements DocItem {

    private int responseCode;
    private JsonDocItem payload;
    private List<String> headers;

    public ResponseDocItem(Response response, List<String> headersToShow) {
        this.responseCode = response.httpStatus;
        this.payload = new JsonDocItem(response.payload);
        this.headers = headersToShow;
    }

    public int getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(int responseCode) {
        this.responseCode = responseCode;
    }

    public JsonDocItem getPayload() {
        return payload;
    }

    public void setPayload(JsonDocItem payload) {
        this.payload = payload;
    }

    public String getItemName() {
        return "response";
    }

    public List<String> getHeaders() {
        return this.headers;
    }

    public void setHeaders(List<String> headers) {
        this.headers = headers;
    }
}
