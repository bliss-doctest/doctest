package com.devbliss.doctest.items;

public class ResponseDocItem implements DocItem {

    public final int responseCode;
    public final String payload;

    public ResponseDocItem(int responseCode, String payload) {
        this.responseCode = responseCode;
        this.payload = payload;
    }
}
