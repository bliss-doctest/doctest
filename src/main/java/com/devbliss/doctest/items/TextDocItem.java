package com.devbliss.doctest.items;

public class TextDocItem implements DocItem {

    private String text;

    public TextDocItem(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

}
