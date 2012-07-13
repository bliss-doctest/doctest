package com.devbliss.doctest;

import java.io.File;
import java.util.Date;
import java.util.List;

import com.devbliss.doctest.templates.AssertDocItem;
import com.devbliss.doctest.templates.DocItem;
import com.devbliss.doctest.templates.RequestDocItem;
import com.devbliss.doctest.templates.ResponseDocItem;
import com.devbliss.doctest.templates.SectionDocItem;
import com.devbliss.doctest.templates.Templates;
import com.devbliss.doctest.templates.TextDocItem;
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
    private final Templates templates;

    @Inject
    public HtmlRenderer(IndexFileGenerator indexFileGenerator, Templates templates) {
        this.indexFileGenerator = indexFileGenerator;
        this.templates = templates;
    }

    // public void sayRequest(URI uri, String payload, HTTP_REQUEST httpRequest) throws
    // JSONException {
    // if (uri != null) {
    // listTemplates.add(new RequestDocItem(httpRequest, uri.toString(), getJson(payload)));
    // say(templates.getUriTemplate(uri.toString(), getJson(payload), httpRequest));
    // }
    // }
    //
    // public void sayResponse(int responseCode, String payload) throws Exception {
    // listTemplates.add(new com.devbliss.doctest.templates.ResponseDocItem(responseCode,
    // getJson(payload)));
    // say(templates.getResponseTemplate(responseCode, getJson(payload)));
    // }
    //
    // public void sayVerify(String condition) {
    // listTemplates.add(new AssertDocItem(condition));
    // say(templates.getVerifyTemplate(condition));
    // }
    //
    // public void sayPreformatted(String preformattedText) {
    // say(templates.getJsonTemplate(preformattedText));
    // }

    public void render(List<DocItem> listTemplates, String name) {

        String finalHeader = String.format(headerFormat, name);

        StringBuffer buffer = new StringBuffer();
        buffer.append("Doctest originally perfomed at: " + new Date());
        for (DocItem myitem : listTemplates) {

            if (myitem instanceof AssertDocItem) {
                buffer.append(templates.getVerifyTemplate(((AssertDocItem) myitem).expected));
            } else if (myitem instanceof RequestDocItem) {
                buffer.append(templates.getUriTemplate(((RequestDocItem) myitem).uri,
                        ((RequestDocItem) myitem).payload, ((RequestDocItem) myitem).http));
            } else if (myitem instanceof ResponseDocItem) {
                buffer.append(templates
                        .getResponseTemplate(((ResponseDocItem) myitem).responseCode,
                                ((ResponseDocItem) myitem).payload));
            } else if (myitem instanceof SectionDocItem) {
                buffer.append(templates.getSectionTemplate(((SectionDocItem) myitem).title));
            } else if (myitem instanceof TextDocItem) {
                buffer.append(((TextDocItem) myitem).text);
            }
        }

        String finalBody = String.format(bodyFormat, buffer.toString());

        // this will be not SaySysoutImpl, but the name of the Unit test :)
        String finalDocument = finalHeader + finalBody;

        String fileNameForCompleteTestOutput = OUTPUT_DIRECTORY + name + ".html";

        // make sure the directories have been generated:
        new File(fileNameForCompleteTestOutput).getParentFile().mkdirs();

        writeOutFile(fileNameForCompleteTestOutput, finalDocument);

        indexFileGenerator.generatIndexFileForTests();
    }
}
