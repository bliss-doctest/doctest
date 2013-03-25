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

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

import com.devbliss.doctest.items.DocItem;
import com.devbliss.doctest.items.IndexFileDocItem;
import com.devbliss.doctest.items.MenuDocItem;
import com.devbliss.doctest.items.ReportFileDocItem;
import com.google.common.io.CharStreams;
import com.google.inject.Inject;

import freemarker.template.Configuration;
import freemarker.template.Template;

/**
 * Items used by the {@link HtmlRenderer} to build the html report.
 * 
 * @author bmary
 * 
 */
public class HtmlItems {

    private final Configuration configuration;

    @Inject
    public HtmlItems(Configuration configuration) {
        this.configuration = configuration;
    }

    private String init(String templateName, DocItem item) {
        StringWriter writer = new StringWriter();
        Template template;
        try {
            template = configuration.getTemplate(templateName);
            template.process(item, writer);
            return writer.toString();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static String getCss() {
        try {
            InputStream htmlCSSStream = HtmlItems.class.getResourceAsStream("/htmlStyle.css");
            return CharStreams.toString(new InputStreamReader(htmlCSSStream));
        } catch (IOException e) {
            return "no css";
        }
    }

    public String getIndexTemplate(IndexFileDocItem item) {
        item.setCss(getCss());
        return init("index.ftl", item);
    }

    public String getReportFileTemplate(ReportFileDocItem item) {
        item.setCss(getCss());
        return init("htmlFile.ftl", item);
    }

    public String getListFilesTemplate(MenuDocItem item) {
        StringWriter writer = new StringWriter();
        Template template;
        try {
            template = configuration.getTemplate("listFiles.ftl");
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("files", item.getFiles());
            template.process(map, writer);
            return writer.toString();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public String getTemplateForItem(DocItem item) {
        return init(item.getItemName() + ".ftl", item);
    }
}
