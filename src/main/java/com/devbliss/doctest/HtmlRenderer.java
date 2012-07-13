package com.devbliss.doctest;

import java.io.File;
import java.util.List;

import com.devbliss.doctest.templates.Item;
import com.google.inject.Inject;

public class HtmlRenderer extends AbstractReportRenderer {

    public String htmlFormat =
            "<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01//EN\" \"http://www.w3.org/TR/html4/strict.dtd\">"
                    + "<html>%s</html>";
    public String headerFormat = "<head><title>DocTest for class %s</title></head>";
    public String bodyFormat = "<body><a href=\"index.html\">back to index page</a><br/>%s<body>";

    public String h1 = "<h1>%s</h1>";
    public String simpleLine = "%s<br/>";
    private final IndexFileGenerator indexFileGenerator;

    @Inject
    public HtmlRenderer(IndexFileGenerator indexFileGenerator) {
        this.indexFileGenerator = indexFileGenerator;
    }

    public void render(List<Item> listTemplates, String name) {
        // this will be not SaySysoutImpl, but the name of the Unit test :)
        String finalHeader = String.format(headerFormat, name);
        String finalBody = String.format(bodyFormat, "outputOfTestsBuffer.toString()");

        String finalDocument = finalHeader + finalBody;

        String fileNameForCompleteTestOutput = OUTPUT_DIRECTORY + name + ".html";

        // make sure the directories have been generated:
        new File(fileNameForCompleteTestOutput).getParentFile().mkdirs();

        writeOutFile(fileNameForCompleteTestOutput, finalDocument);

        indexFileGenerator.generatIndexFileForTests();
    }
}
