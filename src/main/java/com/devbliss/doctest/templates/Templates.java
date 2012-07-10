package com.devbliss.doctest.templates;

import de.devbliss.apitester.ApiTest.HTTP_REQUEST;

public class Templates {

    private static final String CLEAR_FLOAT = "<div style=\"clear:left;\"></div>";
    private static final String PAYLOAD_LI = "<li><div>payload:</div>{payload}</li>";
    private static final String STYLE_REQUEST =
            "margin-left:20px; border: 1px solid grey; padding: 5px; float:left;";
    private static final String STYLE_JSON =
            "margin-left:20px; background: #F1F8E0; border: 1px solid #0B3B0B; padding: 5px; color:#0B3B0B; float:left;";
    private static final String STYLE_VERIFY =
            "margin-left: 100px; padding: 5px; border: 5px solid green; float: left;";

    public final static String REQUEST = "<div style=\"" + STYLE_REQUEST
            + "\"><span>Request</span><ul><li>HTTP:{HTTP}</li><li>URI:{uri}</li>" + PAYLOAD_LI
            + "</ul></div>" + CLEAR_FLOAT;
    public final static String JSON = "<pre style=\"" + STYLE_JSON + "\">" + "<code>{data}</code>"
            + "</pre>" + CLEAR_FLOAT;
    public final static String RESPONSE = "<div style=\"" + STYLE_REQUEST
            + "\"><span>Response</span><ul><li>ResponseCode: {responseCode}</li>" + PAYLOAD_LI
            + "</ul></div>" + CLEAR_FLOAT;
    public final static String VERIFY = "<div style =\"" + STYLE_VERIFY
            + "\">: '{value}' which is correct!</div>" + CLEAR_FLOAT;

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
}
