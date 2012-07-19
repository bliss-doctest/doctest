package com.devbliss.doctest.templating;

import java.io.File;
import java.io.IOException;

import com.google.inject.Inject;

import freemarker.template.Configuration;
import freemarker.template.ObjectWrapper;

public class ConfigurationWrapper extends Configuration {

    @Inject
    public ConfigurationWrapper(ObjectWrapper objectWrapper) throws IOException {
        super();
        setDirectoryForTemplateLoading(new File("src/main/resources/templates"));
        setObjectWrapper(objectWrapper);
    }
}
