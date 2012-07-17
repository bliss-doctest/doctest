package com.devbliss.doctest.renderer.html;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.devbliss.doctest.items.AssertDocItem;
import com.devbliss.doctest.items.DocItem;
import com.devbliss.doctest.items.JsonDocItem;
import com.devbliss.doctest.items.RequestDocItem;
import com.devbliss.doctest.items.ResponseDocItem;
import com.devbliss.doctest.items.SectionDocItem;
import com.devbliss.doctest.items.TextDocItem;
import com.devbliss.doctest.renderer.ReportRenderer;
import com.google.inject.Inject;

import de.devbliss.apitester.ApiTest.HTTP_REQUEST;

/**
 * Html-implementation of the {@link ReportRenderer}.
 * <p>
 * Each time that the function {@link #render(List, String)} is called, a report file is created for
 * the test case and an index file will be generated.
 * </p>
 * 
 * @author bmary
 * 
 */
public class HtmlRenderer extends AbstractHtmlReportRenderer {

    private final IndexFileGenerator indexFileGenerator;
    private int sectionNumber = 0;
    private final Map<String, String> sections;

    @Inject
    public HtmlRenderer(IndexFileGenerator indexFileGenerator, HtmlItems htmlItems) {
        super(htmlItems);
        this.indexFileGenerator = indexFileGenerator;
        sections = new HashMap<String, String>();
    }

    public void render(List<DocItem> listTemplates, String name) throws Exception {
        String finalHeader = htmlItems.getHeaderFormatTemplate(name);

        StringBuffer buffer = new StringBuffer();
        buffer.append(htmlItems.getLinkTemplate(INDEX + HTML_EXTENSION, "back to index page"));
        buffer.append("Doctest originally perfomed at: " + new Date());
        appendItemsToBuffer(listTemplates, buffer);

        String finalBody = htmlItems.getBodyTemplate(buffer.toString());

        String finalDocument = finalHeader + finalBody;
        finalDocument = htmlItems.getHtmlTemplate(finalDocument);

        String fileNameForCompleteTestOutput = getCompleteFileName(name, HTML_EXTENSION);
        writeFile(fileNameForCompleteTestOutput, finalDocument);

        indexFileGenerator.render(null, INDEX);
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

        buffer.append(htmlItems.getLiMenuTemplate("Sections of this test class", tmpBuffer
                .toString()));
    }

    private String getLiSection(Entry<String, String> section) {
        return htmlItems.getLiWithLinkTemplate(section.getKey(), section.getValue());
    }

    private String getAssertDocItem(DocItem myitem) {
        return htmlItems.getVerifyTemplate(((AssertDocItem) myitem).expected);
    }

    private String getRequestDocItem(DocItem myitem) {
        String payload = htmlItems.getJsonTemplate(((RequestDocItem) myitem).payload);
        String uri = ((RequestDocItem) myitem).uri;
        HTTP_REQUEST http = ((RequestDocItem) myitem).http;
        return htmlItems.getUriTemplate(uri, payload, http);
    }

    private String getJsonDocItem(DocItem myitem) {
        return htmlItems.getJsonTemplate(((JsonDocItem) myitem).expected);
    }

    private String getTextDocItem(DocItem myitem) {
        return ((TextDocItem) myitem).text;
    }

    private String getSectionDocItem(DocItem myitem) {
        String sectionId = getSectionId();
        String sectionName = ((SectionDocItem) myitem).title;
        sections.put("#" + sectionId, sectionName);
        return htmlItems.getSectionTemplate(sectionName, sectionId);
    }

    private String getSectionId() {
        return "section" + ++sectionNumber;
    }

    private String getResponseDocItem(DocItem myitem) {
        String payload = htmlItems.getJsonTemplate(((ResponseDocItem) myitem).payload);
        int responseCode = ((ResponseDocItem) myitem).responseCode;
        return htmlItems.getResponseTemplate(responseCode, payload);
    }
}
