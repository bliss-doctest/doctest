package com.devbliss.doctest.items;


public class JsonDocItem implements DocItem {

    private String expected;

    public JsonDocItem(String expected) {
        this.setExpected(expected);
    }

    public String getExpected() {
        return expected;
    }

    public void setExpected(String expected) {
        this.expected = expected;
    }
}
