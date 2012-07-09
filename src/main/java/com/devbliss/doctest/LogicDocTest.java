package com.devbliss.doctest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.net.URI;

import org.junit.AfterClass;
import org.junit.Before;

import com.devbliss.doctest.templates.Templates;
import com.google.inject.Inject;

import de.devbliss.apitester.ApiResponse;
import de.devbliss.apitester.ApiTest;
import de.devbliss.apitester.ApiTest.HTTP_REQUEST;

public class LogicDocTest {

    protected static DocTestMachine docTest;

    private final ApiTest apiTest;
    private final JSONHelper jsonHelper;

    @Before
    public void ensureDocTestClassSet() {
        docTest.beginDoctest(this.getClass());
    }

    @AfterClass
    public static void finishDocTest() {
        LogicDocTest.docTest.endDocTest();
    }

    @Inject
    public LogicDocTest(
            DocTestMachine docTest,
            ApiTest apiTest,
            JSONHelper jsonHelper,
            Templates templates) {
        LogicDocTest.docTest = docTest;
        this.apiTest = apiTest;
        this.jsonHelper = jsonHelper;
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
        docTest.sayRequest(uri, jsonHelper.toJson(obj), httpRequest);
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
        sayUri(uri, obj, HTTP_REQUEST.DELETE);
        ApiResponse response = makeDeleteRequestSilent(uri, obj);
        docTest.sayResponse(response.httpStatus, response.payload);
        return response;
    }

    protected void assertEqualsAndSay(String expected, String result) {
        assertEquals(expected, result);
        docTest.sayVerify(expected.toString());
    }

    protected void assertEqualsAndSay(int expected, int result) {
        assertEqualsAndSay(String.valueOf(expected), String.valueOf(result));
    }

    protected void assertTrueAndSay(Boolean condition) {
        assertTrue(condition);
        docTest.sayVerify(condition.toString());
    }

    protected void assertFalseAndSay(Boolean condition) {
        assertFalse(condition);
        docTest.sayVerify(condition.toString());
    }
}
