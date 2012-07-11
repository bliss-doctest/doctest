package com.devbliss.doctest;

import com.devbliss.doctest.httpwithoutredirect.DeleteWithoutRedirectImpl;
import com.devbliss.doctest.httpwithoutredirect.GetWithoutRedirectImpl;
import com.devbliss.doctest.httpwithoutredirect.PostWithoutRedirectImpl;
import com.devbliss.doctest.httpwithoutredirect.PutWithoutRedirectImpl;
import com.devbliss.doctest.templates.Templates;
import com.google.inject.AbstractModule;
import com.google.inject.Singleton;
import com.google.inject.name.Names;

import de.devbliss.apitester.ApiTest;
import de.devbliss.apitester.factory.DeleteFactory;
import de.devbliss.apitester.factory.GetFactory;
import de.devbliss.apitester.factory.PostFactory;
import de.devbliss.apitester.factory.PutFactory;

public class GuiceModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(DocTestMachine.class).to(DocTestMachineImpl.class).in(Singleton.class);
        bind(ApiTest.class).in(Singleton.class);
        bind(JSONHelper.class).in(Singleton.class);
        bind(Templates.class).in(Singleton.class);

        bind(DeleteFactory.class).annotatedWith(Names.named("deleteFactory")).to(
                DeleteWithoutRedirectImpl.class);
        bind(GetFactory.class).annotatedWith(Names.named("getFactory")).to(
                GetWithoutRedirectImpl.class);
        bind(PutFactory.class).annotatedWith(Names.named("putFactory")).to(
                PutWithoutRedirectImpl.class);
        bind(PostFactory.class).annotatedWith(Names.named("postFactory")).to(
                PostWithoutRedirectImpl.class);
    }
}
