package com.devbliss.doctest.renderer.html;

import java.util.List;

import com.devbliss.doctest.items.DocItem;
import com.devbliss.doctest.items.IndexFileDocItem;
import com.devbliss.doctest.items.LinkDocItem;
import com.devbliss.doctest.items.MenuDocItem;
import com.devbliss.doctest.utils.FileHelper;
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

    private final FileHelper fileHelper;

    @Inject
    public HtmlIndexFileRenderer(HtmlItems htmlItems, FileHelper fileHelper) {
        super(htmlItems);
        this.fileHelper = fileHelper;
    }

    public void render(List<DocItem> listItems, String name, String introduction) throws Exception {
        String nameWithExtension = fileHelper.getCompleteFileName(INDEX, HTML_EXTENSION);
        List<LinkDocItem> files = fileHelper.getListOfFile(nameWithExtension);
        MenuDocItem menu = new MenuDocItem("List of doctest files", files);
        String body = htmlItems.getListFilesTemplate(menu);
        IndexFileDocItem index = new IndexFileDocItem(name, body);
        fileHelper.writeFile(nameWithExtension, htmlItems.getIndexTemplate(index));
    }
}
