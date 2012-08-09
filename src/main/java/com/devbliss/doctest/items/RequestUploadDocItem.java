package com.devbliss.doctest.items;

import de.devbliss.apitester.ApiTest.HTTP_REQUEST;

public class RequestUploadDocItem extends RequestDocItem {

    private String fileName;
    private String fileBody;

    public RequestUploadDocItem(HTTP_REQUEST http, String uri, String fileName, String fileBody) {
        super(http, uri);
        this.fileName = fileName;
        this.fileBody = fileBody;
        this.isAnUploadRequest = true;
    }

    public String getFileName() {
        return fileName;
    }

    public String getFileBody() {
        return fileBody;
    }

    public String getItemName() {
        return "request";
    }
}
