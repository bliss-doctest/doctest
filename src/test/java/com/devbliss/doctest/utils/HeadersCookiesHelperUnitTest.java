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
 * Unit Test for {@link HeadersCookiesHelper}
 * 
 * @author katharinairrgang
 */
public class HeadersCookiesHelperUnitTest {

    /*
     * the original headers are in lower case, because they
     * will be transformed in the ApiTester project
     */

    private static final String HEADER1 = "content-type";
    private static final String VALUE1 = "application/json";
    private static final String HEADER2 = "accept-charset";
    private static final String VALUE2 = "utf-8";
    private static final String HEADER3 = "accept-language";
    private static final String VALUE3 = "en-US";

    private static final String HEADER4 = "CONTENT-TYPE";
    private static final String HEADER5 = "META-INFORMATION";
    private static final String HEADER6 = "accept-language";

    private Map<String, String> headers;
    private List<String> headersToShow;
    private HeadersCookiesHelper headersHelper;

    @Before
    public void setUp() {
        headersHelper = new HeadersCookiesHelper();

        // original headers from e.g. httprequest
        headers = new HashMap<String, String>();
        headers.put(HEADER1, VALUE1);
        headers.put(HEADER2, VALUE2);
        headers.put(HEADER3, VALUE3);
    }

    @Test
    public void testFilter() {
        // headers which should be displayed in the documentation and
        // they should be declared by developer
        headersToShow = new ArrayList<String>();
        headersToShow.add(HEADER4);
        headersToShow.add(HEADER5);
        headersToShow.add(HEADER6);

        Map<String, String> filteredHeaders = headersHelper.filter(headers, headersToShow);
        assertEquals(VALUE1, filteredHeaders.get(HEADER1));
        assertEquals(VALUE3, filteredHeaders.get(HEADER3));

        assertFalse(filteredHeaders.containsKey(HEADER2));
        assertFalse(filteredHeaders.containsKey(HEADER4));
    }

    @Test
    public void testShowAllHeaders() {
        Map<String, String> filteredHeaders = headersHelper.filter(headers, LogicDocTest.ALL);
        assertEquals(VALUE1, filteredHeaders.get(HEADER1));
        assertEquals(VALUE2, filteredHeaders.get(HEADER2));
        assertEquals(VALUE3, filteredHeaders.get(HEADER3));

    }
}
