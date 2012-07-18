package com.devbliss.doctest.renderer.html;

import static org.mockito.Matchers.anyList;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.runners.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;

import com.devbliss.doctest.items.AssertDocItem;
import com.devbliss.doctest.items.DocItem;
import com.devbliss.doctest.items.RequestDocItem;
import com.devbliss.doctest.items.ResponseDocItem;
import com.devbliss.doctest.items.SectionDocItem;
import com.devbliss.doctest.utils.FileHelper;
import com.devbliss.doctest.utils.InvalidReportException;

import de.devbliss.apitester.ApiTest.HTTP_REQUEST;

/**
 * Unit tests for the {@link HtmlRenderer}
 * 
 * @author bmary
 * 
 */
@RunWith(MockitoJUnitRunner.class)
public class HtmlRendererUnitTest {

    private static final String NAME = "name";
    private static final String COMPLETE_NAME = "complete/name";
    private static final String HEADER = "header";
    private static final String BODY = "body";
    private static final String HTML = "html";
    private static final String SECTION_TITLE = "title";
    private static final String PAYLOAD = "payload";
    private static final String JSON_PAYLOAD = "json_payload";
    private static final String URI = "uri";
    private static final String CONDITION = "condition";
    private static final int RESPONSE_CODE = 0;

    @Mock
    private HtmlItems htmlItems;
    @Mock
    private IndexFileGenerator indexFileGenerator;
    @Mock
    private FileHelper helper;
    @Mock
    private DocItem docItem;

    private HtmlRenderer renderer;
    private List<DocItem> listTemplates;

    @Before
    public void setUp() {
        when(helper.getCompleteFileName(NAME, ".html")).thenReturn(COMPLETE_NAME);
        when(htmlItems.getHeaderFormatTemplate(NAME)).thenReturn(HEADER);
        when(htmlItems.getBodyTemplate(anyString())).thenReturn(BODY);
        when(htmlItems.getHtmlTemplate(HEADER + BODY)).thenReturn(HTML);
        when(htmlItems.getJsonTemplate(PAYLOAD)).thenReturn(JSON_PAYLOAD);
        when(htmlItems.getLiWithLinkTemplate(anyString(), anyString())).thenAnswer(
                new Answer<String>() {

                    public String answer(InvocationOnMock invocation) throws Throwable {
                        return (String) invocation.getArguments()[1];
                    }
                });
        renderer = new HtmlRenderer(indexFileGenerator, htmlItems, helper);
        listTemplates = new ArrayList<DocItem>();
    }

    @Test
    @SuppressWarnings("unchecked")
    public void doNotCreateIndexIfListIsEmpty() throws Exception {
        renderer.render(listTemplates, NAME);
        verify(indexFileGenerator, never()).render(anyList(), anyString());
    }

    @Test
    @SuppressWarnings("unchecked")
    public void doNotCreateIndexIfListIsNull() throws Exception {
        renderer.render(null, NAME);
        verify(indexFileGenerator, never()).render(anyList(), anyString());
    }

    @Test
    public void renderListDocItems() throws Exception {
        listTemplates.add(docItem);
        listTemplates.add(docItem);
        listTemplates.add(docItem);
        renderer.render(listTemplates, NAME);
        verifyFilesAreCreated();
    }

    @Test
    public void renderSections() throws Exception {
        listTemplates.add(new SectionDocItem(SECTION_TITLE + "1"));
        listTemplates.add(new SectionDocItem(SECTION_TITLE + "2"));
        listTemplates.add(new SectionDocItem(SECTION_TITLE + "3"));
        renderer.render(listTemplates, NAME);
        verify(htmlItems).getSectionTemplate(SECTION_TITLE + "1", "section1");
        verify(htmlItems).getSectionTemplate(SECTION_TITLE + "2", "section2");
        verify(htmlItems).getSectionTemplate(SECTION_TITLE + "3", "section3");

        verify(htmlItems).getLiMenuTemplate("Sections of this test class",
                SECTION_TITLE + "1" + SECTION_TITLE + "2" + SECTION_TITLE + "3");
    }

    @Test
    public void renderUnorderedSections() throws Exception {
        listTemplates.add(new SectionDocItem(SECTION_TITLE + "2"));
        listTemplates.add(new SectionDocItem(SECTION_TITLE + "3"));
        listTemplates.add(new SectionDocItem(SECTION_TITLE + "1"));
        renderer.render(listTemplates, NAME);
        verify(htmlItems).getSectionTemplate(SECTION_TITLE + "2", "section1");
        verify(htmlItems).getSectionTemplate(SECTION_TITLE + "3", "section2");
        verify(htmlItems).getSectionTemplate(SECTION_TITLE + "1", "section3");

        verify(htmlItems).getLiMenuTemplate("Sections of this test class",
                SECTION_TITLE + "2" + SECTION_TITLE + "3" + SECTION_TITLE + "1");
    }

    @Test
    public void renderRequest() throws Exception {
        listTemplates.add(new RequestDocItem(HTTP_REQUEST.GET, URI, PAYLOAD));
        renderer.render(listTemplates, NAME);
        verify(htmlItems).getUriTemplate(URI, JSON_PAYLOAD, HTTP_REQUEST.GET);
        verifyFilesAreCreated();
    }

    @Test
    public void renderResponse() throws Exception {
        listTemplates.add(new ResponseDocItem(RESPONSE_CODE, PAYLOAD));
        renderer.render(listTemplates, NAME);
        verify(htmlItems).getResponseTemplate(RESPONSE_CODE, JSON_PAYLOAD);
        verifyFilesAreCreated();
    }

    @Test
    public void renderAssert() throws Exception {
        listTemplates.add(new AssertDocItem(CONDITION));
        renderer.render(listTemplates, NAME);
        verify(htmlItems).getVerifyTemplate(CONDITION);
        verifyFilesAreCreated();
    }

    private void verifyFilesAreCreated() throws InvalidReportException, Exception {
        verify(helper).writeFile(COMPLETE_NAME, HTML);
        verify(indexFileGenerator).render(null, "index");
    }
}
