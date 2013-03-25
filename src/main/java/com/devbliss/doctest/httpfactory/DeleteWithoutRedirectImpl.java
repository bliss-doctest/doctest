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

package com.devbliss.doctest.httpfactory;

import static com.devbliss.doctest.httpfactory.HttpConstants.ENCODING;
import static com.devbliss.doctest.httpfactory.HttpConstants.HANDLE_REDIRECTS;

import java.io.IOException;
import java.net.URI;

import org.apache.http.client.methods.HttpDelete;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;

import com.google.gson.Gson;
import com.google.inject.Inject;

import de.devbliss.apitester.factory.DeleteFactory;
import de.devbliss.apitester.factory.HttpDeleteWithBody;

/**
 * Implements a DELETE HTTP request which does not handle any redirect.
 * 
 * @author bmary
 * 
 */
public class DeleteWithoutRedirectImpl implements DeleteFactory {

    private final Gson gson;

    @Inject
    public DeleteWithoutRedirectImpl(Gson gson) {
        this.gson = gson;
    }

    public HttpDeleteWithBody createDeleteRequest(URI uri, Object payload) throws IOException {
        HttpDeleteWithBody httpDelete = new HttpDeleteWithBody(uri);
        HttpParams params = new BasicHttpParams();
        params.setParameter(HANDLE_REDIRECTS, false);
        httpDelete.setParams(params);

        if (payload != null) {
            String json = gson.toJson(payload);
            StringEntity entity = new StringEntity(json, ENCODING);
            entity.setContentType(ContentType.APPLICATION_JSON.getMimeType());
            httpDelete.setEntity(entity);
        }

        return httpDelete;
    }

    public HttpDelete createDeleteRequest(URI uri) throws IOException {
        HttpDelete httpDelete = new HttpDelete(uri);
        HttpParams params = new BasicHttpParams();
        params.setParameter(HANDLE_REDIRECTS, false);
        httpDelete.setParams(params);
        return httpDelete;
    }

}
