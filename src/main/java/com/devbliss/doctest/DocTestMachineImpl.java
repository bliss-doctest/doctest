package com.devbliss.doctest;

import java.net.URI;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import com.devbliss.doctest.templates.Assert;
import com.devbliss.doctest.templates.Item;
import com.devbliss.doctest.templates.Request;
import com.devbliss.doctest.templates.Section;
import com.devbliss.doctest.templates.Templates;
import com.devbliss.doctest.templates.Text;
import com.google.inject.Inject;

import de.devbliss.apitester.ApiTest.HTTP_REQUEST;

public class DocTestMachineImpl implements DocTestMachine {

    // some html formatting

    // The class under test.
    // Usually I don't like that, but we don't have another option to generate
    // the name under test because of the static way JUnit runs files and uses
    // AfterClass and BeforeClass.
    @SuppressWarnings("rawtypes")
    public Class classUnderTest;

    public StringBuffer outputOfTestsBuffer = new StringBuffer();

    private final Templates templates;

    private final List<Item> listTemplates;

    private final ReportRenderer reportRenderer;

    @Inject
    public DocTestMachineImpl(Templates templates, ReportRenderer reportRenderer) {
        listTemplates = new ArrayList<Item>();
        this.reportRenderer = reportRenderer;
        this.templates = templates;
    }

    /**
     * Main method => this lets you write out stuff.
     * 
     * Usually after a say a newline is appended.
     * 
     */
    public void say(String say) {
        listTemplates.add(new Text(say));
        // outputOfTestsBuffer.append(String.format(simpleLine, say));
    }

    /**
     * Inits the new file for writing stuff out.
     */
    public void beginDoctest(@SuppressWarnings("rawtypes") Class clazz) {
        if (classUnderTest == null) {
            classUnderTest = clazz;
            listTemplates.add(new Text("Doctest originally perfomed at: " + new Date()));
            say("Doctest originally perfomed at: " + new Date());
        }
    }

    /**
     * This would be a header. Maybe a new test inside a testcase.
     */
    public void sayNextSection(String sectionName) {
        listTemplates.add(new Section(sectionName));
        // outputOfTestsBuffer.append(String.format(h1, sectionName));
    }

    /**
     * At the end of a successful test stuff gets written out.
     */
    public void endDocTest() {
        // assemble the html page:

        reportRenderer.render(listTemplates, classUnderTest.getCanonicalName());

        classUnderTest = null;
    }

    private String getJson(String json) throws JSONException {
        if (isJsonValid(json)) {
            return templates.getJsonTemplate(new JSONObject(json).toString(2));
        } else {
            return "";
        }
    }

    private boolean isJsonValid(String json) {
        return json != null && !json.equals("null") && !json.isEmpty() && json.startsWith("{");
    }

    public void sayRequest(URI uri, String payload, HTTP_REQUEST httpRequest) throws JSONException {
        if (uri != null) {
            listTemplates.add(new Request(httpRequest, uri.toString(), getJson(payload)));
            say(templates.getUriTemplate(uri.toString(), getJson(payload), httpRequest));
        }
    }

    public void sayResponse(int responseCode, String payload) throws Exception {
        listTemplates.add(new com.devbliss.doctest.templates.Response(responseCode,
                getJson(payload)));
        say(templates.getResponseTemplate(responseCode, getJson(payload)));
    }

    public void sayVerify(String condition) {
        listTemplates.add(new Assert(condition));
        say(templates.getVerifyTemplate(condition));
    }

    public void sayPreformatted(String preformattedText) {
        say(templates.getJsonTemplate(preformattedText));
    }
}
