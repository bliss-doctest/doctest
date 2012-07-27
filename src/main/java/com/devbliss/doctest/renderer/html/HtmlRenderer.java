package com.devbliss.doctest.renderer.html;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.devbliss.doctest.items.DocItem;
import com.devbliss.doctest.items.LinkDocItem;
import com.devbliss.doctest.items.MenuDocItem;
import com.devbliss.doctest.items.ReportFileDocItem;
import com.devbliss.doctest.items.SectionDocItem;
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
            ReportFileDocItem report = new ReportFileDocItem(name, items);
            String nameWithExtension = helper.getCompleteFileName(name, HTML_EXTENSION);
            helper.writeFile(nameWithExtension, htmlItems.getReportFileTemplate(report));

            indexFileGenerator.render(null, INDEX);
        }
    }

    private String appendItemsToBuffer(List<DocItem> listTemplates) {
        sections.clear();
        StringBuffer buffer = new StringBuffer();
        StringBuffer tempBuffer = new StringBuffer();
        for (DocItem item : listTemplates) {
            if (item instanceof SectionDocItem) {
                tempBuffer.append(getSectionDocItem((SectionDocItem) item));
            } else {
                tempBuffer.append(getTemplateForItem(item));
            }
        }

        appendSectionList(buffer);
        buffer.append(tempBuffer);
        return buffer.toString();
    }

    private String getTemplateForItem(DocItem item) {
        return htmlItems.getTemplateForItem(item);
    }

    private String getSectionDocItem(SectionDocItem item) {
        String sectionId = getSectionId();
        String sectionName = item.getTitle();
        sections.put("#" + sectionId, sectionName);
        item.setHref(sectionId);
        return getTemplateForItem(item);
    }

    private void appendSectionList(StringBuffer buffer) {
        List<LinkDocItem> files = new ArrayList<LinkDocItem>();
        for (Entry<String, String> section : sections.entrySet()) {
            files.add(new LinkDocItem(section.getKey(), section.getValue()));
        }
        buffer.append(htmlItems.getListFilesTemplate(new MenuDocItem("", files)));
    }

    private String getSectionId() {
        return "section" + ++sectionNumber;
    }
}
