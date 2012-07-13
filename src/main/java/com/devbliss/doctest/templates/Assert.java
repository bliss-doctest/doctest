package com.devbliss.doctest.templates;

public class Assert implements Item {

    public final String expected;

    public Assert(String expected) {
        this.expected = expected;
    }
}
