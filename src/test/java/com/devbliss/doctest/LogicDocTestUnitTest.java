package com.devbliss.doctest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyList;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Matchers.isA;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.devbliss.doctest.httpfactory.PostUploadWithoutRedirectImpl;
import com.devbliss.doctest.machine.DocTestMachine;
import com.devbliss.doctest.utils.FileHelper;
import com.devbliss.doctest.utils.JSONHelper;

import de.devbliss.apitester.ApiRequest;
import de.devbliss.apitester.ApiResponse;
import de.devbliss.apitester.ApiTest;
import de.devbliss.apitester.Context;
import de.devbliss.apitester.Cookie;
import de.devbliss.apitester.TestState;

@RunWith(MockitoJUnitRunner.class)
public class LogicDocTestUnitTest {

    public static final String MESSAGE = "i am the message and the message is me";

    private class ShouldFailException extends Exception {
        private static final long serialVersionUID = 1160418817945072075L;

        ShouldFailException() {
            super("The assertion should have failed");
        }
    }

    private static final String NULL = "NULL";
    private static final String OBJECT = "OBJECT";
    private static final String OBJECT2 = "OBJECT2";
    private static final String RESPONSE_PAYLOAD = "payload";
    private static final int HTTP_STATUS = 204;
    private static final String REASON_PHRASE = "No Content";
    protected static final String FILE_NAME = "file-name";
    private static final String HEADER_NAME1 = "Content-type";
    private static final String HTTP_METHOD = "httpMethod";

    @Mock
    private ApiTest apiTest;
    @Mock
    private DocTestMachine docTestMachine;
    @Mock
    private JSONHelper jsonHelper;
    @Mock
    private Object obj;
    @Mock
    private TestState testState;
    @Mock
    private FileHelper fileHelper;

    private LogicDocTest docTest;
    private URI uri;
    private ApiResponse response;
    private ApiRequest request;

    private File fileToUpload;
    private Context context;
    private List<String> headersToShow;
    private Map<String, String> headers;
    private Map<String, String> cookies;
    private List<String> cookiesToShow;

    @Before
    public void setUp() throws Exception {
        headersToShow = new ArrayList<String>();
        headersToShow.add(HEADER_NAME1);

        cookiesToShow = new ArrayList<String>();

        headers = new HashMap<String, String>();
        cookies = new HashMap<String, String>();

        uri = new URI("");
        request = new ApiRequest(uri, HTTP_METHOD, headers, cookies);
        response =
                new ApiResponse(HTTP_STATUS, REASON_PHRASE, RESPONSE_PAYLOAD, Collections
                        .<String, String> emptyMap());

        context = new Context(response, request);
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
        when(apiTest.get(uri)).thenReturn(context);
        docTest.makeGetRequest(uri);
        verify(docTestMachine).sayRequest(request, null, headersToShow, cookiesToShow);
        verify(docTestMachine).sayResponse(response, headersToShow);
    }

    @Test
    public void makeDeleteRequest() throws Exception {
        when(apiTest.delete(uri, null)).thenReturn(context);
        docTest.makeDeleteRequest(uri);
        verify(docTestMachine).sayRequest(request, NULL, headersToShow, cookiesToShow);
        verify(docTestMachine).sayResponse(response, headersToShow);
    }

    @Test
    public void makeDeleteRequestWithBody() throws Exception {
        when(apiTest.delete(uri, obj)).thenReturn(context);
        docTest.makeDeleteRequest(uri, obj);
        verify(docTestMachine).sayRequest(request, OBJECT, headersToShow, cookiesToShow);
        verify(docTestMachine).sayResponse(response, headersToShow);
    }

    @Test
    public void makePostRequest() throws Exception {
        when(apiTest.post(uri, null)).thenReturn(context);
        docTest.makePostRequest(uri);
        verify(docTestMachine).sayRequest(request, NULL, headersToShow, cookiesToShow);
        verify(docTestMachine).sayResponse(response, headersToShow);
    }

    @Test
    public void makePostRequestWithBody() throws Exception {
        when(apiTest.post(uri, obj)).thenReturn(context);
        docTest.makePostRequest(uri, obj);
        verify(docTestMachine).sayRequest(request, OBJECT, headersToShow, cookiesToShow);
        verify(docTestMachine).sayResponse(response, headersToShow);
    }

    @Test
    public void makePostUploadRequest() throws Exception {
        when(apiTest.post(eq(uri), eq(null), isA(PostUploadWithoutRedirectImpl.class))).thenReturn(
                context);
        when(fileHelper.readFile(fileToUpload)).thenReturn("fileBody");
        docTest.makePostUploadRequest(uri, fileToUpload, "paramName");

        verify(docTestMachine).sayUploadRequest(request, "file.txt", "fileBody",
                fileToUpload.length(), "text/plain", headersToShow, cookiesToShow);
        verify(docTestMachine).sayResponse(response, headersToShow);
    }

    @Test(expected = FileNotFoundException.class)
    public void makePostUploadRequestFileNotFound() throws Exception {
        when(apiTest.post(eq(uri), eq(null), any(PostUploadWithoutRedirectImpl.class))).thenReturn(
                context);
        doThrow(new FileNotFoundException()).when(fileHelper).readFile(fileToUpload);
        docTest.makePostUploadRequest(uri, fileToUpload, "paramName");
    }

