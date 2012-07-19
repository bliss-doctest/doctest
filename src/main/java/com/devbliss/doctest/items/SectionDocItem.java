package com.devbliss.doctest.items;

public class SectionDocItem implements DocItem {

    private String title;
    private String href;

    public SectionDocItem(String title) {
        this.title = title;
    }

    public SectionDocItem(String title, String href) {
        this.title = title;
        this.href = href;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }
}
