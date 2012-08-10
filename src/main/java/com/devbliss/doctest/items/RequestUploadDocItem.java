package com.devbliss.doctest.items;

import de.devbliss.apitester.ApiTest.HTTP_REQUEST;

public class RequestUploadDocItem extends RequestDocItem {

    private static int NB_ITEMS = 0;

    private String id;
    private String fileName;
    private String fileBody;
    private long fileSize;

    public RequestUploadDocItem(
            HTTP_REQUEST http,
            String uri,
            String fileName,
            String fileBody,
            long fileSize) {
        super(http, uri);
        this.fileName = fileName;
        this.fileBody = fileBody;
        this.isAnUploadRequest = true;
        this.fileSize = fileSize;
        this.id = "request" + NB_ITEMS++;
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

    public long getFileSize() {
        return fileSize;
    }

    public String getId() {
        return id;
    }
}
