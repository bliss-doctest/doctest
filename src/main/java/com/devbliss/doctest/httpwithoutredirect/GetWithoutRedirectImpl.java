package com.devbliss.doctest.httpwithoutredirect;

import java.io.IOException;
import java.net.URI;

import org.apache.http.client.methods.HttpGet;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;

import de.devbliss.apitester.factory.GetFactory;

public class GetWithoutRedirectImpl implements GetFactory {

    public HttpGet createGetRequest(URI uri) throws IOException {
        HttpGet httpGet = new HttpGet(uri);
        HttpParams params = new BasicHttpParams();
        params.setParameter("http.protocol.handle-redirects", false);
        httpGet.setParams(params);
        return httpGet;
    }

}
