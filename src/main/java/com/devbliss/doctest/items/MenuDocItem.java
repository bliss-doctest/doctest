package com.devbliss.doctest.items;

import java.util.List;

public class MenuDocItem implements DocItem {

    private String title;
    private List<LinkDocItem> files;

    public MenuDocItem(String title, List<LinkDocItem> files) {
        this.setTitle(title);
        this.files = files;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<LinkDocItem> getFiles() {
        return files;
    }

    public void setFiles(List<LinkDocItem> files) {
        this.files = files;
    }
}
