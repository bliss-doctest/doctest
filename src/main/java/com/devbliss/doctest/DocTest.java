package com.devbliss.doctest;

import com.devbliss.doctest.templates.Templates;
import com.google.inject.Guice;
import com.google.inject.Injector;

import de.devbliss.apitester.ApiTest;

public abstract class DocTest extends LogicDocTest {

    private static Injector injector;
    private static Templates TEMPLATES;
    private static JSONHelper JSON_HELPER;
    private static ApiTest API_TEST;
    private static DocTestMachine DOC_TEST_MACHINE;

    static {
        injector = Guice.createInjector(new GuiceModule());
        DOC_TEST_MACHINE = injector.getInstance(DocTestMachine.class);
        API_TEST = injector.getInstance(ApiTest.class);
        JSON_HELPER = injector.getInstance(JSONHelper.class);
        TEMPLATES = injector.getInstance(Templates.class);
    }

    public DocTest() {
        super(DOC_TEST_MACHINE, API_TEST, JSON_HELPER, TEMPLATES);
    }
}
