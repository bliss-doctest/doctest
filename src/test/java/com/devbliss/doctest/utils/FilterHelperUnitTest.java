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

package com.devbliss.doctest.utils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import com.devbliss.doctest.LogicDocTest;

/**
 * Unit Test for {@link FilterHelper}
 * 
 * @author katharinairrgang
 */
public class FilterHelperUnitTest {

    /*
     * the original headers are in lower case, because they
     * will be transformed in the ApiTester project
     */

    private static final String NAME_1 = "content-type";
    private static final String VALUE_1 = "application/json";
    private static final String NAME_2 = "accept-charset";
    private static final String VALUE_2 = "utf-8";
    private static final String NAME_3 = "accept-language";
    private static final String VALUE_3 = "en-US";

    private static final String NAME_4 = "CONTENT-TYPE";
    private static final String NAME_5 = "META-INFORMATION";
    private static final String NAME_6 = "accept-language";

    private Map<String, String> elements;
    private List<String> elementsToShow;
    private FilterHelper helper;

    @Before
    public void setUp() {
        helper = new FilterHelper();

        // original elements
        elements = new HashMap<String, String>();
        elements.put(NAME_1, VALUE_1);
        elements.put(NAME_2, VALUE_2);
        elements.put(NAME_3, VALUE_3);
    }

    @Test
    public void testFilter() {
        // elements which should be displayed in the documentation and
        // they should be declared by developer
        elementsToShow = new ArrayList<String>();
        elementsToShow.add(NAME_4);
        elementsToShow.add(NAME_5);
        elementsToShow.add(NAME_6);

        Map<String, String> filteredHeaders = helper.filterMap(elements, elementsToShow);
        assertEquals(VALUE_1, filteredHeaders.get(NAME_1));
        assertEquals(VALUE_3, filteredHeaders.get(NAME_3));

        assertFalse(filteredHeaders.containsKey(NAME_2));
        assertFalse(filteredHeaders.containsKey(NAME_4));
    }

    @Test
    public void testShowAllElements() {
        Map<String, String> filteredHeaders = helper.filterMap(elements, LogicDocTest.SHOW_ALL_ELEMENTS);
        assertEquals(VALUE_1, filteredHeaders.get(NAME_1));
        assertEquals(VALUE_2, filteredHeaders.get(NAME_2));
        assertEquals(VALUE_3, filteredHeaders.get(NAME_3));
    }
}
