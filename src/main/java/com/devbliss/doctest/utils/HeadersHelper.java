package com.devbliss.doctest.utils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 
 * Headershelper filters the original request or response headers
 * depending on the headers for the documentation
 * 
 * The original headers will be transformed to lower case in the ApiTester project by ApiTestUtil
 * 
 * @author katharinairrgang, bmary
 * 
 */
public class HeadersHelper {

    public Map<String, String> filter(Map<String, String> originalHeaders,
            List<String> headersToShow) {

        Map<String, String> headers = new HashMap<String, String>();

        for (String header : headersToShow) {
            String lowerCasedHeader = header.toLowerCase();
            if (originalHeaders.containsKey(lowerCasedHeader)) {
                headers.put(lowerCasedHeader, originalHeaders.get(lowerCasedHeader));
            }
        }
        return headers;
    }
}
