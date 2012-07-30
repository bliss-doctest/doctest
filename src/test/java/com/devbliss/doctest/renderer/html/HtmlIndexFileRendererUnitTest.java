package com.devbliss.doctest.renderer.html;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.devbliss.doctest.items.DocItem;
import com.devbliss.doctest.items.IndexFileDocItem;
import com.devbliss.doctest.items.LinkDocItem;
import com.devbliss.doctest.items.MenuDocItem;
import com.devbliss.doctest.utils.FileHelper;
import com.devbliss.doctest.utils.FileListHelper;

/**
 * Unit tests for the {@link HtmlIndexFileRenderer}
 * 
 * @author mbankmann
 * 
 */
@RunWith(MockitoJUnitRunner.class)
public class HtmlIndexFileRendererUnitTest {

    private static final String NAME = "index";
    private static final String COMPLETE_NAME = "complete/name";

    @Mock
    private HtmlItems htmlItems;
    @Mock
    private FileHelper helper;
    @Mock
    private FileListHelper fileListHelper;

    HtmlIndexFileRenderer renderer;

    @Before
    public void setUp() {
        when(helper.getCompleteFileName(NAME, ".html")).thenReturn(COMPLETE_NAME);
        renderer = new HtmlIndexFileRenderer(htmlItems, helper, fileListHelper);
    }

    @Test
    public void testRender() throws Exception {
        List<DocItem> listTemplates = new ArrayList<DocItem>();
        when(fileListHelper.getListOfFileAsString(anyString(), anyString(), anyString()))
                .thenReturn(new ArrayList<LinkDocItem>());
        renderer.render(listTemplates, "");
        verify(htmlItems).getListFilesTemplate(any(MenuDocItem.class));
        verify(htmlItems).getIndexTemplate(any(IndexFileDocItem.class));
        verify(helper).writeFile(eq(COMPLETE_NAME), anyString());
    }
}
