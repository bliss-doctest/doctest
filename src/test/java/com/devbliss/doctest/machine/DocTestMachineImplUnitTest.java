package com.devbliss.doctest.machine;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.devbliss.doctest.items.AssertDocItem;
import com.devbliss.doctest.items.DocItem;
import com.devbliss.doctest.items.JsonDocItem;
import com.devbliss.doctest.items.RequestDocItem;
import com.devbliss.doctest.items.RequestUploadDocItem;
import com.devbliss.doctest.items.ResponseDocItem;
import com.devbliss.doctest.items.SectionDocItem;
import com.devbliss.doctest.items.TextDocItem;
import com.devbliss.doctest.renderer.ReportRenderer;
import com.devbliss.doctest.utils.FilterHelper;
import com.devbliss.doctest.utils.JSONHelper;
import com.devbliss.doctest.utils.UriHelper;

import de.devbliss.apitester.ApiRequest;
import de.devbliss.apitester.ApiResponse;

/**
 * Unit test for {@link DocTestMachineImpl}
 * 
 * @author bmary
 * 
 */
@RunWith(MockitoJUnitRunner.class)
public class DocTestMachineImplUnitTest {

    private static final String FILE_NAME = "className";
    private static final String TEXT = "text";
    private static final int RESPONSE_CODE = 130;
    private static final String JSON_VALID = "{'abc':'a'}";
    private static final String JSON_INVALID = "invalid";
    private static final String INTRODUCTION = "";
    private static final String HTTP_METHOD = "get";
    private static final String PRETTY_JSON = "pretty_json";

    @Mock
    private ReportRenderer renderer;
    @Mock
    private JSONHelper jsonHelper;
    @Mock
    private List<DocItem> listItem;
    @Mock
    private UriHelper uriHelper;
    @Mock
    private FilterHelper headersHelper;

    @Captor
    private ArgumentCaptor<List<DocItem>> listItemCaptor;

    private ApiRequest apiRequest;
    private ApiResponse apiResponse;
    private DocTestMachineImpl machine;
    private final String uriString = "www.example.de:91/ressource";
    private URI uri;
    private List<String> headersToShow;
    private Map<String, String> headers;
    private Map<String, String> cookies;
    private List<String> cookiesToShow;
    private Map<String, String> filteredHeaders;
    private Map<String, String> filteredCookies;

    @Before
    public void setUp() throws URISyntaxException {
        uri = new URI("");
        initHeadersAndCookies();

        apiRequest = new ApiRequest(uri, HTTP_METHOD, headers, cookies);
        apiResponse = new ApiResponse(RESPONSE_CODE, "", JSON_VALID, headers);

        when(jsonHelper.isJsonValid(JSON_VALID)).thenReturn(true);
        when(jsonHelper.prettyPrintJson(JSON_VALID)).thenReturn(PRETTY_JSON);
        when(uriHelper.uriToString(uri)).thenReturn(uriString);

        machine = spy(new DocTestMachineImpl(renderer, jsonHelper, uriHelper, headersHelper));
    }

    @Test
    public void render() throws Exception {
        when(machine.getListItem()).thenReturn(listItem);
        machine.beginDoctest(FILE_NAME, INTRODUCTION);
        machine.endDocTest();
        machine.prepareDocTest();
        verify(listItem).clear();
    }

    @Test
    public void addTextItem() throws Exception {
        machine.beginDoctest(FILE_NAME, INTRODUCTION);
        machine.say(TEXT);
        machine.endDocTest();

        verify(renderer).render(listItemCaptor.capture(), eq(FILE_NAME), eq(INTRODUCTION));

        List<DocItem> listItems = listItemCaptor.getValue();
        assertEquals(1, listItems.size());
        assertTrue(listItems.get(0) instanceof TextDocItem);
        assertEquals(TEXT, ((TextDocItem) listItems.get(0)).getText());
    }

    @Test
    public void addSectionItem() throws Exception {
        machine.beginDoctest(FILE_NAME, INTRODUCTION);
        machine.sayNextSectionTitle(TEXT);
        machine.endDocTest();

        verify(renderer).render(listItemCaptor.capture(), eq(FILE_NAME), eq(INTRODUCTION));

        List<DocItem> listItems = listItemCaptor.getValue();
        assertEquals(1, listItems.size());
        assertTrue(listItems.get(0) instanceof SectionDocItem);
        assertEquals(TEXT, ((SectionDocItem) listItems.get(0)).getTitle());
    }

