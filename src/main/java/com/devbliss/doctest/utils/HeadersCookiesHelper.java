package com.devbliss.doctest.utils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.devbliss.doctest.LogicDocTest;

/**
 * 
 * {@link HeadersCookiesHelper} filters the original headers/cookies
 * depending on the headers/cookies that the user wants to show
 * 
 * The original headers/cookies name will be transformed to lower case.
 * 
 * @author katharinairrgang, bmary
 * 
 */
public class HeadersCookiesHelper {

    public Map<String, String> filter(Map<String, String> originalHeaders,
            List<String> headersToShow) {

        Map<String, String> headers = new HashMap<String, String>();

        if (LogicDocTest.ALL.equals(headersToShow)) {
            headers = originalHeaders;
        }

        for (String header : headersToShow) {
            String lowerCasedHeader = header.toLowerCase();
            if (originalHeaders.containsKey(lowerCasedHeader)) {
                headers.put(lowerCasedHeader, originalHeaders.get(lowerCasedHeader));
            }
        }
        return headers;
    }
}
