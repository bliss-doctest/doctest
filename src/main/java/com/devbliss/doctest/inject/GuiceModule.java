package com.devbliss.doctest.inject;

import com.devbliss.doctest.httpwithoutredirect.DeleteWithoutRedirectImpl;
import com.devbliss.doctest.httpwithoutredirect.GetWithoutRedirectImpl;
import com.devbliss.doctest.httpwithoutredirect.PostWithoutRedirectImpl;
import com.devbliss.doctest.httpwithoutredirect.PutWithoutRedirectImpl;
import com.devbliss.doctest.machine.DocTestMachine;
import com.devbliss.doctest.machine.DocTestMachineImpl;
import com.devbliss.doctest.renderer.ReportRenderer;
import com.devbliss.doctest.renderer.html.HtmlItems;
import com.devbliss.doctest.renderer.html.HtmlRenderer;
import com.devbliss.doctest.utils.FileHelper;
import com.devbliss.doctest.utils.JSONHelper;
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
        bind(HtmlItems.class).in(Singleton.class);
        bind(FileHelper.class).in(Singleton.class);
        bind(ReportRenderer.class).to(HtmlRenderer.class).in(Singleton.class);

        bind(DeleteFactory.class).annotatedWith(Names.named(ApiTest.DELETE_FACTORY)).to(
                DeleteWithoutRedirectImpl.class);
        bind(GetFactory.class).annotatedWith(Names.named(ApiTest.GET_FACTORY)).to(
                GetWithoutRedirectImpl.class);
        bind(PutFactory.class).annotatedWith(Names.named(ApiTest.PUT_FACTORY)).to(
                PutWithoutRedirectImpl.class);
        bind(PostFactory.class).annotatedWith(Names.named(ApiTest.POST_FACTORY)).to(
                PostWithoutRedirectImpl.class);
    }
}
