package com.devbliss.doctest.renderer.html;

import java.io.File;
import java.util.List;

import com.devbliss.doctest.items.DocItem;
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
        String finalHeader = htmlItems.getHeaderFormatTemplate(INDEX);
        String indexFileWithCompletePath = helper.getCompleteFileName(INDEX, HTML_EXTENSION);

        String hrefs = getListOfFileAsString(indexFileWithCompletePath, INDEX);

        String body = htmlItems.getLiMenuTemplate("List of test cases", hrefs);;
        String finalDoc = finalHeader + htmlItems.getBodyTemplate(body);

        finalDoc = htmlItems.getHtmlTemplate(finalDoc);

        helper.writeFile(indexFileWithCompletePath, finalDoc);
    }

    private String getListOfFileAsString(String nameWithCompletePath, String shortName) {
        File[] files = fetchFilesInDirectory(nameWithCompletePath);
        StringBuffer hrefs = new StringBuffer();

        // fetch neither the file itself nor the hidden files
        for (File file : files) {
            if (!file.getName().equals(shortName + HTML_EXTENSION)) {
                if (!file.isHidden()) {
                    hrefs.append(htmlItems.getLiWithLinkTemplate(file.getName(), file.getName()));
                }
            }

        }
        return hrefs.toString();
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
