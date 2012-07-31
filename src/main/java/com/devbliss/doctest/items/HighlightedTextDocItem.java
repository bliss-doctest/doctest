package com.devbliss.doctest.items;

public class HighlightedTextDocItem implements DocItem {

    private String text;

    public HighlightedTextDocItem(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getItemName() {
        return "inlinehighlight";
    }
}
