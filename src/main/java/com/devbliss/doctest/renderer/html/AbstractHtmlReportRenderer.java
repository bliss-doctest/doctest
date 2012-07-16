package com.devbliss.doctest.renderer.html;

import com.devbliss.doctest.renderer.AbstractReportRenderer;

public abstract class AbstractHtmlReportRenderer extends AbstractReportRenderer {

    protected final static String HTML_EXTENSION = ".html";
    protected final HtmlItems htmlItems;

    public AbstractHtmlReportRenderer(HtmlItems htmlItems) {
        this.htmlItems = htmlItems;
    }
}
