/*
 * Copyright 2013, devbliss GmbH
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */

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
