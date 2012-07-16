package com.devbliss.doctest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.net.URI;
import java.util.List;

import org.junit.AfterClass;
import org.junit.Before;

import com.devbliss.doctest.renderer.html.HtmlItems;
import com.devbliss.doctest.utils.JSONHelper;

import de.devbliss.apitester.ApiTest;
import de.devbliss.apitester.ApiTest.HTTP_REQUEST;

public class LogicDocTest {

    protected static DocTestMachine docTest;

    private final ApiTest apiTest;
    private final JSONHelper jsonHelper;

    @Before
    public void ensureDocTestClassSet() {
        docTest.beginDoctest(this.getClass().getCanonicalName());
    }

    @AfterClass
    public static void finishDocTest() {
        LogicDocTest.docTest.endDocTest();
    }

    public LogicDocTest(
            DocTestMachine docTest,
            ApiTest apiTest,
            JSONHelper jsonHelper,
            HtmlItems templates) {
        LogicDocTest.docTest = docTest;
        this.apiTest = apiTest;
        this.jsonHelper = jsonHelper;
    }

    public void say(String say) {
        docTest.say(say);
    }

    public void sayNextSection(String sectionName) {
        docTest.sayNextSectionTitle(sectionName);
    }

    protected void sayUri(URI uri, HTTP_REQUEST httpRequest) throws Exception {
        sayUri(uri, null, httpRequest);
    }

    protected void sayUri(URI uri, Object obj, HTTP_REQUEST httpRequest) throws Exception {
        docTest.sayRequest(uri, jsonHelper.toJson(obj), httpRequest);
    }

    /**
     * 
     * The given POJO will be converted to JSON and be pretty printed.
     * 
     * @param obj
     * @throws Exception
     */
    protected void sayObject(Object obj) throws Exception {
        docTest.sayPreformatted(obj == null ? "" : new JSONHelper().toJson(obj, true));
    }

    /**
     * 
     * The given String will be formatted as-is and be highlighted in a fancy box.
     * 
     * @param code
     * @throws Exception
     */
    protected void sayPreformattedCode(String code) throws Exception {
        docTest.sayPreformatted(code == null ? "" : code);
    }

    protected Response makeGetRequestSilent(URI uri) throws Exception {
        return new Response(apiTest.get(uri));
    }

    protected Response makeGetRequest(URI uri) throws Exception {
        sayUri(uri, HTTP_REQUEST.GET);
        Response response = makeGetRequestSilent(uri);
        docTest.sayResponse(response.httpStatus, response.payload);
        return response;
    }

    protected Response makePostRequestSilent(URI uri, Object obj) throws Exception {
        return new Response(apiTest.post(uri, obj));
    }

    protected Response makePostRequest(URI uri) throws Exception {
        return makePostRequest(uri, null);
    }

    protected Response makePostRequest(URI uri, Object obj) throws Exception {
        sayUri(uri, obj, HTTP_REQUEST.POST);
        Response response = makePostRequestSilent(uri, obj);
        docTest.sayResponse(response.httpStatus, response.payload);
        return response;
    }

    protected Response makePutRequestSilent(URI uri, Object obj) throws Exception {
        return new Response(apiTest.put(uri, obj));
    }

    protected Response makePutRequest(URI uri) throws Exception {
        return makePutRequest(uri, null);
    }

    protected Response makePutRequest(URI uri, Object obj) throws Exception {
        sayUri(uri, obj, HTTP_REQUEST.PUT);
        Response response = makePutRequestSilent(uri, obj);
        docTest.sayResponse(response.httpStatus, response.payload);
        return response;
    }

    protected Response makeDeleteRequestSilent(URI uri) throws Exception {
        return new Response(apiTest.delete(uri));
    }

    protected Response makeDeleteRequestSilent(URI uri, Object obj) throws Exception {
        return new Response(apiTest.delete(uri, obj));
    }

    protected Response makeDeleteRequest(URI uri) throws Exception {
        return makeDeleteRequest(uri, null);
    }

    protected Response makeDeleteRequest(URI uri, Object obj) throws Exception {
        sayUri(uri, obj, HTTP_REQUEST.DELETE);
        Response response = makeDeleteRequestSilent(uri, obj);
        docTest.sayResponse(response.httpStatus, response.payload);
        return response;
    }

    protected void assertEqualsAndSay(Object expected, Object result) {
        assertEquals(expected, result);
        docTest.sayVerify(expected.toString());
    }

    protected void assertEqualsAndSay(Object expected, Object result, String message) {
        assertEquals(expected, result);
        docTest.sayVerify(message + expected.toString());
    }

    /**
     * 
     * At first converts both objects to Json and then asserts that they are equal.
     * The resulting doc will sport the expected Json String.
     * 
     * @param expected POJO
     * @param result POJO
     */
    protected void assertJsonEqualsAndSay(Object expected, Object result) {
        assertJsonEqualsAndSay(expected, result, "", null);
    }

    /**
     * 
     * At first converts both objects to Json and then asserts that they are equal, except on the
     * fields mentioned in exceptions.
     * The resulting doc will sport the expected Json String.
     * 
     * @param expected POJO
     * @param result POJO
     */

    protected void assertJsonEqualsAndSay(Object expected, Object result, List<String> exceptions) {
        assertJsonEqualsAndSay(expected, result, "", exceptions);
    }

    /**
     * 
     * First converts both objects to Json and then asserts that they are equal, except on the
     * fields mentioned in exceptions.
     * The resulting doc will sport the expected Json String after the given message.
     * 
     * @param expected POJO
     * @param result POJO
     * @param message Additional message to be concatenated to the expected Json
     * @param exceptions a List of fieldnames that will be omitted in comparison
     */
    protected void assertJsonEqualsAndSay(Object expected, Object result, String message,
            List<String> exceptions) {
        String expectedJson;
        String resultingJson;

        if (exceptions != null && exceptions.size() > 0) {
            expectedJson = jsonHelper.toJsonAndSkipCertainFields(expected, exceptions, true);
            resultingJson = jsonHelper.toJsonAndSkipCertainFields(result, exceptions, true);
        } else {
            expectedJson = jsonHelper.toJson(expected, true);
            resultingJson = jsonHelper.toJson(result, true);
        }

        assertEquals(expectedJson, resultingJson);
        docTest.sayVerify(message + expectedJson);
    }

    protected void assertTrueAndSay(Boolean condition) {
        assertTrue(condition);
        docTest.sayVerify(condition.toString());
    }

    protected void assertTrueAndSay(Boolean condition, String message) {
        assertTrue(condition);
        docTest.sayVerify(message + condition.toString());
    }

    protected void assertFalseAndSay(Boolean condition) {
        assertFalse(condition);
        docTest.sayVerify(condition.toString());
    }

    protected void assertFalseAndSay(Boolean condition, String message) {
        assertFalse(condition);
        docTest.sayVerify(message + condition.toString());
    }

}
