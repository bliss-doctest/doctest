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

package com.devbliss.doctest.items;

import java.util.Map;

public class RequestDocItem implements DocItem {

    protected final String http;
    protected final String uri;
    protected final JsonDocItem payload;
    protected Boolean isAnUploadRequest = false;
    protected Map<String, String> headers;
    protected Map<String, String> cookies;

    public RequestDocItem(
            String http,
            String uri,
            Map<String, String> headers,
            Map<String, String> cookies) {
        this(http, uri, new JsonDocItem(null), headers, cookies);
    }

    public RequestDocItem(
            String http,
            String uri,
            String payload,
            Map<String, String> headers,
            Map<String, String> cookies) {
        this(http, uri, new JsonDocItem(payload), headers, cookies);
    }

    private RequestDocItem(
            String http,
            String uri,
            JsonDocItem payload,
            Map<String, String> headers,
            Map<String, String> cookies) {
        this.http = http;
        this.uri = uri;
        this.payload = payload;
        this.headers = headers;
        this.cookies = cookies;
    }

    public String getHttp() {
        return http.toUpperCase();
    }

    public String getUri() {
        return uri;
    }

    public JsonDocItem getPayload() {
        return payload;
    }

    public Boolean getIsAnUploadRequest() {
        return isAnUploadRequest;
    }

    public String getItemName() {
        return "request";
    }

    public Map<String, String> getHeaders() {
        return this.headers;
    }

    public Map<String, String> getCookies() {
        return this.cookies;
    }
}
