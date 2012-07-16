package com.devbliss.doctest.renderer.html;

import com.devbliss.doctest.renderer.AbstractReportRenderer;

/**
 * Abstract ReportRenderer for the html output format which defines some variables used both in
 * {@link HtmlRenderer} and {@link IndexFileGenerator}.
 * 
 * @author bmary
 * 
 */
public abstract class AbstractHtmlReportRenderer extends AbstractReportRenderer {

    protected final static String INDEX = "index";
    protected final static String HTML_EXTENSION = ".html";
    protected final HtmlItems htmlItems;

    public AbstractHtmlReportRenderer(HtmlItems htmlItems) {
        this.htmlItems = htmlItems;
    }
}
