package com.devbliss.doctest;

import com.devbliss.doctest.templates.Templates;
import com.google.inject.Guice;
import com.google.inject.Injector;

import de.devbliss.apitester.ApiTest;

public abstract class DocTest extends LogicDocTest {

    private static Injector injector;
    static {
        injector = Guice.createInjector(new GuiceModule());
    }

    public DocTest() {
        super(injector.getInstance(DocTestMachine.class), injector.getInstance(ApiTest.class),
                injector.getInstance(JSONHelper.class), injector.getInstance(Templates.class));
    }
}
