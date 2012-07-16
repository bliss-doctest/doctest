package com.devbliss.doctest.items;

public class AssertDocItem implements DocItem {

    public final String expected;

    public AssertDocItem(String expected) {
        this.expected = expected;
    }
}
