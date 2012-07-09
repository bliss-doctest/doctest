package com.devbliss.doctest;

import com.devbliss.doctest.templates.Templates;
import com.google.inject.AbstractModule;
import com.google.inject.Singleton;

import de.devbliss.apitester.ApiTest;

public class GuiceModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(DocTestMachine.class).to(DocTestMachineImpl.class).in(Singleton.class);
        bind(ApiTest.class).in(Singleton.class);
        bind(JSONHelper.class).in(Singleton.class);
        bind(Templates.class).in(Singleton.class);
    }

}
