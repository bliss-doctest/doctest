package com.devbliss.doctest;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.net.URI;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import com.devbliss.doctest.templates.Templates;

import de.devbliss.apitester.ApiResponse;
import de.devbliss.apitester.ApiTest;
import de.devbliss.apitester.ApiTest.HTTP_REQUEST;

/**
 * Unit tests for the {@link DocTest}.
 * 
 * @author bmary
 * 
 */
@RunWith(MockitoJUnitRunner.class)
public class LogicDocTestUnitTest {

    private static final String NULL = "NULL";
    private static final String OBJECT = "OBJECT";
    private static final String RESPONSE_PAYLOAD = "payload";
    private static final int HTTP_STATUS = 204;
    @Mock
    private ApiTest apiTest;
    @Mock
    private DocTestMachine docTestMachine;
    @Mock
    private JSONHelper jsonHelper;
    @Mock
    private Object obj;
    @Mock
    private Templates templates;

    private LogicDocTest docTest;
    private URI uri;
    private ApiResponse apiResponse;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        uri = new URI("");
        apiResponse = new ApiResponse(HTTP_STATUS, RESPONSE_PAYLOAD);
        when(jsonHelper.toJson(null)).thenReturn(NULL);
        when(jsonHelper.toJson(obj)).thenReturn(OBJECT);
        docTest = instantiateAbstractDocTest();
    }

    @Test
    public void say() {
        docTest.say("abc");
        verify(docTestMachine).say("abc");
    }

    @Test
    public void makeGetRequest() throws Exception {
        when(apiTest.get(uri)).thenReturn(apiResponse);
        docTest.makeGetRequest(uri);
        verify(docTestMachine).sayRequest(uri, NULL, HTTP_REQUEST.GET);
        verify(docTestMachine).sayResponse(HTTP_STATUS, RESPONSE_PAYLOAD);
    }

    @Test
    public void makeDeleteRequest() throws Exception {
        when(apiTest.delete(uri, null)).thenReturn(apiResponse);
        docTest.makeDeleteRequest(uri);
        verify(docTestMachine).sayRequest(uri, NULL, HTTP_REQUEST.DELETE);
        verify(docTestMachine).sayResponse(HTTP_STATUS, RESPONSE_PAYLOAD);
    }

    @Test
    public void makeDeleteRequestWithBody() throws Exception {
        when(apiTest.delete(uri, obj)).thenReturn(apiResponse);
        docTest.makeDeleteRequest(uri, obj);
        verify(docTestMachine).sayRequest(uri, OBJECT, HTTP_REQUEST.DELETE);
        verify(docTestMachine).sayResponse(HTTP_STATUS, RESPONSE_PAYLOAD);
    }

    @Test
    public void makePostRequest() throws Exception {
        when(apiTest.post(uri, null)).thenReturn(apiResponse);
        docTest.makePostRequest(uri);
        verify(docTestMachine).sayRequest(uri, NULL, HTTP_REQUEST.POST);
        verify(docTestMachine).sayResponse(HTTP_STATUS, RESPONSE_PAYLOAD);
    }

    @Test
    public void makePostRequestWithBody() throws Exception {
        when(apiTest.post(uri, obj)).thenReturn(apiResponse);
        docTest.makePostRequest(uri, obj);
        verify(docTestMachine).sayRequest(uri, OBJECT, HTTP_REQUEST.POST);
        verify(docTestMachine).sayResponse(HTTP_STATUS, RESPONSE_PAYLOAD);
    }

    @Test
    public void makePutRequest() throws Exception {
        when(apiTest.put(uri, null)).thenReturn(apiResponse);
        docTest.makePutRequest(uri);
        verify(docTestMachine).sayRequest(uri, NULL, HTTP_REQUEST.PUT);
        verify(docTestMachine).sayResponse(HTTP_STATUS, RESPONSE_PAYLOAD);
    }

    @Test
    public void makePutRequestWithBody() throws Exception {
        when(apiTest.put(uri, obj)).thenReturn(apiResponse);
        docTest.makePutRequest(uri, obj);
        verify(docTestMachine).sayRequest(uri, OBJECT, HTTP_REQUEST.PUT);
        verify(docTestMachine).sayResponse(HTTP_STATUS, RESPONSE_PAYLOAD);
    }

    private LogicDocTest instantiateAbstractDocTest() {
        return new LogicDocTest(docTestMachine, apiTest, jsonHelper, templates);
    }
}
