package com.devbliss.doctest.httpwithoutredirect;

import java.io.IOException;
import java.net.URI;

import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;

import com.google.gson.Gson;
import com.google.inject.Inject;

import de.devbliss.apitester.factory.PostFactory;

public class PostWithoutRedirectImpl implements PostFactory {

    private static final String ENCODING = "UTF-8";
    private final Gson gson;

    @Inject
    public PostWithoutRedirectImpl(Gson gson) {
        this.gson = gson;
    }

    public HttpGet createGetRequest(URI uri) throws IOException {
        HttpGet httpGet = new HttpGet(uri);
        HttpParams params = new BasicHttpParams();
        params.setParameter("http.protocol.handle-redirects", false);
        httpGet.setParams(params);
        return httpGet;
    }

    public HttpPost createPostRequest(URI uri, Object payload) throws IOException {
        HttpPost httpPost = new HttpPost(uri);
        HttpParams params = new BasicHttpParams();
        params.setParameter("http.protocol.handle-redirects", false);
        httpPost.setParams(params);

        if (payload != null) {
            String json = gson.toJson(payload);
            StringEntity entity = new StringEntity(json, ENCODING);
            entity.setContentType(ContentType.APPLICATION_JSON.getMimeType());
            httpPost.setEntity(entity);
        }

        return httpPost;
    }

}
