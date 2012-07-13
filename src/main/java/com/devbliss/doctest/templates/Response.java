package com.devbliss.doctest.templates;

public class Response implements Item {

    public final int responseCode;
    public final String payload;

    public Response(int responseCode, String payload) {
        this.responseCode = responseCode;
        this.payload = payload;
    }
}
