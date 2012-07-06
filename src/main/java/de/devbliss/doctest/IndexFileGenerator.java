package de.devbliss.doctest;


import java.io.File;

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
 * @author rbauer
 * 
 */
public class IndexFileGenerator {

    /**
     * The html page.
     */
    public static String fullPageFormat = "<html><head><title>Doctest result</title></head><body>%s</body></html>";

    public static String br = "<br/>";

    public static String anchorFormat = "<a href=\"%s\">%s</a>";

    /**
     * Default name of the index file.
     */
    public final static String INDEX_FILE = "index.html";

    public static void generatIndexFileForTests() {

        String indexFileWithCompletePath = DocTestMachineImpl.OUTPUT_DIRECTORY
                + INDEX_FILE;

        new File(indexFileWithCompletePath).getParentFile().mkdirs();

        // fetch all files in the directory
        // => these are the tests.
        File[] files = new File(indexFileWithCompletePath).getParentFile()
                .listFiles();

        StringBuffer hrefs = new StringBuffer();

        for (File file : files) {
            // don't fetch the index file itself:
            if (!file.getName().equals(INDEX_FILE)) {

                // and don't fetch hidden files (eg. .DS_STORE on Mac)
                if (!file.isHidden()) {
                    // if the file is okay simply generate an anchor and add
                    // it to the list of other testcases.
                    hrefs.append(String.format(anchorFormat, file.getName(),
                            file.getName() + br));
                }
            }

        }

        // format the final html
        String finalDoc = String.format(fullPageFormat, hrefs.toString());

        // and write it out...
        DocTestMachineImpl.writeOutFile(indexFileWithCompletePath, finalDoc);

    }

}
