package com.devbliss.doctest.renderer.html;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
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
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.devbliss.doctest.items.AssertDocItem;
import com.devbliss.doctest.items.DocItem;
import com.devbliss.doctest.items.IndexFileDocItem;
import com.devbliss.doctest.items.JsonDocItem;
import com.devbliss.doctest.items.LinkDocItem;
import com.devbliss.doctest.items.MenuDocItem;
import com.devbliss.doctest.items.ReportFileDocItem;
import com.devbliss.doctest.items.RequestDocItem;
import com.devbliss.doctest.items.ResponseDocItem;
import com.devbliss.doctest.items.SectionDocItem;
import com.devbliss.doctest.utils.FileHelper;

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
    private static final String HTML = "html";
    private static final String SECTION_TITLE = "title";
    private static final String JSON_PAYLOAD = "json_payload";
    private static final String REQUEST = "request";
    private static final String RESPONSE = "response";
    private static final String ASSERT = "assert";

    @Mock
    private HtmlItems htmlItems;
    @Mock
    private HtmlIndexFileRenderer indexFileGenerator;
    @Mock
    private FileHelper helper;

    @Mock
    private AssertDocItem assertDocItem;
    @Mock
    private JsonDocItem jsonDocItem;
    @Mock
    private ResponseDocItem responseDocItem;
    @Mock
    private RequestDocItem requestDocItem;

    @Mock
    private SectionDocItem section1;
    @Mock
    private SectionDocItem section2;
    @Mock
    private SectionDocItem section3;
    @Captor
    private ArgumentCaptor<ReportFileDocItem> fileCaptor;
    @Captor
    private ArgumentCaptor<MenuDocItem> menuCaptor;

    private HtmlRenderer renderer;
    private List<DocItem> listTemplates;

    @Before
    public void setUp() {
        when(htmlItems.getReportFileTemplate(any(ReportFileDocItem.class))).thenReturn(HTML);
        when(htmlItems.getIndexTemplate(any(IndexFileDocItem.class))).thenReturn(HTML);

        when(htmlItems.getTemplateForItem(requestDocItem)).thenReturn(REQUEST);
        when(htmlItems.getTemplateForItem(responseDocItem)).thenReturn(RESPONSE);
        when(htmlItems.getTemplateForItem(assertDocItem)).thenReturn(ASSERT);
        when(htmlItems.getTemplateForItem(jsonDocItem)).thenReturn(JSON_PAYLOAD);

        when(section1.getTitle()).thenReturn(SECTION_TITLE + "1");
        when(section2.getTitle()).thenReturn(SECTION_TITLE + "2");
        when(section3.getTitle()).thenReturn(SECTION_TITLE + "3");
        when(helper.getCompleteFileName(NAME, ".html")).thenReturn(COMPLETE_NAME);

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
        // there is no section
        when(htmlItems.getListFilesTemplate(any(MenuDocItem.class))).thenReturn("");
        listTemplates.add(requestDocItem);
        listTemplates.add(responseDocItem);
        listTemplates.add(assertDocItem);
        renderer.render(listTemplates, NAME);
        verify(htmlItems).getReportFileTemplate(fileCaptor.capture());
        ReportFileDocItem docItem = fileCaptor.getValue();
        assertEquals(REQUEST + RESPONSE + ASSERT, docItem.getItems());
        verifyFilesAreCreated();
    }

    @Test
    public void renderSections() throws Exception {
        listTemplates.add(section1);
        listTemplates.add(section2);
        listTemplates.add(section3);

        renderer.render(listTemplates, NAME);
        verify(htmlItems).getTemplateForItem(section1);
        verify(htmlItems).getTemplateForItem(section2);
        verify(htmlItems).getTemplateForItem(section3);

        verify(section1).setHref("section1");
        verify(section2).setHref("section2");
        verify(section3).setHref("section3");

        verify(htmlItems).getListFilesTemplate(menuCaptor.capture());
        List<LinkDocItem> menuFiles = menuCaptor.getValue().getFiles();

        verifyLinkDocItem("#section1", SECTION_TITLE + "1", menuFiles.get(0));
        verifyLinkDocItem("#section2", SECTION_TITLE + "2", menuFiles.get(1));
        verifyLinkDocItem("#section3", SECTION_TITLE + "3", menuFiles.get(2));
    }

    @Test
    public void renderUnorderedSections() throws Exception {
        listTemplates.add(section2);
        listTemplates.add(section3);
        listTemplates.add(section1);

        renderer.render(listTemplates, NAME);
        verify(htmlItems).getTemplateForItem(section1);
        verify(htmlItems).getTemplateForItem(section2);
        verify(htmlItems).getTemplateForItem(section3);

        verify(section1).setHref("section3");
        verify(section2).setHref("section1");
        verify(section3).setHref("section2");

        verify(htmlItems).getListFilesTemplate(menuCaptor.capture());
        List<LinkDocItem> menuFiles = menuCaptor.getValue().getFiles();

        verifyLinkDocItem("#section1", SECTION_TITLE + "2", menuFiles.get(0));
        verifyLinkDocItem("#section2", SECTION_TITLE + "3", menuFiles.get(1));
        verifyLinkDocItem("#section3", SECTION_TITLE + "1", menuFiles.get(2));
    }

    @Test
    public void renderRequest() throws Exception {
        listTemplates.add(requestDocItem);
        renderer.render(listTemplates, NAME);
        verify(htmlItems).getTemplateForItem(requestDocItem);
        verifyFilesAreCreated();
    }

    @Test
    public void renderResponse() throws Exception {
        listTemplates.add(responseDocItem);
        renderer.render(listTemplates, NAME);
        verify(htmlItems).getTemplateForItem(responseDocItem);
        verifyFilesAreCreated();
    }

    @Test
    public void renderAssert() throws Exception {
        listTemplates.add(assertDocItem);
        renderer.render(listTemplates, NAME);
        verify(htmlItems).getTemplateForItem(assertDocItem);
        verifyFilesAreCreated();
    }

    private void verifyFilesAreCreated() throws Exception {
        verify(helper).writeFile(COMPLETE_NAME, HTML);
        verify(indexFileGenerator).render(null, "index");
    }

    private void verifyLinkDocItem(String href, String name, LinkDocItem item) {
        assertEquals(href, item.getHref());
        assertEquals(name, item.getName());
    }
}
