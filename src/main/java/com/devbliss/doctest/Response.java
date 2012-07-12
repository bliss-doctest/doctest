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
public class Response extends ApiResponse {

    public Response(ApiResponse apiResponse) {
        super(apiResponse.httpStatus, apiResponse.payload);
    }

}
