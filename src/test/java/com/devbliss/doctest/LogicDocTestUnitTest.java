package com.devbliss.doctest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.Matchers.anyList;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URI;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import com.devbliss.doctest.machine.DocTestMachine;
import com.devbliss.doctest.renderer.html.HtmlItems;
import com.devbliss.doctest.utils.FileHelper;
import com.devbliss.doctest.utils.JSONHelper;

import de.devbliss.apitester.ApiResponse;
import de.devbliss.apitester.ApiTest;
import de.devbliss.apitester.ApiTest.HTTP_REQUEST;
import de.devbliss.apitester.Cookie;
import de.devbliss.apitester.TestState;

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
    private static final String OBJECT2 = "OBJECT2";
    private static final String RESPONSE_PAYLOAD = "payload";
    private static final int HTTP_STATUS = 204;
    private static final String REASON_PHRASE = "No Content";
    @Mock
    private ApiTest apiTest;
    @Mock
    private DocTestMachine docTestMachine;
    @Mock
    private JSONHelper jsonHelper;
    @Mock
    private Object obj;
    @Mock
    private HtmlItems templates;
    @Mock
    private TestState testState;
    @Mock
    private FileHelper fileHelper;

    private LogicDocTest docTest;
    private URI uri;
    private ApiResponse response;
    private File fileToUpload;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        uri = new URI("");
        response =
                new ApiResponse(HTTP_STATUS, REASON_PHRASE, RESPONSE_PAYLOAD, Collections
                        .<String, String> emptyMap());
        when(jsonHelper.toJson(null)).thenReturn(NULL);
        when(jsonHelper.toJson(obj)).thenReturn(OBJECT);
        when(apiTest.getTestState()).thenReturn(testState);
        docTest = instantiateAbstractDocTest();
        fileToUpload = new File("src/test/resources/file.txt");
    }

    @Test
    public void say() {
        docTest.say("abc");
        verify(docTestMachine).say("abc");
    }

    @Test
    public void finishDocTest() throws Exception {
        LogicDocTest.finishDocTest();
        verify(docTestMachine).endDocTest();
        verify(docTestMachine).prepareDocTest();
    }

    @Test
    public void makeGetRequest() throws Exception {
        when(apiTest.get(uri)).thenReturn(response);
        docTest.makeGetRequest(uri);
        verify(docTestMachine).sayRequest(uri, NULL, HTTP_REQUEST.GET);
        verify(docTestMachine).sayResponse(HTTP_STATUS, RESPONSE_PAYLOAD);
    }

    @Test
    public void makeDeleteRequest() throws Exception {
        when(apiTest.delete(uri, null)).thenReturn(response);
        docTest.makeDeleteRequest(uri);
        verify(docTestMachine).sayRequest(uri, NULL, HTTP_REQUEST.DELETE);
        verify(docTestMachine).sayResponse(HTTP_STATUS, RESPONSE_PAYLOAD);
    }

    @Test
    public void makeDeleteRequestWithBody() throws Exception {
        when(apiTest.delete(uri, obj)).thenReturn(response);
        docTest.makeDeleteRequest(uri, obj);
        verify(docTestMachine).sayRequest(uri, OBJECT, HTTP_REQUEST.DELETE);
        verify(docTestMachine).sayResponse(HTTP_STATUS, RESPONSE_PAYLOAD);
    }

    @Test
    public void makePostRequest() throws Exception {
        when(apiTest.post(uri, null)).thenReturn(response);
        docTest.makePostRequest(uri);
        verify(docTestMachine).sayRequest(uri, NULL, HTTP_REQUEST.POST);
        verify(docTestMachine).sayResponse(HTTP_STATUS, RESPONSE_PAYLOAD);
    }

    @Test
    public void makePostRequestWithBody() throws Exception {
        when(apiTest.post(uri, obj)).thenReturn(response);
        docTest.makePostRequest(uri, obj);
        verify(docTestMachine).sayRequest(uri, OBJECT, HTTP_REQUEST.POST);
        verify(docTestMachine).sayResponse(HTTP_STATUS, RESPONSE_PAYLOAD);
    }

    @Test
    public void makePostUploadRequest() throws Exception {
        when(apiTest.post(uri, null)).thenReturn(response);
        when(fileHelper.readFile(fileToUpload)).thenReturn("fileBody");
        docTest.makePostUploadRequest(uri, fileToUpload, "paramName");
        verify(docTestMachine).sayUploadRequest(uri, HTTP_REQUEST.POST, "file.txt", "fileBody",
                fileToUpload.length());
        verify(docTestMachine).sayResponse(HTTP_STATUS, RESPONSE_PAYLOAD);
    }

    @Test(expected = FileNotFoundException.class)
    public void makePostUploadRequestFileNotFound() throws Exception {
        when(apiTest.post(uri, null)).thenReturn(response);
        doThrow(new FileNotFoundException()).when(fileHelper).readFile(fileToUpload);
        docTest.makePostUploadRequest(uri, fileToUpload, "paramName");
    }

    @Test
    public void makePutRequest() throws Exception {
        when(apiTest.put(uri, null)).thenReturn(response);
        docTest.makePutRequest(uri);
        verify(docTestMachine).sayRequest(uri, NULL, HTTP_REQUEST.PUT);
        verify(docTestMachine).sayResponse(HTTP_STATUS, RESPONSE_PAYLOAD);
    }

    @Test
    public void makePutRequestWithBody() throws Exception {
        when(apiTest.put(uri, obj)).thenReturn(response);
        docTest.makePutRequest(uri, obj);
        verify(docTestMachine).sayRequest(uri, OBJECT, HTTP_REQUEST.PUT);
        verify(docTestMachine).sayResponse(HTTP_STATUS, RESPONSE_PAYLOAD);
    }

    @Test
    public void assertTrue() throws Exception {
        docTest.assertTrueAndSay(true);
        verify(docTestMachine).sayVerify("true");
    }

    @Test
    public void assertTrueIsFalse() throws Exception {
        try {
            docTest.assertTrueAndSay(false);
            fail();
        } catch (AssertionError e) {
            verify(docTestMachine, never()).sayVerify("true");
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void assertFalse() throws Exception {
        docTest.assertFalseAndSay(false);
        verify(docTestMachine).sayVerify("false");
    }

    @Test
    public void assertFalseIsFalse() throws Exception {
        try {
            docTest.assertFalseAndSay(true);
            fail();
        } catch (AssertionError e) {
            verify(docTestMachine, never()).sayVerify("false");
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void assertEqualsString() throws Exception {
        docTest.assertEqualsAndSay("expected", "expected");
        verify(docTestMachine).sayVerify("expected");
    }

    @Test
    public void assertStringAreNotEquals() throws Exception {
        try {
            docTest.assertEqualsAndSay("expected", "result");
            fail();
        } catch (AssertionError e) {
            verify(docTestMachine, never()).sayVerify("expected");
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void assertEqualsInt() throws Exception {
        docTest.assertEqualsAndSay(100, 100);
        verify(docTestMachine).sayVerify("100");
    }

    @Test
    public void assertIntAreNotEquals() throws Exception {
        try {
            docTest.assertEqualsAndSay(100, 105);
            fail();
        } catch (AssertionError e) {
            verify(docTestMachine, never()).sayVerify("100");
        } catch (Exception e) {
            fail();
        }
    }

    private LogicDocTest instantiateAbstractDocTest() {
        return new LogicDocTest(docTestMachine, apiTest, jsonHelper, templates, fileHelper);
    }

    @Test
    public void assertEqualsJsonObjects() throws Exception {
        Object object1 = new Object();
        Object object2 = new Object();
        when(jsonHelper.toJson(object1, true)).thenReturn(OBJECT);
        when(jsonHelper.toJson(object2, true)).thenReturn(OBJECT);

        docTest.assertJsonEqualsAndSay(object1, object2);
        verify(docTestMachine).sayVerify(anyString());
    }

    @Test
    public void assertStringAreNotEqualsJsonObjects() throws Exception {
        Object object1 = new Object();
        Object object2 = new Object();
        when(jsonHelper.toJson(object1, true)).thenReturn(OBJECT);
        when(jsonHelper.toJson(object2, true)).thenReturn(OBJECT2);

        try {
            docTest.assertJsonEqualsAndSay(object1, object2);
            fail();
        } catch (AssertionError e) {
            verify(docTestMachine, never()).sayVerify(anyString());
        } catch (Exception e) {
            fail();
        }
    }

    @SuppressWarnings("unchecked")
    @Test
    public void assertStringAreNotEqualsJsonObjectsExcluded() throws Exception {
        Object object1 = new Object();
        Object object2 = new Object();
        when(jsonHelper.toJsonAndSkipCertainFields(eq(object1), anyList(), eq(true))).thenReturn(
                OBJECT);
        when(jsonHelper.toJsonAndSkipCertainFields(eq(object2), anyList(), eq(true))).thenReturn(
                OBJECT2);

        try {
            docTest.assertJsonEqualsAndSay(object1, object2, "", Arrays.asList(""));
            fail();
        } catch (AssertionError e) {
            verify(docTestMachine, never()).sayVerify(anyString());
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void assertCookieEqualsShouldPassWhenEquals() {
        when(testState.getCookieValue("name")).thenReturn("value");
        docTest.assertCookieEqualsAndSay("name", "value");
        verify(docTestMachine).sayVerify(anyString());
    }

    @Test(expected = AssertionError.class)
    public void assertCookieEqualsShouldFailWhenNotEquals() {
        when(testState.getCookieValue("name")).thenReturn("value");
        docTest.assertCookieEqualsAndSay("name", "notvalue");
    }

    @Test(expected = AssertionError.class)
    public void assertCookieEqualsShouldFailWhenNotPresent() {
        docTest.assertCookieEqualsAndSay("name", "notvalue");
    }

    @Test
    public void assertCookiePresentShouldPassWhenPresent() {
        when(testState.getCookieValue("name")).thenReturn("value");
        docTest.assertCookiePresentAndSay("name");
        verify(docTestMachine).sayVerify(anyString());
    }

    @Test(expected = AssertionError.class)
    public void assertCookiePresentShouldFailWhenNotPresent() {
        docTest.assertCookiePresentAndSay("name");
    }

    @Test
    public void assertCookieNotPresentShouldPassWhenNotPresent() {
        docTest.assertCookieNotPresentAndSay("name");
        verify(docTestMachine).sayVerify(anyString());
    }

    @Test(expected = AssertionError.class)
    public void assertCookieNotPresentShouldFailWhenPresent() {
        when(testState.getCookieValue("name")).thenReturn("value");
        docTest.assertCookieNotPresentAndSay("name");
    }

    @Test
    public void assertCookieMatchesShouldPassWhenMatches() {
        when(testState.getCookie("name")).thenReturn(new Cookie("name", "value"));
        docTest.assertCookieMatchesAndSay(new Cookie("name", "value"));
        verify(docTestMachine).sayVerify(anyString());
    }

    @Test(expected = AssertionError.class)
    public void assertCookieMatchesShouldFailWhenNotMatches() {
        when(testState.getCookie("name")).thenReturn(new Cookie("name", "value"));
        docTest.assertCookieMatchesAndSay(new Cookie("name", "value", new Date()));
    }

    @Test(expected = AssertionError.class)
    public void assertCookieMatchesShouldFailWhenNotPresent() {
        docTest.assertCookieMatchesAndSay(new Cookie("name", "value"));
    }

    @Test
    public void getCookieValueShouldGetCookieValue() {
        when(testState.getCookieValue("name")).thenReturn("value");
        assertEquals("value", docTest.getCookieValue("name"));
    }

    @Test
    public void addCookieShouldAddCookie() {
        docTest.addCookie("name", "value");
        verify(testState).addCookie(
                new Cookie("name", "value", null, "/", "localhost", false, false));
    }

    @Test
    public void clearCookiesShouldClearCookies() {
        docTest.clearCookies();
        testState.clearCookies();
    }
}