    @Test
    public void makePutRequest() throws Exception {
        when(apiTest.put(uri, null)).thenReturn(context);
        docTest.makePutRequest(uri);
        verify(docTestMachine).sayRequest(request, NULL, headersToShow, cookiesToShow);
        verify(docTestMachine).sayResponse(response, headersToShow);
    }

    @Test
    public void makePutRequestWithBody() throws Exception {
        when(apiTest.put(uri, obj)).thenReturn(context);
        docTest.makePutRequest(uri, obj);
        verify(docTestMachine).sayRequest(request, OBJECT, headersToShow, cookiesToShow);
        verify(docTestMachine).sayResponse(response, headersToShow);
    }

    @Test
    public void assertTrueAndSay() throws Exception {
        docTest.assertTrueAndSay(true, MESSAGE);
        verify(docTestMachine).sayVerify(MESSAGE);
    }

    @Test
    public void assertTrueAndSayShouldFail() throws Exception {
        try {
            docTest.assertTrueAndSay(false, MESSAGE);
            throw new ShouldFailException();
        } catch (AssertionError e) {
            verify(docTestMachine, never()).sayVerify(MESSAGE);
        } catch (ShouldFailException e) {
            fail(e.getMessage());
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void assertNullAndSay() throws Exception {
        docTest.assertNullAndSay(null, MESSAGE);
        verify(docTestMachine).sayVerify(MESSAGE);
    }

    @Test
    public void assertNullAndSayShouldFail() throws Exception {
        try {
            docTest.assertNullAndSay(new String(), MESSAGE);
            throw new ShouldFailException();
        } catch (AssertionError assertionError) {
            verify(docTestMachine, never()).sayVerify(MESSAGE);
        } catch (ShouldFailException e) {
            fail(e.getMessage());
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void assertNotNullAndSay() throws Exception {
        docTest.assertNotNullAndSay(new String(), MESSAGE);
        verify(docTestMachine).sayVerify(MESSAGE);
    }

    @Test
    public void assertNotNullAndSayShouldFail() throws Exception {
        try {
            docTest.assertNotNullAndSay(null, MESSAGE);
            throw new ShouldFailException();
        } catch (AssertionError assertionError) {
            verify(docTestMachine, never()).sayVerify(MESSAGE);
        } catch (ShouldFailException e) {
            fail(e.getMessage());
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void assertFalseAndSay() throws Exception {
        docTest.assertFalseAndSay(false, MESSAGE);
        verify(docTestMachine).sayVerify(MESSAGE);
    }

    @Test
    public void assertFalseShouldFail() throws Exception {
        try {
            docTest.assertFalseAndSay(true, MESSAGE);
            throw new ShouldFailException();
        } catch (AssertionError e) {
            verify(docTestMachine, never()).sayVerify(anyString());
        } catch (ShouldFailException e) {
            fail(e.getMessage());
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void assertEqualsAndSay() throws Exception {
        docTest.assertEqualsAndSay("expected", "expected", MESSAGE);
        verify(docTestMachine).sayVerify(MESSAGE);
    }

    @Test
    public void assertEqualsAndSayShouldFail() throws Exception {
        try {
            docTest.assertEqualsAndSay("expected", "unexpected", MESSAGE);
            throw new ShouldFailException();
        } catch (AssertionError e) {
            verify(docTestMachine, never()).sayVerify(anyString());
        } catch (ShouldFailException e) {
            fail(e.getMessage());
        } catch (Exception e) {
            fail();
        }
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
        verify(testState).clearCookies();
    }

    @Test
    public void setTheNameOfTheFile() {
        docTest.ensureDocTestClassSet();
        verify(docTestMachine).beginDoctest(FILE_NAME, "");
    }

    @Test(expected = AssertionError.class)
    public void theFileNameIsAlreadyTaken() {
        doThrow(AssertionError.class).when(fileHelper).validateFileName(FILE_NAME);
        docTest.ensureDocTestClassSet();
    }

    private LogicDocTest instantiateAbstractDocTest() {
        return new LogicDocTest(docTestMachine, apiTest, jsonHelper, fileHelper) {

            @Override
            protected String getFileName() {
                return FILE_NAME;
            }

            @Override
            public List<String> showHeaders() {
                return headersToShow;
            }
        };
    }

    @Test
    public void useDefaultintroduction() {
        docTest.ensureDocTestClassSet();
        // verify default value is empty string
        verify(docTestMachine).beginDoctest(FILE_NAME, "");
    }

    @Test
    public void setIntroduction() {
        // create an instance of the logicDocTest which overrides the introduction function
        docTest = new LogicDocTest(docTestMachine, apiTest, jsonHelper, fileHelper) {
            @Override
            public String getIntroduction() {
                return "intro written by the user";
            }

            @Override
            protected String getFileName() {
                return FILE_NAME;
            }
        };

        docTest.ensureDocTestClassSet();
        // verify the new intro is used
        verify(docTestMachine).beginDoctest(FILE_NAME, "intro written by the user");
    }
}
