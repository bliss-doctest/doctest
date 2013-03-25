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

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

/**
 * Unit tests for {@link JSONHelper}
 * 
 * @author bmary
 * 
 */
public class JSONHelperUnitTest {

    private JSONHelper helper;

    @Before
    public void setUp() {
        helper = new JSONHelper();
    }

    @Test
    public void jsonObjectIsValid() {
        assertTrue(helper.isJsonValid("{'abc': 'a'}"));
    }

    @Test
    public void jsonArrayIsValid() {
        assertTrue(helper.isJsonValid("[{'abc': 'a'},{'abf': 'tt'}]"));
    }

    @Test
    public void jsonNotValidStringEqualsNull() {
        assertFalse(helper.isJsonValid("null"));
    }

    @Test
    public void jsonNotValidStringIsNull() {
        assertFalse(helper.isJsonValid(null));
    }

    @Test
    public void jsonNotValidStringIsEmpty() {
        assertFalse(helper.isJsonValid(""));
    }

    @Test
    public void jsonObjectIsNotValid() {
        assertFalse(helper.isJsonValid("'abc': 'a'}"));
        assertFalse(helper.isJsonValid("''a'"));
    }

}
