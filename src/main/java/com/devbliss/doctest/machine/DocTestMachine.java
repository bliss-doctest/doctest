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

import java.util.List;

import org.json.JSONException;

import com.devbliss.doctest.items.AssertDocItem;
import com.devbliss.doctest.items.JsonDocItem;
import com.devbliss.doctest.items.RequestDocItem;
import com.devbliss.doctest.items.ResponseDocItem;
import com.devbliss.doctest.items.SectionDocItem;
import com.devbliss.doctest.items.TextDocItem;

import de.devbliss.apitester.ApiRequest;
import de.devbliss.apitester.ApiResponse;

/**
 * The {@link DocTestMachine} offers method to generate a report. <code>
 * <ol>
 * <li>First of all, the {@link #beginDoctest(String)} method must be called to initialize the report.</li>
 * <li>Then, the different say methods give the possibility to write some informations into the report
 * by using several templates (Request, response, assert)</li>
 * <li>The method {@link #endDocTest()} ends up the generation of the file.</li>
 * </ol>
 * </code>
 * 
 * @author bmary
 * 
 */
public interface DocTestMachine {

    public static final String NOT_VALID_JSON = "The payload is not a valid json: ";

    /**
     * Begins a report if there is no on-going generation.
     * 
     * @param className The name of the tested class, which is going to be the name of the file.
     * @param introduction
     */
    void beginDoctest(String fileName, String introduction);

    /**
     * Ends up the generation of the report.
     * 
     * @throws Exception
     */
    void endDocTest() throws Exception;

    /**
     * Clears the internal state of the DocTest to be ready for another run.
     * 
     */
    void prepareDocTest() throws Exception;

    /**
     * Writes a new {@link TextDocItem} into the report.
     * 
     * @param say Text to add
     */
    void say(String say);

    /**
     * Writes a new {@link TextDocItem} into the report supporting %s replacements.
     * 
     * @param say
     * @param strings
     */
    void say(String say, String[] strings);

    /**
     * Writes a new {@link SectionDocItem} into the report.
     * 
     * @param sectionTitle Title of the new section
     */
    void sayNextSectionTitle(String sectionTitle);

    /**
     * Writes a new {@link RequestDocItem} into the report.
     * 
     * @param uri
     * @param payload
     * @param httpRequest
     * @throws JSONException
     */
    void sayRequest(ApiRequest apiRequest, String payload, List<String> headersToShow,
            List<String> cookiesToShow) throws JSONException;

    /**
     * Writes a new {@link AssertDocItem} into the report.
     * 
     * @param condition
     */
    void sayVerify(String condition);

    /**
     * Writes a new {@link JsonDocItem} into the report.
     * 
     * @param preformattedText
     */
    void sayPreformatted(String preformattedText);

    void sayUploadRequest(ApiRequest httpRequest, String fileName, String fileBody, long l,
            String mimetype, List<String> headersToShow, List<String> cookiesToShow)
            throws JSONException;

    /**
     * Writes a new {@link ResponseDocItem} into the report.
     * 
     * @param response
     * @throws Exception
     */
    void sayResponse(ApiResponse response, List<String> headersToShow) throws Exception;
}
