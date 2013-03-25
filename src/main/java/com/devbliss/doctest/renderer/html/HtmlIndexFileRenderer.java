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

import java.util.List;

import com.devbliss.doctest.items.DocItem;
import com.devbliss.doctest.items.IndexFileDocItem;
import com.devbliss.doctest.items.LinkDocItem;
import com.devbliss.doctest.items.MenuDocItem;
import com.devbliss.doctest.utils.FileHelper;
import com.google.inject.Inject;

/**
 * Simply gets all files from the doctests directory and lists them in a
 * index.html.
 * 
 * This approach is really stupid, but the cool thing is that it works without
 * using a TestRunner or Testsuite. Zero conf and hazzle free.
 * 
 * Means each test driven by DocTestMachineImpl generates a new index.html. But
 * this should be cheap in terms of time consumed.
 * 
 * @author rbauer, bmary
 * 
 */
public class HtmlIndexFileRenderer extends AbstractHtmlReportRenderer {

    private final FileHelper fileHelper;

    @Inject
    public HtmlIndexFileRenderer(HtmlItems htmlItems, FileHelper fileHelper) {
        super(htmlItems);
        this.fileHelper = fileHelper;
    }

    public void render(List<DocItem> listItems, String name, String introduction) throws Exception {
        String nameWithExtension = fileHelper.getCompleteFileName(INDEX, HTML_EXTENSION);
        List<LinkDocItem> files = fileHelper.getListOfFile(nameWithExtension);
        MenuDocItem menu = new MenuDocItem("List of doctest files", files);
        String body = htmlItems.getListFilesTemplate(menu);
        IndexFileDocItem index = new IndexFileDocItem(name, body);
        fileHelper.writeFile(nameWithExtension, htmlItems.getIndexTemplate(index));
    }
}
