package com.devbliss.doctest.renderer.html;

import de.devbliss.apitester.ApiTest.HTTP_REQUEST;

public class HtmlItems {

    private static final String PAYLOAD_LI = "<li><div>payload:</div>{payload}</li>";

    private final static String REQUEST =
            "<div class=\"box\"><span>Request</span><ul><li>HTTP:{HTTP}</li><li>URI:{uri}</li>"
                    + PAYLOAD_LI + "</ul></div>";
    private final static String JSON = "<pre>" + "<code>{data}</code>" + "</pre>";
    private final static String RESPONSE =
            "<div class=\"box\"><span>Response</span><ul><li>ResponseCode: {responseCode}</li>"
                    + PAYLOAD_LI + "</ul></div>";
    private final static String VERIFY = "<div class=\"box correct\">'{value}' is correct!</div>";
    private final static String SECTION =
            "<h1><a name=\"{name}\"> {value} </a><a class=\"link-to-top\" href=\"#\">(top)</a></h1>";
    private final static String MENU_SECTION =
            "<div class=\"menu\">{title}:<ul>{elements}</ul></div>";
    private final static String LI = "<li><a href=\"{sectionId}\">{sectionName}</a></li>";
    private final static String LINK = "<a href=\"{href}\">{text}</a><br/>";
    private final static String HEADER_FORMAT = "<head><style>" + HtmlStyle.getCss() + "</style>"
            + "<title>DocTest for class {name}</title></head>";
    private final static String HTML_FORMAT =
            "<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01//EN\" \"http://www.w3.org/TR/html4/strict.dtd\">"
                    + "<html>{html}</html>";
    private final static String BODY_FORMAT =
            "<body><div class=\"container\"><div class=\"wrapper\">"
                    + "<br/>{body}</div></div><body>";

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

    public String getSectionTemplate(String title, String sectionId) {
        return SECTION.replace("{value}", title).replace("{name}", sectionId);
    }

    public String getLiWithLinkTemplate(String sectionId, String sectionName) {
        return LI.replace("{sectionId}", sectionId).replace("{sectionName}", sectionName);
    }

    public String getLiMenuTemplate(String title, String sections) {
        return MENU_SECTION.replace("{title}", title).replace("{elements}", sections);
    }

    public String getLinkTemplate(String href, String text) {
        return LINK.replace("{href}", href).replace("{text}", text);
    }

    public String getHeaderFormatTemplate(String name) {
        return HEADER_FORMAT.replace("{name}", name);
    }

    public String getHtmlTemplate(String html) {
        return HTML_FORMAT.replace("{html}", html);
    }

    public String getBodyTemplate(String body) {
        return BODY_FORMAT.replace("{body}", body);
    }
}
