/*
 * Copyright 2013, devbliss GmbH
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */

package com.devbliss.doctest.inject;

import com.devbliss.doctest.httpfactory.DeleteWithoutRedirectImpl;
import com.devbliss.doctest.httpfactory.GetWithoutRedirectImpl;
import com.devbliss.doctest.httpfactory.PostWithoutRedirectImpl;
import com.devbliss.doctest.httpfactory.PutWithoutRedirectImpl;
import com.devbliss.doctest.machine.DocTestMachine;
import com.devbliss.doctest.machine.DocTestMachineImpl;
import com.devbliss.doctest.renderer.ReportRenderer;
import com.devbliss.doctest.renderer.html.HtmlItems;
import com.devbliss.doctest.renderer.html.HtmlRenderer;
import com.devbliss.doctest.templating.ConfigurationWrapper;
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
import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.ObjectWrapper;

public class GuiceModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(DocTestMachine.class).to(DocTestMachineImpl.class).in(Singleton.class);
        bind(ApiTest.class).in(Singleton.class);
        bind(JSONHelper.class).in(Singleton.class);
        bind(HtmlItems.class).in(Singleton.class);
        bind(FileHelper.class).in(Singleton.class);
        bind(ReportRenderer.class).to(HtmlRenderer.class).in(Singleton.class);
        bind(Configuration.class).to(ConfigurationWrapper.class).in(Singleton.class);
        bind(ObjectWrapper.class).to(DefaultObjectWrapper.class);

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
