package com.devbliss.doctest.items;

public class AssertDocItem implements DocItem {

    private String expected;

    public AssertDocItem(String expected) {
        this.setExpected(expected);
    }

    public String getExpected() {
        return expected;
    }

    public void setExpected(String expected) {
        this.expected = expected;
    }

    public String getItemName() {
        return "assert";
    }
}
