package com.devbliss.doctest.items;

import java.util.Date;

public class FileDocItem implements DocItem {

    private String css;
    private String name;
    private String items;
    private final String date;

    public FileDocItem(String name, String items) {
        this("", name, items);
    }

    public FileDocItem(String css, String name, String items) {
        this.css = css;
        this.name = name;
        this.items = items;
        this.date = new Date().toString();
    }

    public String getCss() {
        return css;
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
}
