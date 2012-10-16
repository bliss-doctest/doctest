package com.devbliss.doctest.items;

import java.util.List;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.http.Header;
import org.apache.http.HttpRequest;

import de.devbliss.apitester.ApiTest.HTTP_REQUEST;

public class RequestUploadDocItem extends RequestDocItem {

    private static int NB_ITEMS = 0;

    private final String id;
    private final String fileName;
    private final String fileBody;
    private final long fileSize;
    private final String mimeType;

    public RequestUploadDocItem(
            HTTP_REQUEST http,
            HttpRequest httpRequest,
            String fileName,
            String fileBody,
            long fileSize,
            String mimeType,
            List<String> headersToShow) {
        super(http, httpRequest.getRequestLine().getUri());
        this.fileName = fileName;
        this.fileBody = fileBody;
        this.isAnUploadRequest = true;
        this.fileSize = fileSize;
        this.mimeType = mimeType;
        this.id = "request" + NB_ITEMS++;
        this.headers.clear();
        for (Header header : httpRequest.getAllHeaders()) {
            if (headersToShow.contains(header.getName())) {
                headers.put(header.getName(), header.getValue());
            }
        }
    }

    public String getFileName() {
        return fileName;
    }

    public String getFileBody() {
        return fileBody;
    }

    public String getHtmlEscapedFileBody() {
        return StringEscapeUtils.escapeHtml(fileBody);
    }

    @Override
    public String getItemName() {
        return "request";
    }

    public long getFileSize() {
        return fileSize;
    }

    public String getMimeType() {
        return mimeType;
    }

    public String getId() {
        return id;
    }

    public boolean getShowFileBody() {
        return "text/plain".equals(mimeType);
    }
}