    @Test
    public void addAssertItem() throws Exception {
        machine.beginDoctest(FILE_NAME, INTRODUCTION);
        machine.sayVerify(TEXT);
        machine.endDocTest();

        verify(renderer).render(listItemCaptor.capture(), eq(FILE_NAME), eq(INTRODUCTION));

        List<DocItem> listItems = listItemCaptor.getValue();
        assertEquals(1, listItems.size());
        assertTrue(listItems.get(0) instanceof AssertDocItem);
        assertEquals(TEXT, ((AssertDocItem) listItems.get(0)).getExpected());
    }

    @Test
    public void addResponseItemWithValidJsonContent() throws Exception {
        machine.beginDoctest(FILE_NAME, INTRODUCTION);
        machine.sayResponse(apiResponse, headersToShow);
        machine.endDocTest();

        verify(renderer).render(listItemCaptor.capture(), eq(FILE_NAME), eq(INTRODUCTION));

        List<DocItem> listItems = listItemCaptor.getValue();
        assertEquals(1, listItems.size());
        assertTrue(listItems.get(0) instanceof ResponseDocItem);
        assertEquals(PRETTY_JSON, ((ResponseDocItem) listItems.get(0)).getPayload().getExpected());
        assertEquals(RESPONSE_CODE, ((ResponseDocItem) listItems.get(0)).getResponseCode());
    }

    @Test
    public void addResponseItemWithContentTypeJson() throws Exception {
        ApiResponse apiResponse;
        List<DocItem> listItems;
        headers.put("content-type", "application/json");

        // valid json content
        apiResponse = new ApiResponse(RESPONSE_CODE, "", JSON_VALID, headers);

        machine.beginDoctest(FILE_NAME, INTRODUCTION);
        machine.sayResponse(apiResponse, headersToShow);
        machine.endDocTest();

        verify(renderer).render(listItemCaptor.capture(), eq(FILE_NAME), eq(INTRODUCTION));

        listItems = listItemCaptor.getValue();
        assertEquals(1, listItems.size());
        assertTrue(listItems.get(0) instanceof ResponseDocItem);
        assertEquals(PRETTY_JSON, ((ResponseDocItem) listItems.get(0)).getPayload().getExpected());

        // invalid json content
        String noJson = "{}fghkd{]}[";
        apiResponse = new ApiResponse(RESPONSE_CODE, "", noJson, headers);

        when(jsonHelper.prettyPrintJson(noJson)).thenReturn(noJson);

        machine.beginDoctest(FILE_NAME, INTRODUCTION);
        machine.sayResponse(apiResponse, headersToShow);
        machine.endDocTest();

        verify(renderer, times(2)).render(listItemCaptor.capture(), eq(FILE_NAME), eq(INTRODUCTION));

        listItems = listItemCaptor.getValue();
        assertEquals(2, listItems.size());
        assertTrue(listItems.get(1) instanceof ResponseDocItem);
        assertEquals(noJson, ((ResponseDocItem) listItems.get(1)).getPayload().getExpected());
    }

    @Test
    public void addRequestItemWithValidJsonContent() throws Exception {
        machine.beginDoctest(FILE_NAME, INTRODUCTION);
        machine.sayRequest(apiRequest, JSON_VALID, headersToShow, cookiesToShow);
        machine.endDocTest();

        verify(renderer).render(listItemCaptor.capture(), eq(FILE_NAME), eq(INTRODUCTION));

        List<DocItem> listItems = listItemCaptor.getValue();
        assertEquals(1, listItems.size());
        assertTrue(listItems.get(0) instanceof RequestDocItem);
        assertEquals(PRETTY_JSON, ((RequestDocItem) listItems.get(0)).getPayload().getExpected());
        assertEquals(HTTP_METHOD.toUpperCase(), ((RequestDocItem) listItems.get(0)).getHttp());
        assertEquals(uriString, ((RequestDocItem) listItems.get(0)).getUri());
        assertEquals(filteredCookies, ((RequestDocItem) listItems.get(0)).getCookies());
        assertEquals(filteredHeaders, ((RequestDocItem) listItems.get(0)).getHeaders());
    }

