package com.devbliss.doctest.machine;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;

import com.devbliss.doctest.items.AssertDocItem;
import com.devbliss.doctest.items.DocItem;
import com.devbliss.doctest.items.JsonDocItem;
import com.devbliss.doctest.items.MultipleTextDocItem;
import com.devbliss.doctest.items.RequestDocItem;
import com.devbliss.doctest.items.RequestUploadDocItem;
import com.devbliss.doctest.items.ResponseDocItem;
import com.devbliss.doctest.items.SectionDocItem;
import com.devbliss.doctest.items.TextDocItem;
import com.devbliss.doctest.renderer.ReportRenderer;
import com.devbliss.doctest.utils.JSONHelper;
import com.devbliss.doctest.utils.UriHelper;
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

    // The name of the report.
    // Usually I don't like that, but we don't have another option to generate
    // the name under test because of the static way JUnit runs files and uses
    // AfterClass and BeforeClass.
    private String fileName;

    public StringBuffer outputOfTestsBuffer = new StringBuffer();
    private final List<DocItem> listItem;

    private final ReportRenderer reportRenderer;
    private final JSONHelper jsonHelper;
    private final UriHelper uriHelper;

    @Inject
    public DocTestMachineImpl(
            ReportRenderer reportRenderer,
            JSONHelper jsonHelper,
            UriHelper uriHelper) {
        this.uriHelper = uriHelper;
        listItem = new ArrayList<DocItem>();
        this.reportRenderer = reportRenderer;
        this.jsonHelper = jsonHelper;
    }

    public void beginDoctest(String fileName, String introduction) {
        if (this.fileName == null) {
            this.fileName = fileName;
            if (introduction != null && !introduction.isEmpty()) {
                listItem.add(new TextDocItem(introduction));
            }
        }
    }

    public void endDocTest() throws Exception {
        reportRenderer.render(listItem, fileName);
        fileName = null;
    }

    public void prepareDocTest() {
        getListItem().clear();
    }

    protected List<DocItem> getListItem() {
        return listItem;
    }

    public void say(String say) {
        listItem.add(new TextDocItem(say));
    }

    public void sayNextSectionTitle(String sectionName) {
        listItem.add(new SectionDocItem(sectionName));
    }

    public void sayRequest(URI uri, String payload, HTTP_REQUEST httpRequest) throws JSONException {
        if (uri != null) {
            listItem.add(new RequestDocItem(httpRequest, uriHelper.uriToString(uri),
                    getPayload(payload)));
        }
    }

    public void sayUploadRequest(URI uri, HTTP_REQUEST httpRequest, String fileName,
            String fileBody, long size) throws JSONException {
        sayUploadRequest(uri, httpRequest, fileName, fileBody, size, null);
    }

    public void sayUploadRequest(URI uri, HTTP_REQUEST httpRequest, String fileName,
            String fileBody, long size, String mimeType) {
        if (uri != null) {
            listItem.add(new RequestUploadDocItem(httpRequest, uriHelper.uriToString(uri),
                    fileName, fileBody, size, mimeType));
        }
    }

    public void sayResponse(int responseCode, String payload) throws Exception {
        listItem.add(new ResponseDocItem(responseCode, getPayload(payload)));
    }

    public void sayVerify(String condition) {
        listItem.add(new AssertDocItem(condition));
    }

    public void sayPreformatted(String preformattedText) {
        listItem.add(new JsonDocItem(preformattedText));
    }

    private String getPayload(String payload) {
        String payloadToShow;
        if (jsonHelper.isJsonValid(payload)) {
            payloadToShow = jsonHelper.prettyPrintJson(payload);
        } else if (null == payload) {
            payloadToShow = "";
        } else {
            payloadToShow = payload;
        }
        return payloadToShow;
    }

    public void say(String say, String[] strings) {
        listItem.add(new MultipleTextDocItem(say, strings));
    }

}
