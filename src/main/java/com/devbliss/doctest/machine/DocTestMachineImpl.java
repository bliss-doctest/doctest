package com.devbliss.doctest.machine;

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
import com.devbliss.doctest.utils.HeadersCookiesHelper;
import com.devbliss.doctest.utils.JSONHelper;
import com.devbliss.doctest.utils.UriHelper;
import com.google.inject.Inject;

import de.devbliss.apitester.ApiRequest;
import de.devbliss.apitester.ApiResponse;

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
    private final HeadersCookiesHelper headersHelper;
    private String introduction;

    @Inject
    public DocTestMachineImpl(
            ReportRenderer reportRenderer,
            JSONHelper jsonHelper,
            UriHelper uriHelper,
            HeadersCookiesHelper headersHelper) {
        this.uriHelper = uriHelper;
        listItem = new ArrayList<DocItem>();
        this.reportRenderer = reportRenderer;
        this.jsonHelper = jsonHelper;
        this.headersHelper = headersHelper;
    }

    public void beginDoctest(String fileName, String introduction) {
        if (this.fileName == null) {
            this.fileName = fileName;
            if (this.introduction == null) {
                this.introduction = introduction;
            }
        }
    }

    public void endDocTest() throws Exception {
        reportRenderer.render(listItem, fileName, introduction);
        fileName = null;
        introduction = null;
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

    /**
     * if apiRequest's uri is null, no documentation for this request/response will be created
     * 
     */
    public void sayRequest(ApiRequest apiRequest, String payload, List<String> headersToShow,
            List<String> cookiesToShow) throws JSONException {

        if (apiRequest.uri != null) {
            listItem.add(new RequestDocItem(apiRequest.httpMethod, uriHelper
                    .uriToString(apiRequest.uri), validateAndPrettifyJson(payload), headersHelper
                    .filter(apiRequest.headers, headersToShow), headersHelper.filter(
                    apiRequest.cookies, cookiesToShow)));
        }
    }

    /**
     * if apiRequest's uri is null, no documentation for this request/response will be created
     * 
     */
    public void sayUploadRequest(ApiRequest apiRequest, String fileName, String fileBody,
            long size, String mimeType, List<String> headersToShow, List<String> cookiesToShow) {

        if (apiRequest.uri != null) {
            listItem.add(new RequestUploadDocItem(apiRequest.httpMethod, uriHelper
                    .uriToString(apiRequest.uri), fileName, fileBody, size, mimeType, headersHelper
                    .filter(apiRequest.headers, headersToShow), headersHelper.filter(
                    apiRequest.cookies, cookiesToShow)));
        }
    }

    /**
     * add new item for doctest
     * and filter the headers from the ApiResponse
     */
    public void sayResponse(ApiResponse response, List<String> headersToShow) throws Exception {
        listItem.add(new ResponseDocItem(response, validateAndPrettifyJson(response.payload),
                headersHelper.filter(response.headers, headersToShow)));
    }

    public void sayVerify(String condition) {
        listItem.add(new AssertDocItem(condition));
    }

    public void sayPreformatted(String preformattedText) {
        listItem.add(new JsonDocItem(preformattedText));
    }

    /**
     * Checks whether the given {@link String} is a valid Json.
     * If the json is valid, it will be prettyfied.
     * 
     * @param payload
     * @return
     */
    private String validateAndPrettifyJson(String payload) {
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
