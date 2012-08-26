package com.devbliss.doctest;

import com.devbliss.doctest.inject.GuiceModule;
import com.devbliss.doctest.machine.DocTestMachine;
import com.devbliss.doctest.renderer.html.HtmlItems;
import com.devbliss.doctest.utils.FileHelper;
import com.devbliss.doctest.utils.JSONHelper;
import com.google.inject.Guice;
import com.google.inject.Injector;

import de.devbliss.apitester.ApiTest;

public abstract class DocTest extends LogicDocTest {

    private static Injector injector;
    private static JSONHelper JSON_HELPER;
    private static ApiTest API_TEST;
    private static DocTestMachine DOC_TEST_MACHINE;
    private static FileHelper FILE_HELPER;

    static {
        injector = Guice.createInjector(new GuiceModule());
        DOC_TEST_MACHINE = injector.getInstance(DocTestMachine.class);
        API_TEST = injector.getInstance(ApiTest.class);
        JSON_HELPER = injector.getInstance(JSONHelper.class);
        FILE_HELPER = injector.getInstance(FileHelper.class);
    }

    protected static void setApi(ApiTest api) {
        API_TEST = api;
    }

    public DocTest() {
        super(DOC_TEST_MACHINE, API_TEST, JSON_HELPER, FILE_HELPER);
    }
}
