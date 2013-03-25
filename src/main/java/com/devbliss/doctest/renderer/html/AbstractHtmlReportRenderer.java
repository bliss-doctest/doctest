/*
 * Copyright 2013, devbliss GmbH
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */

package com.devbliss.doctest.renderer.html;

import com.devbliss.doctest.renderer.ReportRenderer;

/**
 * Abstract ReportRenderer for the html output format which defines some variables used both in
 * {@link HtmlRenderer} and {@link HtmlIndexFileRenderer}.
 * 
 * @author bmary
 * 
 */
public abstract class AbstractHtmlReportRenderer implements ReportRenderer {

    protected final static String INDEX = "index";
    protected final static String HTML_EXTENSION = ".html";
    protected final HtmlItems htmlItems;

    public AbstractHtmlReportRenderer(HtmlItems htmlItems) {
        this.htmlItems = htmlItems;
    }
}
