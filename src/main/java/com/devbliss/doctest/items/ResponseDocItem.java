package com.devbliss.doctest.items;

public class ResponseDocItem implements DocItem {

    private int responseCode;
    private JsonDocItem payload;

    public ResponseDocItem(int responseCode, JsonDocItem payload) {
        this.responseCode = responseCode;
        this.payload = payload;
    }

    public ResponseDocItem(int responseCode, String json) {
        this.responseCode = responseCode;
        this.payload = new JsonDocItem(json);
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

}
