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

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.devbliss.doctest.items.DocItem;
import com.devbliss.doctest.items.IndexFileDocItem;
import com.devbliss.doctest.items.LinkDocItem;
import com.devbliss.doctest.items.MenuDocItem;
import com.devbliss.doctest.utils.FileHelper;

/**
 * Unit tests for the {@link HtmlIndexFileRenderer}
 * 
 * @author mbankmann
 * 
 */
@RunWith(MockitoJUnitRunner.class)
public class HtmlIndexFileRendererUnitTest {

    private static final String NAME = "index";
    private static final String COMPLETE_NAME = "complete/name";

    @Mock
    private HtmlItems htmlItems;
    @Mock
    private FileHelper fileHelper;

    private HtmlIndexFileRenderer renderer;

    @Before
    public void setUp() {
        when(fileHelper.getCompleteFileName(NAME, ".html")).thenReturn(COMPLETE_NAME);
        renderer = new HtmlIndexFileRenderer(htmlItems, fileHelper);
    }

    @Test
    public void testRender() throws Exception {
        List<DocItem> listTemplates = new ArrayList<DocItem>();
        when(fileHelper.getListOfFile(anyString())).thenReturn(new ArrayList<LinkDocItem>());
        renderer.render(listTemplates, "", "");
        verify(htmlItems).getListFilesTemplate(any(MenuDocItem.class));
        verify(htmlItems).getIndexTemplate(any(IndexFileDocItem.class));
        verify(fileHelper).writeFile(eq(COMPLETE_NAME), anyString());
    }
}
