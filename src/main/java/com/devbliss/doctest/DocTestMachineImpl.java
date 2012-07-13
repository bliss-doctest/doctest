package com.devbliss.doctest;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;

import com.devbliss.doctest.templates.AssertDocItem;
import com.devbliss.doctest.templates.DocItem;
import com.devbliss.doctest.templates.RequestDocItem;
import com.devbliss.doctest.templates.SectionDocItem;
import com.devbliss.doctest.templates.Templates;
import com.devbliss.doctest.templates.TextDocItem;
import com.devbliss.doctest.utils.JSONHelper;
import com.google.inject.Inject;

import de.devbliss.apitester.ApiTest.HTTP_REQUEST;

public class DocTestMachineImpl implements DocTestMachine {

    // The class under test.
    // Usually I don't like that, but we don't have another option to generate
    // the name under test because of the static way JUnit runs files and uses
    // AfterClass and BeforeClass.
    private String className;

    public StringBuffer outputOfTestsBuffer = new StringBuffer();
    private final Templates templates;
    private final List<DocItem> listTemplates;

    private final ReportRenderer reportRenderer;
    private final JSONHelper jsonHelper;

    @Inject
    public DocTestMachineImpl(
            Templates templates,
            ReportRenderer reportRenderer,
            JSONHelper jsonHelper) {
        listTemplates = new ArrayList<DocItem>();
        this.reportRenderer = reportRenderer;
        this.templates = templates;
        this.jsonHelper = jsonHelper;
    }

    /**
     * Main method => this lets you write out stuff.
     * 
     * Usually after a say a newline is appended.
     * 
     */
    public void say(String say) {
        listTemplates.add(new TextDocItem(say));
    }

    /**
     * Inits the new file for writing stuff out.
     */
    public void beginDoctest(String className) {
        if (this.className == null) {
            this.className = className;
        }
    }

    /**
     * This would be a header. Maybe a new test inside a testcase.
     */
    public void sayNextSection(String sectionName) {
        listTemplates.add(new SectionDocItem(sectionName));
    }

    /**
     * At the end of a successful test stuff gets written out.
     */
    public void endDocTest() {
        reportRenderer.render(listTemplates, className);
        className = null;
    }

    public void sayRequest(URI uri, String payload, HTTP_REQUEST httpRequest) throws JSONException {
        if (uri != null) {
            listTemplates.add(new RequestDocItem(httpRequest, uri.toString(), getJson(payload)));
        }
    }

    public void sayResponse(int responseCode, String payload) throws Exception {
        listTemplates.add(new com.devbliss.doctest.templates.ResponseDocItem(responseCode,
                getJson(payload)));
    }

    public void sayVerify(String condition) {
        listTemplates.add(new AssertDocItem(condition));
    }

    public void sayPreformatted(String preformattedText) {
        say(templates.getJsonTemplate(preformattedText));
    }

    private String getJson(String json) throws JSONException {
        if (jsonHelper.isJsonValid(json)) {
            return json;
        } else {
            return "";
        }
    }
}
