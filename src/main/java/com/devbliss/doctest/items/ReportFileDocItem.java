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
    private String items;
    private final String date;

    public ReportFileDocItem(String name, String items) {
        this("", name, items);
    }

    public ReportFileDocItem(String css, String name, String items) {
        this.css = css;
        this.name = name;
        this.items = items;
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
