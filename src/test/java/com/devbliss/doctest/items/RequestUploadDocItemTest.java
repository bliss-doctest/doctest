package com.devbliss.doctest.items;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import de.devbliss.apitester.ApiTest.HTTP_REQUEST;

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
    private HTTP_REQUEST http;

    @Test
    public void doNotshowFileBody() {
        RequestUploadDocItem item =
                new RequestUploadDocItem(http, uri, fileName, fileBody, fileSize,
                        "application/octet-stream");
        assertFalse(item.getShowFileBody());
    }

    @Test
    public void showFileBody() {
        RequestUploadDocItem item =
                new RequestUploadDocItem(http, uri, fileName, fileBody, fileSize, "text/plain");
        assertTrue(item.getShowFileBody());
    }
}