    @Test
    public void addRequestItemWithContentTypeJson() throws Exception {
        ApiRequest apiRequest;
        List<DocItem> listItems;
        headers.put("content-type", "application/json");

        // valid json content
        apiRequest = new ApiRequest(uri, HTTP_METHOD, headers, cookies);

        machine.beginDoctest(FILE_NAME, INTRODUCTION);
        machine.sayRequest(apiRequest, JSON_VALID, headersToShow, cookiesToShow);
        machine.endDocTest();

        verify(renderer).render(listItemCaptor.capture(), eq(FILE_NAME), eq(INTRODUCTION));

        listItems = listItemCaptor.getValue();
        assertEquals(1, listItems.size());
        assertTrue(listItems.get(0) instanceof RequestDocItem);
        assertEquals(PRETTY_JSON, ((RequestDocItem) listItems.get(0)).getPayload().getExpected());

        // invalid json content
        String noJson = "{}fghkd{]}[";
        apiRequest = new ApiRequest(uri, HTTP_METHOD, headers, cookies);

        when(jsonHelper.prettyPrintJson(noJson)).thenReturn(noJson);

        machine.beginDoctest(FILE_NAME, INTRODUCTION);
        machine.sayRequest(apiRequest, noJson, headersToShow, cookiesToShow);
        machine.endDocTest();

        verify(renderer, times(2)).render(listItemCaptor.capture(), eq(FILE_NAME), eq(INTRODUCTION));

        listItems = listItemCaptor.getValue();
        assertEquals(2, listItems.size());
        assertTrue(listItems.get(1) instanceof RequestDocItem);
        assertEquals(noJson, ((RequestDocItem) listItems.get(1)).getPayload().getExpected());
    }

    @Test
    public void addUploadRequestItem() throws Exception {
        machine.beginDoctest(FILE_NAME, INTRODUCTION);
        machine.sayUploadRequest(apiRequest, "file", "fileBody", 10l, "", headersToShow,
                cookiesToShow);
        machine.endDocTest();

        verify(renderer).render(listItemCaptor.capture(), eq(FILE_NAME), eq(INTRODUCTION));

        List<DocItem> listItems = listItemCaptor.getValue();
        assertEquals(1, listItems.size());
        assertTrue(listItems.get(0) instanceof RequestUploadDocItem);
        assertEquals(HTTP_METHOD.toUpperCase(), ((RequestUploadDocItem) listItems.get(0)).getHttp());
        assertEquals(uriString, ((RequestUploadDocItem) listItems.get(0)).getUri());
        assertEquals("file", ((RequestUploadDocItem) listItems.get(0)).getFileName());
        assertEquals("fileBody", ((RequestUploadDocItem) listItems.get(0)).getFileBody());
        assertEquals(filteredCookies, ((RequestDocItem) listItems.get(0)).getCookies());
        assertEquals(filteredHeaders, ((RequestDocItem) listItems.get(0)).getHeaders());
    }

    @Test
    public void addUploadRequestItemUriIsNull() throws Exception {
        apiRequest = new ApiRequest(null, HTTP_METHOD, headers, cookies);
        machine.beginDoctest(FILE_NAME, INTRODUCTION);
        machine.sayUploadRequest(apiRequest, "file", "fileBody", 10l, "", headersToShow,
                cookiesToShow);
        machine.endDocTest();

        verify(renderer).render(listItemCaptor.capture(), eq(FILE_NAME), eq(INTRODUCTION));

        List<DocItem> listItems = listItemCaptor.getValue();
        assertTrue(listItems.isEmpty());
    }

    @Test
    public void addRequestItemWihtoutUri() throws Exception {
        apiRequest = new ApiRequest(null, HTTP_METHOD, headers, cookies);

        machine.beginDoctest(FILE_NAME, INTRODUCTION);
        machine.sayRequest(apiRequest, JSON_VALID, headersToShow, cookiesToShow);
        machine.endDocTest();

        verify(renderer).render(listItemCaptor.capture(), eq(FILE_NAME), eq(INTRODUCTION));

        List<DocItem> listItems = listItemCaptor.getValue();
        assertTrue(listItems.isEmpty());
    }

    @Test
    public void addRequestItemWihtInvalidJson() throws Exception {
        machine.beginDoctest(FILE_NAME, INTRODUCTION);
        machine.sayRequest(apiRequest, JSON_INVALID, headersToShow, cookiesToShow);
        machine.endDocTest();

        verify(renderer).render(listItemCaptor.capture(), eq(FILE_NAME), eq(INTRODUCTION));

        List<DocItem> listItems = listItemCaptor.getValue();
        assertEquals(1, listItems.size());
        assertTrue(listItems.get(0) instanceof RequestDocItem);
        assertEquals(JSON_INVALID, ((RequestDocItem) listItems.get(0)).getPayload().getExpected());
        assertEquals(HTTP_METHOD.toUpperCase(), ((RequestDocItem) listItems.get(0)).getHttp());
        assertEquals(uriString, ((RequestDocItem) listItems.get(0)).getUri());
        assertEquals(filteredCookies, ((RequestDocItem) listItems.get(0)).getCookies());
        assertEquals(filteredHeaders, ((RequestDocItem) listItems.get(0)).getHeaders());
    }

