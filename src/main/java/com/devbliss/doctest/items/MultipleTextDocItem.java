package com.devbliss.doctest.items;

public class MultipleTextDocItem implements DocItem {

    private String text;
    private String[] additionalStrings;

    public MultipleTextDocItem(String text, String[] strings) {
        this.text = text;
        this.additionalStrings = strings;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String[] getAdditionalStrings() {
        return additionalStrings;
    }

    public void setStrings(String[] strings) {
        this.additionalStrings = strings;
    }

    public String getItemName() {
        return "text";
    }
}
