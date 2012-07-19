package com.devbliss.doctest.renderer.html;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.devbliss.doctest.items.DocItem;
import com.devbliss.doctest.items.FileDocItem;
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

    private final FileHelper helper;

    @Inject
    public HtmlIndexFileRenderer(HtmlItems htmlItems, FileHelper abstractReportRenderer) {
        super(htmlItems);
        this.helper = abstractReportRenderer;
    }

    public void render(List<DocItem> listItems, String name) throws Exception {
        String nameWithExtension = helper.getCompleteFileName(INDEX, HTML_EXTENSION);
        List<LinkDocItem> files = getListOfFileAsString(nameWithExtension, INDEX);
        MenuDocItem menu = new MenuDocItem("List of doctest files", files);
        String body = htmlItems.getListFilesTemplate(menu);
        FileDocItem index = new FileDocItem(name, body);
        helper.writeFile(nameWithExtension, htmlItems.getIndexTemplate(index));
    }

    private List<LinkDocItem> getListOfFileAsString(String nameWithCompletePath, String shortName) {
        File[] files = fetchFilesInDirectory(nameWithCompletePath);
        List<LinkDocItem> list = new ArrayList<LinkDocItem>();
        // fetch neither the file itself nor the hidden files
        for (File file : files) {
            if (!file.getName().equals(shortName + HTML_EXTENSION)) {
                if (!file.isHidden()) {
                    list.add(new LinkDocItem(file.getName(), file.getName()));
                }
            }

        }
        return list;
    }

    /**
     * Fetch all the files of the doctests directory. Each file corresponds to a test case.
     * 
     * @param indexFileWithCompletePath
     * @return
     */
    private File[] fetchFilesInDirectory(String indexFileWithCompletePath) {
        return new File(indexFileWithCompletePath).getParentFile().listFiles();
    }

}
