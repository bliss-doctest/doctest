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
