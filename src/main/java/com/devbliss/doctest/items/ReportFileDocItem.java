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

package com.devbliss.doctest.items;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Date;

import com.devbliss.doctest.renderer.html.HtmlItems;
import com.google.common.io.CharStreams;

public class ReportFileDocItem implements DocItem {

    private String css;
    private String jsCode;
    private String name;
    private final String introduction;
    private String items;
    private final String date;

    public ReportFileDocItem(String name, String introduction, String items) {
        this("", name, introduction, items);
    }

    public ReportFileDocItem(String css, String name, String introduction, String items) {
        this.css = css;
        this.name = name;
        this.items = items;
        this.introduction = introduction;
        this.date = new Date().toString();
        try {
            StringBuffer stringBuffer = new StringBuffer();
            InputStream jsStream = HtmlItems.class.getResourceAsStream("/script.js");
            stringBuffer.append(CharStreams.toString(new InputStreamReader(jsStream)));
            jsStream = HtmlItems.class.getResourceAsStream("/jquery-1.8.0.min.js");
            stringBuffer.append(CharStreams.toString(new InputStreamReader(jsStream)));
            this.jsCode = stringBuffer.toString();
        } catch (IOException e) {
            this.jsCode = "no js";
        }
    }

    public String getCss() {
        return css;
    }

    public String getJsCode() {
        return jsCode;
    }

    public String getName() {
        return name;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setCss(String css) {
        this.css = css;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getItems() {
        return items;
    }

    public void setItems(String items) {
        this.items = items;
    }

    public String getDate() {
        return this.date;
    }

    public String getItemName() {
        return "htmlFile";
    }

}
