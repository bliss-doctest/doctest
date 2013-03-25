/*
 * Copyright 2013, devbliss GmbH
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */

package com.devbliss.doctest.renderer.html;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.devbliss.doctest.items.DocItem;
import com.devbliss.doctest.items.HighlightedTextDocItem;
import com.devbliss.doctest.items.JsonDocItem;
import com.devbliss.doctest.items.LinkDocItem;
import com.devbliss.doctest.items.MenuDocItem;
import com.devbliss.doctest.items.MultipleTextDocItem;
import com.devbliss.doctest.items.ReportFileDocItem;
import com.devbliss.doctest.items.SectionDocItem;
import com.devbliss.doctest.renderer.ReportRenderer;
import com.devbliss.doctest.utils.FileHelper;
import com.devbliss.doctest.utils.JSONHelper;
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
    private final JSONHelper jsonhelper;

    @Inject
    public HtmlRenderer(
            HtmlIndexFileRenderer indexFileGenerator,
            HtmlItems htmlItems,
            FileHelper abstractReportRenderer,
            JSONHelper jsonhelper) {
        super(htmlItems);
        this.indexFileGenerator = indexFileGenerator;
        this.helper = abstractReportRenderer;
        this.jsonhelper = jsonhelper;
        sections = new LinkedHashMap<String, String>();
    }

    public void render(List<DocItem> listTemplates, String name, String introduction)
            throws Exception {
        if (listTemplates != null && !listTemplates.isEmpty()) {
            String items = appendItemsToBuffer(listTemplates);
            ReportFileDocItem report = new ReportFileDocItem(name, introduction, items);
            String nameWithExtension = helper.getCompleteFileName(name, HTML_EXTENSION);
            helper.writeFile(nameWithExtension, htmlItems.getReportFileTemplate(report));

            indexFileGenerator.render(null, INDEX, introduction);
        }
    }

    private String appendItemsToBuffer(List<DocItem> listTemplates) {
        sections.clear();
        StringBuffer buffer = new StringBuffer();
        StringBuffer tempBuffer = new StringBuffer();
        for (DocItem item : listTemplates) {
            if (item instanceof SectionDocItem) {
                tempBuffer.append(getSectionDocItem((SectionDocItem) item));
            } else if (item instanceof MultipleTextDocItem) {
                tempBuffer.append(getMultipleTextDocItem((MultipleTextDocItem) item));
            } else {
                tempBuffer.append(getTemplateForItem(item));
            }
        }

        appendSectionList(buffer);
        buffer.append(tempBuffer);
        return buffer.toString();
    }

    /**
     * 
     * Renders a MultipleTextDocItem consisting of multiple Json and Text Elements.
     * 
     * @param item
     * @return
     */
    private String getMultipleTextDocItem(MultipleTextDocItem item) {
        for (int i = 0; i < item.getAdditionalStrings().length; i++) {
            if (jsonhelper.isJsonValid(item.getAdditionalStrings()[i])) {
                item.getAdditionalStrings()[i] =
                        getTemplateForItem(new JsonDocItem(item.getAdditionalStrings()[i]));
            } else {
                item.getAdditionalStrings()[i] =
                        getTemplateForItem(new HighlightedTextDocItem(
                                item.getAdditionalStrings()[i]));
            }
        }

        item.setText(String.format(item.getText(), (Object[]) item.getAdditionalStrings()));

        return getTemplateForItem(item);
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
