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

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

/**
 * Unit tests for the {@link RequestUploadDocItem}
 * 
 * @author bmary
 * 
 */
public class RequestUploadDocItemTest {

    private long fileSize;
    private String fileBody;
    private String fileName;
    private String uri;
    private final Map<String, String> headers = new HashMap<String, String>();
    private final Map<String, String> cookies = new HashMap<String, String>();

    @Test
    public void doNotshowFileBody() {
        RequestUploadDocItem item =
                new RequestUploadDocItem("post", uri, fileName, fileBody, fileSize,
                        "application/octet-stream", headers, cookies);
        assertFalse(item.getShowFileBody());
    }

    @Test
    public void showFileBody() {
        RequestUploadDocItem item =
                new RequestUploadDocItem("post", uri, fileName, fileBody, fileSize, "text/plain",
                        headers, cookies);
        assertTrue(item.getShowFileBody());
    }
}
