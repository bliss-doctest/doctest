package com.devbliss.doctest.templates;

public class AssertDocItem implements DocItem {

    public final String expected;

    public AssertDocItem(String expected) {
        this.expected = expected;
    }
}
