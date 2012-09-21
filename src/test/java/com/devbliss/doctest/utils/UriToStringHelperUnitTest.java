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
