package com.devbliss.doctest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.net.URI;
import java.util.List;

import org.junit.AfterClass;
import org.junit.Before;

import com.devbliss.doctest.machine.DocTestMachine;
import com.devbliss.doctest.renderer.html.HtmlItems;
import com.devbliss.doctest.utils.JSONHelper;

import de.devbliss.apitester.ApiTest;
import de.devbliss.apitester.ApiTest.HTTP_REQUEST;
import de.devbliss.apitester.Cookie;

public class LogicDocTest {

    protected static DocTestMachine docTest;

    private final ApiTest apiTest;
    private final JSONHelper jsonHelper;

    @Before
    public void ensureDocTestClassSet() {
        docTest.beginDoctest(this.getClass().getCanonicalName());
    }

    @AfterClass
    public static void finishDocTest() throws Exception {
        LogicDocTest.docTest.endDocTest();
        LogicDocTest.docTest.prepareDocTest();
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

    /**
     * say will result in a text in the resulting document with the support of JSON representations
     * for objects and highlighted text.
     * 
     * @param say the text to be said
     * @param objects multiple Objects matching the amount of placeholdern in the text
     */
    public void say(String say, Object... objects) {
        String[] stringRepresentations = new String[objects.length];
        for (int i = 0; i < objects.length; i++) {
            if (objects[i] instanceof String) {
                stringRepresentations[i] = (String) objects[i];
            } else {
                stringRepresentations[i] = jsonHelper.toJson(objects[i], true);
            }
        }
        docTest.say(say, stringRepresentations);
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

    /**
     * Assert that the value of the cookie with the given name equals the given value.
     *
     * The resulting cookie name and value will be printed, separated by a colon.
     *
     * @param name The name of the cookie
     * @param expectedValue The expected value
     */
    protected void assertCookieEqualsAndSay(String name, String expectedValue) {
        String value = apiTest.getTestState().getCookieValue(name);
        assertEquals(expectedValue, value);
        docTest.sayVerify(name + ": " + value);
    }

    /**
     * Assert that the cookie with the given name is present.
     *
     * The resulting cookie name and value will be printed, separated by a colon.
     *
     * @param name The name of the cookie
     */
    protected void assertCookiePresentAndSay(String name) {
        String value = apiTest.getTestState().getCookieValue(name);
        assertNotNull(value);
        docTest.sayVerify(name + ": " + value);
    }

    /**
     * Assert that the cookie with the given name is not.
     *
     * The resulting cookie name and null will be printed, separated by a colon.
     *
     * @param name The name of the cookie
     */
    protected void assertCookieNotPresentAndSay(String name) {
        String value = apiTest.getTestState().getCookieValue(name);
        assertNull(value);
        docTest.sayVerify(name + ": " + value);
    }

    /**
     * Assert that a cookie is present that matches the attributes of the given
     * cookie
     *
     * The resulting cookie will be output with its parameters, in JSON format.
     *
     * @param expected The cookie to check
     */
    protected void assertCookieMatchesAndSay(Cookie expected) {
        Cookie cookie = apiTest.getTestState().getCookie(expected.name);
        assertNotNull(cookie);
        assertEquals(expected.value, cookie.value);
        if (expected.expires == null) {
            assertNull(cookie.expires);
        } else {
            assertNotNull(cookie.expires);
        }
        assertEquals(expected.path, cookie.path);
        assertEquals(expected.domain, cookie.domain);
        assertEquals(expected.secure, cookie.secure);
        assertEquals(expected.httpOnly, cookie.httpOnly);
        docTest.sayVerify(jsonHelper.toJson(cookie));
    }

    /**
     * Get the value of the cookie with the given name
     *
     * @param name The name of the cookie
     * @return The cookies value, or null if no cookie with that name was found
     */
    protected String getCookieValue(String name) {
        return apiTest.getTestState().getCookieValue(name);
    }

    /**
     * Add the cookie with the given name and value to the current state
     *
     * @param name The name of the cookie
     * @param value The value of the cookie
     */
    protected void addCookie(String name, String value) {
        apiTest.getTestState().addCookie(new Cookie(name, value, null, "/", "localhost", false, false));
    }

    /**
     * Clear all cookies from the current state
     */
    protected void clearCookies() {
        apiTest.getTestState().clearCookies();
    }
}
