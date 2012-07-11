package com.devbliss.doctest;

/**
 * Wrapper object for the {@link de.devbliss.apitester.ApiResponse}.
 * The goal of this wrapper object is to avoid consumers of this library having a dependency on the
 * ApiTester library.
 * 
 * @author bmary
 * 
 */
public class Response extends de.devbliss.apitester.ApiResponse {

    public Response(int httpStatus, String payload) {
        super(httpStatus, payload);
    }

}
