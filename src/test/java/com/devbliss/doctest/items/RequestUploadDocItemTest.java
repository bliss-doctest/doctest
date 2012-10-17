package com.devbliss.doctest.items;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import de.devbliss.apitester.ApiRequest;
import de.devbliss.apitester.ApiTest.HTTP_REQUEST;

/**
 * Unit tests for the {@link RequestUploadDocItem}
 * 
 * @author bmary
 * 
 */
@RunWith(MockitoJUnitRunner.class)
public class RequestUploadDocItemTest {

    @Mock
    private ApiRequest request;

    private long fileSize;
    private String fileBody;
    private String fileName;
    private Map<String, String> headers = new HashMap<String, String>();

    @Test
    public void doNotshowFileBody() {
        RequestUploadDocItem item =
                new RequestUploadDocItem(HTTP_REQUEST.POST, request, fileName, fileBody, fileSize,
                        "application/octet-stream", headers);
        assertFalse(item.getShowFileBody());
    }

    @Test
    public void showFileBody() {
        RequestUploadDocItem item =
                new RequestUploadDocItem(HTTP_REQUEST.POST, request, fileName, fileBody, fileSize,
                        "text/plain", headers);
        assertTrue(item.getShowFileBody());
    }
}
