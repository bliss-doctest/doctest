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
