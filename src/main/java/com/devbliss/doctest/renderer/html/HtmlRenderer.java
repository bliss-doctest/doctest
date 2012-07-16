package com.devbliss.doctest.renderer.html;

import java.util.Date;
import java.util.List;

import com.devbliss.doctest.renderer.AbstractReportRenderer;
import com.devbliss.doctest.renderer.ReportRenderer;
import com.devbliss.doctest.templates.AssertDocItem;
import com.devbliss.doctest.templates.DocItem;
import com.devbliss.doctest.templates.JsonDocItem;
import com.devbliss.doctest.templates.RequestDocItem;
import com.devbliss.doctest.templates.ResponseDocItem;
import com.devbliss.doctest.templates.SectionDocItem;
import com.devbliss.doctest.templates.TextDocItem;
import com.google.inject.Inject;

/**
 * Html-implementation of the {@link ReportRenderer}.
 * 
 * @author bmary
 * 
 */
public class HtmlRenderer extends AbstractReportRenderer {

    public static String htmlFormat =
            "<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01//EN\" \"http://www.w3.org/TR/html4/strict.dtd\">"
                    + "<html>%s</html>";
    public static String headerFormat = "<head><style>" + HtmlStyle.getCss() + "</style>"
            + "<title>DocTest for class %s</title></head>";
    public static String bodyFormat = "<body><div class=\"container\">"
            + "<div class=\"wrapper\"><a href=\"index.html\">back to index page</a>" + "<br/>"
            + "%s" + "</div></div><body>";

    public String h1 = "<h1>%s</h1>";
    public String simpleLine = "%s<br/>";
    private final IndexFileGenerator indexFileGenerator;
    private final HtmlItems templates;

    @Inject
    public HtmlRenderer(IndexFileGenerator indexFileGenerator, HtmlItems templates) {
        this.indexFileGenerator = indexFileGenerator;
        this.templates = templates;
    }

    public void render(List<DocItem> listTemplates, String name) {

        String finalHeader = headerFormat.replace("%s", name);

        StringBuffer buffer = new StringBuffer();
        buffer.append("Doctest originally perfomed at: " + new Date());
        appendItemsToBuffer(listTemplates, buffer);

        String finalBody = String.format(bodyFormat, buffer.toString());

        // this will be not SaySysoutImpl, but the name of the Unit test :)
        String finalDocument = finalHeader + finalBody;
        finalDocument = String.format(htmlFormat, finalDocument);

        String fileNameForCompleteTestOutput = getCompleteFileName(name, ".html");

        createTheDirectory(fileNameForCompleteTestOutput);
        writeOutFile(fileNameForCompleteTestOutput, finalDocument);

        indexFileGenerator.generatIndexFileForTests();
    }

    private void appendItemsToBuffer(List<DocItem> listTemplates, StringBuffer buffer) {
        for (DocItem myitem : listTemplates) {
            if (myitem instanceof AssertDocItem) {
                buffer.append(templates.getVerifyTemplate(((AssertDocItem) myitem).expected));
            } else if (myitem instanceof RequestDocItem) {
                buffer.append(templates.getUriTemplate(((RequestDocItem) myitem).uri, templates
                        .getJsonTemplate(((RequestDocItem) myitem).payload),
                        ((RequestDocItem) myitem).http));
            } else if (myitem instanceof ResponseDocItem) {
                buffer.append(templates.getResponseTemplate(
                        ((ResponseDocItem) myitem).responseCode, templates
                                .getJsonTemplate(((ResponseDocItem) myitem).payload)));
            } else if (myitem instanceof SectionDocItem) {
                buffer.append(templates.getSectionTemplate(((SectionDocItem) myitem).title));
            } else if (myitem instanceof TextDocItem) {
                buffer.append(((TextDocItem) myitem).text);
            } else if (myitem instanceof JsonDocItem) {
                buffer.append(templates.getJsonTemplate(((JsonDocItem) myitem).expected));
            }
        }
    }
}