    @Test
    public void addJsonDocItem() throws Exception {
        machine.beginDoctest(FILE_NAME, INTRODUCTION);
        machine.sayPreformatted(JSON_VALID);
        machine.endDocTest();

        verify(renderer).render(listItemCaptor.capture(), eq(FILE_NAME), eq(INTRODUCTION));

        List<DocItem> listItems = listItemCaptor.getValue();
        assertEquals(1, listItems.size());
        assertTrue(listItems.get(0) instanceof JsonDocItem);
        assertEquals(JSON_VALID, ((JsonDocItem) listItems.get(0)).getExpected());
    }

    @Test
    public void testAddSeveralItems() throws Exception {
        machine.beginDoctest(FILE_NAME, INTRODUCTION);
        machine.say(TEXT + "_first");
        machine.sayPreformatted(JSON_VALID);
        machine.say(TEXT + "_second");
        machine.sayRequest(apiRequest, JSON_VALID, headersToShow, cookiesToShow);
        machine.sayResponse(apiResponse, headersToShow);
        machine.sayVerify(TEXT);
        machine.sayNextSectionTitle(TEXT);
        machine.endDocTest();

        verify(renderer).render(listItemCaptor.capture(), eq(FILE_NAME), eq(INTRODUCTION));
        List<DocItem> items = listItemCaptor.getValue();
        assertEquals(7, items.size());
        assertTrue(items.get(0) instanceof TextDocItem);
        assertEquals(TEXT + "_first", ((TextDocItem) items.get(0)).getText());
        assertTrue(items.get(1) instanceof JsonDocItem);
        assertTrue(items.get(2) instanceof TextDocItem);
        assertEquals(TEXT + "_second", ((TextDocItem) items.get(2)).getText());
        assertTrue(items.get(3) instanceof RequestDocItem);
        assertTrue(items.get(4) instanceof ResponseDocItem);
        assertTrue(items.get(5) instanceof AssertDocItem);
        assertTrue(items.get(6) instanceof SectionDocItem);
    }

    @Test
    public void testNameOfTheReport() throws Exception {
        machine.beginDoctest(FILE_NAME, INTRODUCTION);
        machine.beginDoctest("blabla", INTRODUCTION);
        machine.endDocTest();

        verify(renderer).render(listItemCaptor.capture(), eq(FILE_NAME), eq(INTRODUCTION));
    }

    @Test
    public void setIntroduction() throws Exception {
        machine.beginDoctest(FILE_NAME, "introduction");
        machine.say("say-1");
        machine.say("say-2");
        machine.endDocTest();

        verify(renderer).render(listItemCaptor.capture(), eq(FILE_NAME), eq("introduction"));

        List<DocItem> listItems = listItemCaptor.getValue();
        assertEquals(2, listItems.size());
        assertTrue(listItems.get(0) instanceof TextDocItem);
        assertEquals("say-1", ((TextDocItem) listItems.get(0)).getText());
        assertTrue(listItems.get(1) instanceof TextDocItem);
        assertEquals("say-2", ((TextDocItem) listItems.get(1)).getText());
    }

    private void initHeadersAndCookies() {
        headersToShow = new ArrayList<String>();
        headersToShow.add("header_1");
        headers = new HashMap<String, String>();
        headers.put("header_1", "header_value_1");
        headers.put("header_2", "header_value_2");
        filteredHeaders = new HashMap<String, String>();
        filteredHeaders.put("header_1", "header_value_1");

        cookiesToShow = new ArrayList<String>();
        cookiesToShow.add("cookie_1");
        cookies = new HashMap<String, String>();
        cookies.put("cookie_1", "cookie_value_1");
        cookies.put("cookie_2", "cookie_value_2");
        filteredCookies = new HashMap<String, String>();
        filteredCookies.put("cookie_1", "cookie_value_1");

        when(headersHelper.filterMap(headers, headersToShow)).thenReturn(filteredHeaders);
        when(headersHelper.filterMap(cookies, cookiesToShow)).thenReturn(filteredCookies);
    }
}
