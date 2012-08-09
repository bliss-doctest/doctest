package com.devbliss.doctest.httpfactory;

import static com.devbliss.doctest.httpfactory.HttpConstants.HANDLE_REDIRECTS;

import java.io.IOException;
import java.net.URI;

import org.apache.http.client.methods.HttpGet;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;

import de.devbliss.apitester.factory.GetFactory;

/**
 * Implements a GET HTTP request which does not handle any redirect.
 * 
 * @author bmary
 * 
 */
public class GetWithoutRedirectImpl implements GetFactory {

    public HttpGet createGetRequest(URI uri) throws IOException {
        HttpGet httpGet = new HttpGet(uri);
        HttpParams params = new BasicHttpParams();
        params.setParameter(HANDLE_REDIRECTS, false);
        httpGet.setParams(params);
        return httpGet;
    }

}
