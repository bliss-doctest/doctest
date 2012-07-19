package com.devbliss.doctest.renderer.html;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.devbliss.doctest.items.AssertDocItem;
import com.devbliss.doctest.items.DocItem;
import com.devbliss.doctest.items.FileDocItem;
import com.devbliss.doctest.items.JsonDocItem;
import com.devbliss.doctest.items.LinkDocItem;
import com.devbliss.doctest.items.MenuDocItem;
import com.devbliss.doctest.items.RequestDocItem;
import com.devbliss.doctest.items.ResponseDocItem;
import com.devbliss.doctest.items.SectionDocItem;
import com.devbliss.doctest.items.TextDocItem;
import com.devbliss.doctest.renderer.ReportRenderer;
import com.devbliss.doctest.utils.FileHelper;
import com.google.inject.Inject;

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
public class HtmlRenderer extends AbstractHtmlReportRenderer implements ReportRenderer {

    private final HtmlIndexFileRenderer indexFileGenerator;
    private int sectionNumber = 0;
    private final Map<String, String> sections;
    private final FileHelper helper;

    @Inject
    public HtmlRenderer(
            HtmlIndexFileRenderer indexFileGenerator,
            HtmlItems htmlItems,
            FileHelper abstractReportRenderer) {
        super(htmlItems);
        this.indexFileGenerator = indexFileGenerator;
        this.helper = abstractReportRenderer;
        sections = new LinkedHashMap<String, String>();
    }

    public void render(List<DocItem> listTemplates, String name) throws Exception {
        if (listTemplates != null && !listTemplates.isEmpty()) {
            String items = appendItemsToBuffer(listTemplates);
            FileDocItem report = new FileDocItem(name, items);
            String nameWithExtension = helper.getCompleteFileName(name, HTML_EXTENSION);
            helper.writeFile(nameWithExtension, htmlItems.getReportTemplate(report));

            indexFileGenerator.render(null, INDEX);
        }
    }

    private String appendItemsToBuffer(List<DocItem> listTemplates) {
        StringBuffer buffer = new StringBuffer();
        StringBuffer tempBuffer = new StringBuffer();
        for (DocItem item : listTemplates) {
            if (item instanceof AssertDocItem) {
                tempBuffer.append(getAssertDocItem((AssertDocItem) item));
            } else if (item instanceof RequestDocItem) {
                tempBuffer.append(getRequestDocItem((RequestDocItem) item));
            } else if (item instanceof ResponseDocItem) {
                tempBuffer.append(getResponseDocItem((ResponseDocItem) item));
            } else if (item instanceof SectionDocItem) {
                tempBuffer.append(getSectionDocItem((SectionDocItem) item));
            } else if (item instanceof TextDocItem) {
                tempBuffer.append(getTextDocItem((TextDocItem) item));
            } else if (item instanceof JsonDocItem) {
                tempBuffer.append(getJsonDocItem((JsonDocItem) item));
            }
        }

        appendSectionList(buffer);
        buffer.append(tempBuffer);
        return buffer.toString();
    }

    private void appendSectionList(StringBuffer buffer) {
        List<LinkDocItem> files = new ArrayList<LinkDocItem>();
        for (Entry<String, String> section : sections.entrySet()) {
            files.add(new LinkDocItem(section.getKey(), section.getValue()));
        }
        buffer.append(htmlItems.getListFilesTemplate(new MenuDocItem("", files)));
    }

    private String getAssertDocItem(AssertDocItem item) {
        return htmlItems.getAssertTemplate(item);
    }

    private String getRequestDocItem(RequestDocItem item) {
        return htmlItems.getRequestTemplate(item);
    }

    private String getJsonDocItem(JsonDocItem item) {
        return htmlItems.getJsonTemplate(item);
    }

    private String getTextDocItem(TextDocItem item) {
        return item.getText();
    }

    private String getSectionDocItem(SectionDocItem item) {
        String sectionId = getSectionId();
        String sectionName = item.getTitle();
        sections.put("#" + sectionId, sectionName);
        item.setHref(sectionId);
        return htmlItems.getSectionTemplate(item);
    }

    private String getSectionId() {
        return "section" + ++sectionNumber;
    }

    private String getResponseDocItem(ResponseDocItem item) {
        return htmlItems.getResponseTemplate(item);
    }
}
