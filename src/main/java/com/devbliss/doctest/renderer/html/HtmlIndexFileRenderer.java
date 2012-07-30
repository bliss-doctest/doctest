package com.devbliss.doctest.renderer.html;

import java.util.List;

import com.devbliss.doctest.items.DocItem;
import com.devbliss.doctest.items.IndexFileDocItem;
import com.devbliss.doctest.items.LinkDocItem;
import com.devbliss.doctest.items.MenuDocItem;
import com.devbliss.doctest.utils.FileHelper;
import com.devbliss.doctest.utils.FileListHelper;
import com.google.inject.Inject;

/**
 * Simply gets all files from the doctests directory and lists them in a
 * index.html.
 * 
 * This approach is really stupid, but the cool thing is that it works without
 * using a TestRunner or Testsuite. Zero conf and hazzle free.
 * 
 * Means each test driven by DocTestMachineImpl generates a new index.html. But
 * this should be cheap in terms of time consumed.
 * 
 * @author rbauer, bmary
 * 
 */
public class HtmlIndexFileRenderer extends AbstractHtmlReportRenderer {

    private final FileHelper helper;
    private final FileListHelper fileListHelper;

    @Inject
    public HtmlIndexFileRenderer(
            HtmlItems htmlItems,
            FileHelper abstractReportRenderer,
            FileListHelper fileListHelper) {
        super(htmlItems);
        this.helper = abstractReportRenderer;
        this.fileListHelper = fileListHelper;
    }

    public void render(List<DocItem> listItems, String name) throws Exception {
        String nameWithExtension = helper.getCompleteFileName(INDEX, HTML_EXTENSION);
        List<LinkDocItem> files =
                fileListHelper.getListOfFileAsString(nameWithExtension, INDEX, HTML_EXTENSION);
        MenuDocItem menu = new MenuDocItem("List of doctest files", files);
        String body = htmlItems.getListFilesTemplate(menu);
        IndexFileDocItem index = new IndexFileDocItem(name, body);
        helper.writeFile(nameWithExtension, htmlItems.getIndexTemplate(index));
    }
}
