package com.devbliss.doctest.machine;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

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
import com.devbliss.doctest.utils.HeadersHelper;
import com.devbliss.doctest.utils.JSONHelper;
import com.devbliss.doctest.utils.UriHelper;

import de.devbliss.apitester.ApiRequest;
import de.devbliss.apitester.ApiResponse;
import de.devbliss.apitester.ApiTest.HTTP_REQUEST;

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

    @Mock
    private ReportRenderer renderer;
    @Mock
    private JSONHelper jsonHelper;
    @Mock
    private List<DocItem> listItem;
    @Mock
    private UriHelper uriHelper;
    @Mock
    private HeadersHelper headersHelper;
    @Mock
    private ApiRequest apiRequest;
    @Mock
    private ApiResponse apiResponse;

    @Captor
    private ArgumentCaptor<List<DocItem>> listItemCaptor;

    private DocTestMachineImpl machine;
    private final HTTP_REQUEST httpRequest = HTTP_REQUEST.GET;
    private String uriString;
    private URI uri;
    private List<String> headers;

    @Before
    public void setUp() throws URISyntaxException {
        uri = new URI("");
        headers = new ArrayList<String>();
        when(jsonHelper.isJsonValid(JSON_VALID)).thenReturn(true);
        when(jsonHelper.prettyPrintJson(JSON_VALID)).thenReturn(JSON_VALID);
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
    public void addResponseItem() throws Exception {
        machine.beginDoctest(FILE_NAME, INTRODUCTION);
        machine.sayResponse(apiResponse, headers);
        machine.endDocTest();

        verify(renderer).render(listItemCaptor.capture(), eq(FILE_NAME), eq(INTRODUCTION));

        List<DocItem> listItems = listItemCaptor.getValue();
        assertEquals(1, listItems.size());
        assertTrue(listItems.get(0) instanceof ResponseDocItem);
        assertEquals(JSON_VALID, ((ResponseDocItem) listItems.get(0)).getPayload().getExpected());
        assertEquals(RESPONSE_CODE, ((ResponseDocItem) listItems.get(0)).getResponseCode());
    }

    @Test
    public void addRequestItem() throws Exception {
        machine.beginDoctest(FILE_NAME, INTRODUCTION);
        machine.sayRequest(uri, JSON_VALID, httpRequest);
        machine.endDocTest();

        verify(renderer).render(listItemCaptor.capture(), eq(FILE_NAME), eq(INTRODUCTION));

        List<DocItem> listItems = listItemCaptor.getValue();
        assertEquals(1, listItems.size());
        assertTrue(listItems.get(0) instanceof RequestDocItem);
        assertEquals(JSON_VALID, ((RequestDocItem) listItems.get(0)).getPayload().getExpected());
        assertEquals(httpRequest, ((RequestDocItem) listItems.get(0)).getHttp());
        assertEquals(uriString, ((RequestDocItem) listItems.get(0)).getUri());
    }

    @Test
    public void addUploadRequestItem() throws Exception {
        machine.beginDoctest(FILE_NAME, INTRODUCTION);
        machine.sayUploadRequest(apiRequest, "file", "fileBody", 10l, "", headers);
        machine.endDocTest();

        verify(renderer).render(listItemCaptor.capture(), eq(FILE_NAME), eq(INTRODUCTION));

        List<DocItem> listItems = listItemCaptor.getValue();
        assertEquals(1, listItems.size());
        assertTrue(listItems.get(0) instanceof RequestUploadDocItem);
        assertEquals(httpRequest, ((RequestUploadDocItem) listItems.get(0)).getHttp());
        assertEquals(uriString, ((RequestUploadDocItem) listItems.get(0)).getUri());
        assertEquals("file", ((RequestUploadDocItem) listItems.get(0)).getFileName());
        assertEquals("fileBody", ((RequestUploadDocItem) listItems.get(0)).getFileBody());
    }

    @Test
    public void addUploadRequestItemUriIsNull() throws Exception {
        machine.beginDoctest(FILE_NAME, INTRODUCTION);
        machine.sayUploadRequest(apiRequest, "file", "fileBody", 10l, "", headers);
        machine.endDocTest();

        verify(renderer).render(listItemCaptor.capture(), eq(FILE_NAME), eq(INTRODUCTION));

        List<DocItem> listItems = listItemCaptor.getValue();
        assertTrue(listItems.isEmpty());
    }

    @Test
    public void addRequestItemWihtoutUri() throws Exception {
        machine.beginDoctest(FILE_NAME, INTRODUCTION);
        machine.sayRequest(null, JSON_VALID, httpRequest);
        machine.endDocTest();

        verify(renderer).render(listItemCaptor.capture(), eq(FILE_NAME), eq(INTRODUCTION));

        List<DocItem> listItems = listItemCaptor.getValue();
        assertTrue(listItems.isEmpty());
    }

    @Test
    public void addRequestItemWihtInvalidJson() throws Exception {
        machine.beginDoctest(FILE_NAME, INTRODUCTION);
        machine.sayRequest(uri, JSON_INVALID, httpRequest);
        machine.endDocTest();

        verify(renderer).render(listItemCaptor.capture(), eq(FILE_NAME), eq(INTRODUCTION));

        List<DocItem> listItems = listItemCaptor.getValue();
        assertEquals(1, listItems.size());
        assertTrue(listItems.get(0) instanceof RequestDocItem);
        assertEquals(JSON_INVALID, ((RequestDocItem) listItems.get(0)).getPayload().getExpected());
        assertEquals(httpRequest, ((RequestDocItem) listItems.get(0)).getHttp());
        assertEquals(uriString, ((RequestDocItem) listItems.get(0)).getUri());
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
        machine.sayRequest(uri, JSON_VALID, httpRequest);
        machine.sayResponse(apiResponse, headers);
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
}
