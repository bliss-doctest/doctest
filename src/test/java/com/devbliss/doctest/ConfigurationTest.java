/*
 * Copyright 2014, devbliss GmbH
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

package com.devbliss.doctest;

import org.junit.Test;

import static org.junit.Assert.*;

import java.util.Properties;

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
        Properties sysProps = System.getProperties();
        sysProps.remove(Configuration.PROPERTY_HTML_OUTPUT_DIR);
    }

}