package com.devbliss.doctest;

import com.devbliss.doctest.templates.Templates;

import de.devbliss.apitester.ApiTest;

public class IntegrationTest extends DocTest {

    public IntegrationTest(
            DocTestMachine docTest,
            ApiTest apiTest,
            JSONHelper jsonHelper,
            Templates templates) {
        super(docTest, apiTest, jsonHelper, templates);
    }

    @Override
    protected Class<?> getTestClass() {
        // TODO Auto-generated method stub
        return null;
    }

}
