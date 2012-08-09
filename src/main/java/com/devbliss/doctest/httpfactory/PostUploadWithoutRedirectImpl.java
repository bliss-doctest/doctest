package com.devbliss.doctest.httpfactory;

import java.io.IOException;
import java.net.URI;

import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;

import de.devbliss.apitester.factory.PostFactory;

/**
 * Implements a POST HTTP request which does not handle any redirect.
 * 
 * @author bmary
 * 
 */
public class PostUploadWithoutRedirectImpl implements PostFactory {

    private final String paramName;
    private final FileBody fileBodyToUpload;

    public PostUploadWithoutRedirectImpl(String paramName, FileBody fileToUpload) {
        this.paramName = paramName;
        this.fileBodyToUpload = fileToUpload;
    }

    public HttpPost createPostRequest(URI uri, Object payload) throws IOException {
        HttpPost httpPost = new HttpPost(uri);
        HttpParams params = new BasicHttpParams();
        params.setParameter(HttpConstants.HANDLE_REDIRECTS, false);
        httpPost.setParams(params);

        MultipartEntity entity = new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE);
        entity.addPart(paramName, fileBodyToUpload);
        httpPost.setEntity(entity);

        return httpPost;
    }

}
