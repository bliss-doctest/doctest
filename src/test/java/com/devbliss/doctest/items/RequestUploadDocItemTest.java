package com.devbliss.doctest.items;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

/**
 * Unit tests for the {@link RequestUploadDocItem}
 * 
 * @author bmary
 * 
 */
@RunWith(MockitoJUnitRunner.class)
public class RequestUploadDocItemTest {

    private long fileSize;
    private String fileBody;
    private String fileName;
    private final Map<String, String> headers = new HashMap<String, String>();
    private String uri;

    @Test
    public void doNotshowFileBody() {
        RequestUploadDocItem item =
                new RequestUploadDocItem("post", uri, fileName, fileBody, fileSize,
                        "application/octet-stream", headers);
        assertFalse(item.getShowFileBody());
    }

    @Test
    public void showFileBody() {
        RequestUploadDocItem item =
                new RequestUploadDocItem("post", uri, fileName, fileBody, fileSize, "text/plain",
                        headers);
        assertTrue(item.getShowFileBody());
    }
}
