package com.devbliss.doctest.items;

public class LinkDocItem implements DocItem {

    private String name;
    private String href;

    public LinkDocItem(String href, String name) {
        this.name = name;
        this.href = href;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getItemName() {
        return "listFiles";
    }

}
