package com.devbliss.doctest;

import de.devbliss.apitester.ApiResponse;

/**
 * Wrapper object for the {@link de.devbliss.apitester.ApiResponse}.
 * The goal of this wrapper object is to avoid consumers of this library having a dependency on the
 * ApiTester library.
 * 
 * @author bmary
 * 
 */
public class Response {

    public final int httpStatus;
    public final String payload;

    public Response(int httpStatus, String payload) {
        this.httpStatus = httpStatus;
        this.payload = payload;
    }

    public Response(ApiResponse apiResponse) {
        this.httpStatus = apiResponse.httpStatus;
        this.payload = apiResponse.payload;
    }
}
