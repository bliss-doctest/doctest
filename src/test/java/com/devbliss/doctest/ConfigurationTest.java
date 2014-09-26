package com.devbliss.doctest;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Test class for {@link Configuration}.
 * 
 * @author Dennis Schumann <dennis.schumann@devbliss.com>
 */
public class ConfigurationTest {

    public static final String TEST_OUTPUT_DIR_PATH = "some/test_path/";

    /**
     * Test that the constructor reads the system property and set the htmlOutputDirectory correctly.
     */
    @Test
    public void htmlOutputDirectoryConfiguration() {
        Configuration configuration = new Configuration();
        assertTrue(configuration.getHtmlOutputDirectory().endsWith(Configuration.DEFAULT_HTML_OUTPUT_DIR));

        System.setProperty(Configuration.PROPERTY_HTML_OUTPUT_DIR, TEST_OUTPUT_DIR_PATH);
        configuration = new Configuration();
        assertTrue(configuration.getHtmlOutputDirectory().endsWith(TEST_OUTPUT_DIR_PATH));
    }

}