package com.devbliss.doctest.machine;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;

import com.devbliss.doctest.items.AssertDocItem;
import com.devbliss.doctest.items.DocItem;
import com.devbliss.doctest.items.JsonDocItem;
import com.devbliss.doctest.items.RequestDocItem;
import com.devbliss.doctest.items.ResponseDocItem;
import com.devbliss.doctest.items.SectionDocItem;
import com.devbliss.doctest.items.TextDocItem;
import com.devbliss.doctest.renderer.ReportRenderer;
import com.devbliss.doctest.utils.JSONHelper;
import com.google.inject.Inject;

import de.devbliss.apitester.ApiTest.HTTP_REQUEST;

/**
 * Default implementation of {@link DocTestMachine}.
 * <p>
 * This class ownes a list of {@link DocItem} : {@link #listItem}. Each time a say method is called,
 * a {@link DocItem} is added to {@link #listItem}. <br/>
 * At the end of the workflow, the method {@link #endDocTest()} is called and uses a
 * {@link ReportRenderer} to render the {@link #listItem} .
 * </p>
 * 
 * @author bmary
 * 
 */
public class DocTestMachineImpl implements DocTestMachine {

    // The class under test.
    // Usually I don't like that, but we don't have another option to generate
    // the name under test because of the static way JUnit runs files and uses
    // AfterClass and BeforeClass.
    private String className;

    public StringBuffer outputOfTestsBuffer = new StringBuffer();
    private final List<DocItem> listItem;

    private final ReportRenderer reportRenderer;
    private final JSONHelper jsonHelper;

    @Inject
    public DocTestMachineImpl(ReportRenderer reportRenderer, JSONHelper jsonHelper) {
        listItem = new ArrayList<DocItem>();
        this.reportRenderer = reportRenderer;
        this.jsonHelper = jsonHelper;
    }

    public void beginDoctest(String className) {
        if (this.className == null) {
            this.className = className;
        }
    }

    public void endDocTest() throws Exception {
        reportRenderer.render(listItem, className);
        className = null;
    }

    public void say(String say) {
        listItem.add(new TextDocItem(say));
    }

    public void sayNextSectionTitle(String sectionName) {
        listItem.add(new SectionDocItem(sectionName));
    }

    public void sayRequest(URI uri, String payload, HTTP_REQUEST httpRequest) throws JSONException {
        if (uri != null) {
            listItem.add(new RequestDocItem(httpRequest, uri.toString(), getJson(payload)));
        }
    }

    public void sayResponse(int responseCode, String payload) throws Exception {
        listItem.add(new ResponseDocItem(responseCode, getJson(payload)));
    }

    public void sayVerify(String condition) {
        listItem.add(new AssertDocItem(condition));
    }

    public void sayPreformatted(String preformattedText) {
        listItem.add(new JsonDocItem(preformattedText));
    }

    private String getJson(String json) {
        String jsonToShow;
        if (jsonHelper.isJsonValid(json)) {
            jsonToShow = json;
        } else {
            jsonToShow = NOT_VALID_JSON + json;
        }
        return jsonToShow;
    }

}
