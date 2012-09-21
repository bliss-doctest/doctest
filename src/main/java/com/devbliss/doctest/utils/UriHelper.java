package com.devbliss.doctest.utils;

import java.net.URI;

/**
 * Helper class which deals with {@link URI} objects
 * 
 * @author bmary
 * 
 */
public class UriHelper {

    /**
     * Transforms a {@link URI} object to a string containing only the following informations:
     * <ul>
     * <li>path</li>
     * <li>parameters</li>
     * </ul>
     * 
     * @param uri
     * @return
     */
    public String uriToString(URI uri) {
        StringBuffer buffer = new StringBuffer();

        if (uri != null) {
            if (uri.getPath().isEmpty()) {
                buffer.append("/");
            } else {
                buffer.append(uri.getPath());
            }

            if (uri.getQuery() != null) {
                buffer.append("?");
                buffer.append(uri.getQuery());
            }
        }

        return buffer.toString();
    }

}
