package com.devbliss.doctest.renderer.html;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

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

import de.devbliss.apitester.ApiTest.HTTP_REQUEST;

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
    private int sectionNumber = 0;
    private final Map<String, String> sections;

    @Inject
    public HtmlRenderer(IndexFileGenerator indexFileGenerator, HtmlItems templates) {
        sections = new HashMap<String, String>();
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
        StringBuffer tempBuffer = new StringBuffer();
        for (DocItem myitem : listTemplates) {
            if (myitem instanceof AssertDocItem) {
                tempBuffer.append(getAssertDocItem(myitem));
            } else if (myitem instanceof RequestDocItem) {
                tempBuffer.append(getRequestDocItem(myitem));
            } else if (myitem instanceof ResponseDocItem) {
                tempBuffer.append(getResponseDocItem(myitem));
            } else if (myitem instanceof SectionDocItem) {
                tempBuffer.append(getSectionDocItem(myitem));
            } else if (myitem instanceof TextDocItem) {
                tempBuffer.append(getTextDocItem(myitem));
            } else if (myitem instanceof JsonDocItem) {
                tempBuffer.append(getJsonDocItem(myitem));
            }
        }

        appendSectionList(buffer);
        buffer.append(tempBuffer);
    }

    private void appendSectionList(StringBuffer buffer) {
        StringBuffer tmpBuffer = new StringBuffer();
        for (Entry<String, String> section : sections.entrySet()) {
            tmpBuffer.append(getLiSection(section));
        }

        buffer.append(templates.getLiMenuTemplate(tmpBuffer.toString()));
    }

    private String getLiSection(Entry<String, String> section) {
        return templates.getLiSectionTemplate(section.getKey(), section.getValue());
    }

    private String getAssertDocItem(DocItem myitem) {
        return templates.getVerifyTemplate(((AssertDocItem) myitem).expected);
    }

    private String getRequestDocItem(DocItem myitem) {
        String payload = templates.getJsonTemplate(((RequestDocItem) myitem).payload);
        String uri = ((RequestDocItem) myitem).uri;
        HTTP_REQUEST http = ((RequestDocItem) myitem).http;
        return templates.getUriTemplate(uri, payload, http);
    }

    private String getJsonDocItem(DocItem myitem) {
        return templates.getJsonTemplate(((JsonDocItem) myitem).expected);
    }

    private String getTextDocItem(DocItem myitem) {
        return ((TextDocItem) myitem).text;
    }

    private String getSectionDocItem(DocItem myitem) {
        String sectionId = getSectionId();
        String sectionName = ((SectionDocItem) myitem).title;
        sections.put(sectionId, sectionName);
        return templates.getSectionTemplate(sectionName, sectionId);
    }

    private String getSectionId() {
        return "section" + ++sectionNumber;
    }

    private String getResponseDocItem(DocItem myitem) {
        String payload = templates.getJsonTemplate(((ResponseDocItem) myitem).payload);
        int responseCode = ((ResponseDocItem) myitem).responseCode;
        return templates.getResponseTemplate(responseCode, payload);
    }
}
