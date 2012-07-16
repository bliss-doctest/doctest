package com.devbliss.doctest.renderer.html;

import de.devbliss.apitester.ApiTest.HTTP_REQUEST;

public class HtmlItems {

    private static final String PAYLOAD_LI = "<li><div>payload:</div>{payload}</li>";

    public final static String REQUEST =
            "<div class=\"box\"><span>Request</span><ul><li>HTTP:{HTTP}</li><li>URI:{uri}</li>"
                    + PAYLOAD_LI + "</ul></div>";
    public final static String JSON = "<pre>" + "<code>{data}</code>" + "</pre>";
    public final static String RESPONSE =
            "<div class=\"box\"><span>Response</span><ul><li>ResponseCode: {responseCode}</li>"
                    + PAYLOAD_LI + "</ul></div>";
    public final static String VERIFY = "<div class=\"box correct\">'{value}' is correct!</div>";

    public final static String SECTION = "<h1> {value} </h1>";

    public String getJsonTemplate(String json) {
        return JSON.replace("{data}", json);
    }

    public String getUriTemplate(String uri, String payload, HTTP_REQUEST httpRequest) {
        String request = REQUEST.replace("{uri}", uri);
        if (!payload.isEmpty()) {
            request = request.replace("{payload}", payload);
        } else {
            request = request.replace(PAYLOAD_LI, "");
        }
        return request.replace("{HTTP}", httpRequest.name());
    }

    public String getResponseTemplate(int responseCode, String payload) {
        String response = RESPONSE.replace("{responseCode}", String.valueOf(responseCode));
        if (!payload.isEmpty()) {
            response = response.replace("{payload}", payload);
        } else {
            response = response.replace(PAYLOAD_LI, "");
        }
        return response;
    }

    public String getVerifyTemplate(String expected) {
        return VERIFY.replace("{value}", expected);
    }

    public String getSectionTemplate(String title) {
        return SECTION.replace("{value}", title);
    }
}
