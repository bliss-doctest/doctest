package com.devbliss.doctest.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

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

        List<String> headersToShowLowerCase = listToLowerCase(headersToShow);

        for (Entry<String, String> header : originalHeaders.entrySet()) {
            if (headersToShowLowerCase.contains(header.getKey())) {
                headers.put(header.getKey(), header.getValue());
            }
        }
        return headers;
    }

    private List<String> listToLowerCase(List<String> list) {
        List<String> listToLower = new ArrayList<String>();

        for (String ele : list) {
            listToLower.add(ele.toLowerCase());
        }
        return listToLower;
    }
}
