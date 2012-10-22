package com.devbliss.doctest.items;

import java.util.Map;

import org.apache.commons.lang.StringEscapeUtils;

public class RequestUploadDocItem extends RequestDocItem {

    private static int NB_ITEMS = 0;

    private final String id;
    private final String fileName;
    private final String fileBody;
    private final long fileSize;
    private final String mimeType;

    public RequestUploadDocItem(
            String http,
            String uri,
            String fileName,
            String fileBody,
            long fileSize,
            String mimeType,
            Map<String, String> headersToShow,
            Map<String, String> cookies) {
        super(http, uri, headersToShow, cookies);
        this.fileName = fileName;
        this.fileBody = fileBody;
        this.isAnUploadRequest = true;
        this.fileSize = fileSize;
        this.mimeType = mimeType;
        this.id = "request" + NB_ITEMS++;
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
