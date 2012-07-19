package com.devbliss.doctest.items;

public class ResponseDocItem implements DocItem {

    private int responseCode;
    private String payload;

    public ResponseDocItem(int responseCode, String payload) {
        this.responseCode = responseCode;
        this.payload = payload;
    }

    public int getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(int responseCode) {
        this.responseCode = responseCode;
    }

    public String getPayload() {
        return payload;
    }

    public void setPayload(String payload) {
        this.payload = payload;
    }

}
