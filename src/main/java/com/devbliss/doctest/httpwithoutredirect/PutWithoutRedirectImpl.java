package com.devbliss.doctest.httpwithoutredirect;

import java.io.IOException;
import java.net.URI;

import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;

import com.google.gson.Gson;
import com.google.inject.Inject;

import de.devbliss.apitester.factory.PutFactory;

public class PutWithoutRedirectImpl implements PutFactory {

    private static final String ENCODING = "UTF-8";
    private final Gson gson;

    @Inject
    public PutWithoutRedirectImpl(Gson gson) {
        this.gson = gson;
    }

    public HttpPut createPutRequest(URI uri, Object payload) throws IOException {
        HttpPut httpPut = new HttpPut(uri);
        HttpParams params = new BasicHttpParams();
        params.setParameter("http.protocol.handle-redirects", false);
        httpPut.setParams(params);

        if (payload != null) {
            String json = gson.toJson(payload);
            StringEntity entity = new StringEntity(json, ENCODING);
            entity.setContentType(ContentType.APPLICATION_JSON.getMimeType());
            httpPut.setEntity(entity);
        }

        return httpPut;
    }

}
