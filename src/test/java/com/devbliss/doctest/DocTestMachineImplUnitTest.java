package com.devbliss.doctest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.devbliss.doctest.templates.Assert;
import com.devbliss.doctest.templates.Item;
import com.devbliss.doctest.templates.Request;
import com.devbliss.doctest.templates.Response;
import com.devbliss.doctest.templates.Section;
import com.devbliss.doctest.templates.Templates;
import com.devbliss.doctest.templates.Text;
import com.devbliss.doctest.utils.JSONHelper;

import de.devbliss.apitester.ApiTest.HTTP_REQUEST;

/**
 * Unit test for {@link DocTestMachineImpl}
 * 
 * @author bmary
 * 
 */
@RunWith(MockitoJUnitRunner.class)
public class DocTestMachineImplUnitTest {

    private static final String CLASS_NAME = "className";
    private static final String TEXT = "text";
    private static final int RESPONSE_CODE = 130;
    private static final String JSON_VALID = "{'abc':'a'}";

    @Mock
    private Templates templates;
    @Mock
    private ReportRenderer renderer;
    @Mock
    private JSONHelper jsonHelper;
    @Captor
    private ArgumentCaptor<List<Item>> listItemCaptor;

    private DocTestMachineImpl machine;
    private final HTTP_REQUEST httpRequest = HTTP_REQUEST.GET;
    private URI uri;

    @Before
    public void setUp() throws URISyntaxException {
        when(jsonHelper.isJsonValid(JSON_VALID)).thenReturn(true);
        uri = new URI("");
        machine = new DocTestMachineImpl(templates, renderer, jsonHelper);
        machine.beginDoctest(CLASS_NAME);
    }

    @Test
    public void addTextItem() {
        machine.say(TEXT);
        machine.endDocTest();

        verify(renderer).render(listItemCaptor.capture(), eq(CLASS_NAME));

        List<Item> listItems = listItemCaptor.getValue();
        assertEquals(1, listItems.size());
        assertTrue(listItems.get(0) instanceof Text);
        assertEquals(TEXT, ((Text) listItems.get(0)).text);
    }

    @Test
    public void addSectionItem() {
        machine.sayNextSection(TEXT);
        machine.endDocTest();

        verify(renderer).render(listItemCaptor.capture(), eq(CLASS_NAME));

        List<Item> listItems = listItemCaptor.getValue();
        assertEquals(1, listItems.size());
        assertTrue(listItems.get(0) instanceof Section);
        assertEquals(TEXT, ((Section) listItems.get(0)).title);
    }

    @Test
    public void addAssertItem() {
        machine.sayVerify(TEXT);
        machine.endDocTest();

        verify(renderer).render(listItemCaptor.capture(), eq(CLASS_NAME));

        List<Item> listItems = listItemCaptor.getValue();
        assertEquals(1, listItems.size());
        assertTrue(listItems.get(0) instanceof Assert);
        assertEquals(TEXT, ((Assert) listItems.get(0)).expected);
    }

    @Test
    public void addResponseItem() throws Exception {
        machine.sayResponse(RESPONSE_CODE, JSON_VALID);
        machine.endDocTest();

        verify(renderer).render(listItemCaptor.capture(), eq(CLASS_NAME));

        List<Item> listItems = listItemCaptor.getValue();
        assertEquals(1, listItems.size());
        assertTrue(listItems.get(0) instanceof Response);
        assertEquals(JSON_VALID, ((Response) listItems.get(0)).payload);
        assertEquals(RESPONSE_CODE, ((Response) listItems.get(0)).responseCode);
    }

    @Test
    public void addRequestItem() throws Exception {
        machine.sayRequest(uri, JSON_VALID, httpRequest);
        machine.endDocTest();

        verify(renderer).render(listItemCaptor.capture(), eq(CLASS_NAME));

        List<Item> listItems = listItemCaptor.getValue();
        assertEquals(1, listItems.size());
        assertTrue(listItems.get(0) instanceof Request);
        assertEquals(JSON_VALID, ((Request) listItems.get(0)).payload);
        assertEquals(httpRequest, ((Request) listItems.get(0)).http);
        assertEquals(uri.toString(), ((Request) listItems.get(0)).uri);
    }

}
