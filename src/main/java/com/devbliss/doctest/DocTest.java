package com.devbliss.doctest;

import static org.junit.Assert.assertEquals;

import java.net.URI;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;

import com.devbliss.doctest.templates.Templates;
import com.google.gson.Gson;

import de.devbliss.apitester.ApiResponse;
import de.devbliss.apitester.ApiTest;
import de.devbliss.apitester.ApiTest.HTTP_REQUEST;

public abstract class DocTest {

    protected static DocTestMachine docTest;

    private final ApiTest apiTest = new ApiTest();

    protected abstract Class<?> getTestClass();

    @BeforeClass
    public static void initDocTest() {
        docTest = new DocTestMachineImpl();
    }

    @Before
    public void ensureDocTestClassSet() {
        docTest.beginDoctest(getTestClass());
    }

    @AfterClass
    public static void finishDocTest() {
        docTest.endDocTest();
    }

    public void say(String say) {
        docTest.say(say);
    }

    public void sayNextSection(String sectionName) {
        docTest.sayNextSection(sectionName);
    }

    protected void sayUri(URI uri, HTTP_REQUEST httpRequest) throws Exception {
        sayUri(uri, null, httpRequest);
    }

    protected void sayUri(URI uri, Object obj, HTTP_REQUEST httpRequest) throws Exception {
        docTest.sayRequest(uri, new Gson().toJson(obj), httpRequest);
    }

    protected ApiResponse makeGetRequestSilent(URI uri) throws Exception {
        return apiTest.get(uri);
    }

    protected ApiResponse makeGetRequest(URI uri) throws Exception {
        sayUri(uri, HTTP_REQUEST.GET);
        ApiResponse response = makeGetRequestSilent(uri);
        docTest.sayResponse(response.httpStatus, response.payload);
        return response;
    }

    protected ApiResponse makePostRequestSilent(URI uri, Object obj) throws Exception {
        return apiTest.post(uri, obj);
    }

    protected ApiResponse makePostRequest(URI uri) throws Exception {
        return makePostRequest(uri, null);
    }

    protected ApiResponse makePostRequest(URI uri, Object obj) throws Exception {
        sayUri(uri, obj, HTTP_REQUEST.POST);
        ApiResponse response = makePostRequestSilent(uri, obj);
        docTest.sayResponse(response.httpStatus, response.payload);
        return response;
    }

    protected ApiResponse makePutRequestSilent(URI uri, Object obj) throws Exception {
        return apiTest.put(uri, obj);
    }

    protected ApiResponse makePutRequest(URI uri) throws Exception {
        return makePutRequest(uri, null);
    }

    protected ApiResponse makePutRequest(URI uri, Object obj) throws Exception {
        sayUri(uri, obj, HTTP_REQUEST.PUT);
        ApiResponse response = makePutRequestSilent(uri, obj);
        docTest.sayResponse(response.httpStatus, response.payload);
        return response;
    }

    protected ApiResponse makeDeleteRequestSilent(URI uri) throws Exception {
        return apiTest.delete(uri);
    }

    protected ApiResponse makeDeleteRequestSilent(URI uri, Object obj) throws Exception {
        return apiTest.delete(uri, obj);
    }

    protected ApiResponse makeDeleteRequest(URI uri) throws Exception {
        return makeDeleteRequest(uri, null);
    }

    protected ApiResponse makeDeleteRequest(URI uri, Object obj) throws Exception {
        sayUri(uri, HTTP_REQUEST.DELETE);
        ApiResponse response = makeDeleteRequestSilent(uri, obj);
        docTest.sayResponse(response.httpStatus, response.payload);
        return response;
    }

    protected void assertEqualsAndSay(String expected, String result) {
        assertEquals(expected, result);
        docTest.say(Templates.getVerifyTemplate(expected, result));
    }

    protected void assertEqualsAndSay(int expected, int result) {
        assertEqualsAndSay(String.valueOf(expected), String.valueOf(result));
    }
}
