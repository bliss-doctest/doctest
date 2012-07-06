package de.devbliss.doctest;


import java.net.URI;

import org.json.JSONException;

import de.devbliss.apitester.ApiTest.HTTP_REQUEST;

public interface DocTestMachine {

    void say(String say);

    void sayNextSection(String sectionName);

    void beginDoctest(@SuppressWarnings("rawtypes") Class clazz);

    public void endDocTest();

    void sayRequest(URI uri, String payload, HTTP_REQUEST httpRequest) throws JSONException;

    void sayResponse(int responseCode, String payload) throws Exception;
}
