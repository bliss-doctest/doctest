package com.devbliss.doctest.items;

public class SectionDocItem implements DocItem {

    private String title;

    public SectionDocItem(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

}
