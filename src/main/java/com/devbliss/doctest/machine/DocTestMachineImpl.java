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
import com.devbliss.doctest.utils.FilterHelper;
import com.devbliss.doctest.utils.JSONHelper;
import com.devbliss.doctest.utils.UriHelper;
import com.google.inject.Inject;

import de.devbliss.apitester.ApiRequest;
import de.devbliss.apitester.ApiResponse;

/**
 * Default implementation of {@link DocTestMachine}.
 * <p>
 * This class ownes a list of {@link DocItem} : {@link #listItem}. Each time a say method is called, a {@link DocItem}
 * is added to {@link #listItem}. <br/>
 * At the end of the workflow, the method {@link #endDocTest()} is called and uses a {@link ReportRenderer} to render
 * the {@link #listItem} .
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
    private final FilterHelper filterHelper;
    private String introduction;

    @Inject
    public DocTestMachineImpl(
            ReportRenderer reportRenderer,
            JSONHelper jsonHelper,
            UriHelper uriHelper,
            FilterHelper headersHelper) {
        this.uriHelper = uriHelper;
        listItem = new ArrayList<DocItem>();
        this.reportRenderer = reportRenderer;
        this.jsonHelper = jsonHelper;
        this.filterHelper = headersHelper;
    }

    @Override
    public void beginDoctest(String fileName, String introduction) {
        if (this.fileName == null) {
            this.fileName = fileName;
            if (this.introduction == null) {
                this.introduction = introduction;
            }
        }
    }

    @Override
    public void endDocTest() throws Exception {
        reportRenderer.render(listItem, fileName, introduction);
        fileName = null;
        introduction = null;
    }

    @Override
    public void prepareDocTest() {
        getListItem().clear();
    }

    protected List<DocItem> getListItem() {
        return listItem;
    }

    @Override
    public void say(String say) {
        listItem.add(new TextDocItem(say));
    }

    @Override
    public void sayNextSectionTitle(String sectionName) {
        listItem.add(new SectionDocItem(sectionName));
    }

    /**
     * if apiRequest's uri is null, no documentation for this request/response will be created
     * 
     */
    @Override
    public void sayRequest(ApiRequest apiRequest, String payload, List<String> headersToShow,
            List<String> cookiesToShow) throws JSONException {

        if (apiRequest.uri != null) {
            listItem.add(new RequestDocItem(apiRequest.httpMethod, uriHelper
                    .uriToString(apiRequest.uri), validateAndPrettifyPayload(apiRequest.getHeader("Content-Type"), payload), filterHelper
                    .filterMap(apiRequest.headers, headersToShow), filterHelper.filterMap(
                    apiRequest.cookies, cookiesToShow)));
        }
    }

    /**
     * if apiRequest's uri is null, no documentation for this request/response will be created
     * 
     */
    @Override
    public void sayUploadRequest(ApiRequest apiRequest, String fileName, String fileBody,
            long size, String mimeType, List<String> headersToShow, List<String> cookiesToShow) {

        if (apiRequest.uri != null) {
            listItem.add(new RequestUploadDocItem(apiRequest.httpMethod, uriHelper
                    .uriToString(apiRequest.uri), fileName, fileBody, size, mimeType, filterHelper
                    .filterMap(apiRequest.headers, headersToShow), filterHelper.filterMap(
                    apiRequest.cookies, cookiesToShow)));
        }
    }

    /**
     * add new item for doctest
     * and filter the headers from the ApiResponse
     */
    @Override
    public void sayResponse(ApiResponse response, List<String> headersToShow) throws Exception {
        listItem.add(new ResponseDocItem(response, validateAndPrettifyPayload(response.getHeader("Content-Type"), response.payload),
                filterHelper.filterMap(response.headers, headersToShow)));
    }

    @Override
    public void sayVerify(String condition) {
        listItem.add(new AssertDocItem(condition));
    }

    @Override
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
    private String validateAndPrettifyPayload(String contentType, String payload) {
        if (contentType != null) {
            contentType = contentType.toLowerCase();
        } else {
            contentType = "";
        }

        String payloadToShow;
        if (null == payload) {
            payloadToShow = "";
        } else if (contentType.contains("html")) {
            payloadToShow = "<xmp>" + payload + "</xmp>";
        } else if (contentType.contains("json") || jsonHelper.isJsonValid(payload)) {
            payloadToShow = jsonHelper.prettyPrintJson(payload);
        } else {
            payloadToShow = payload;
        }
        return payloadToShow;
    }

    @Override
    public void say(String say, String[] strings) {
        listItem.add(new MultipleTextDocItem(say, strings));
    }
}
