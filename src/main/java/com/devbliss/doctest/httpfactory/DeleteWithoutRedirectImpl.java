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
