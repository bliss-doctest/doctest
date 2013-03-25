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

import static org.junit.Assert.assertEquals;

import java.net.URI;

import org.junit.Before;
import org.junit.Test;

/**
 * Unit tests for {@link UriHelper}
 * 
 * @author bmary
 * 
 */
public class UriToStringHelperUnitTest {

    private UriHelper helper;
    private URI uri;

    @Before
    public void setUp() {
        helper = new UriHelper();
    }

    @Test
    public void completeUri() throws Exception {
        uri = new URI("http://www.host.com:8876/path/resource/id?param=true");
        String result = helper.uriToString(uri);
        assertEquals("/path/resource/id?param=true", result);
    }

    @Test
    public void uriWithoutParam() throws Exception {
        uri = new URI("http://www.host.com:8876/path/resource/id");
        String result = helper.uriToString(uri);
        assertEquals("/path/resource/id", result);
    }

    @Test
    public void uriWithOnlyHostNameAndPort() throws Exception {
        uri = new URI("http://www.host.com:8876");
        String result = helper.uriToString(uri);
        assertEquals("/", result);
    }

    @Test
    public void uriWithOnlyHostName() throws Exception {
        uri = new URI("http://www.host.com");
        String result = helper.uriToString(uri);
        assertEquals("/", result);
    }

    @Test
    public void uriWithOnlyFtpHostName() throws Exception {
        uri = new URI("ftp://www.host.com");
        String result = helper.uriToString(uri);
        assertEquals("/", result);
    }

    @Test
    public void uriIsNull() throws Exception {
        uri = null;
        String result = helper.uriToString(uri);
        assertEquals("", result);
    }

    @Test
    public void uriIsEmpty() throws Exception {
        uri = new URI("");
        String result = helper.uriToString(uri);
        assertEquals("/", result);
    }
}
