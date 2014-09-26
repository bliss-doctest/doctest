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

package com.devbliss.doctest;

import java.io.File;

/**
 * Doctest Configuration class. Simple instantiation of this object will setup default values.
 * 
 * @author mreinwarth
 */
public class Configuration {
    public static final String DEFAULT_HTML_OUTPUT_DIR = "/target/site/doctests/";
    public static final String PROPERTY_HTML_OUTPUT_DIR = "doctest.html.output";

    private String htmlOutputDirectory;

    public Configuration() {
        String absolutePath = new File("").getAbsolutePath();
        String doctestOutputPathRelative = null;
        try {
            doctestOutputPathRelative = System.getProperty(PROPERTY_HTML_OUTPUT_DIR);
            if (doctestOutputPathRelative.isEmpty()) {
                doctestOutputPathRelative = DEFAULT_HTML_OUTPUT_DIR;
            }
        } catch (NullPointerException nex) {
            doctestOutputPathRelative = DEFAULT_HTML_OUTPUT_DIR;
        }
        htmlOutputDirectory = absolutePath + doctestOutputPathRelative;
    }

    /**
     * Get absolute path to the output directory
     * 
     * @return the htmlOutputDirectory
     */
    public String getHtmlOutputDirectory() {
        return htmlOutputDirectory;
    }

    /**
     * Set the absolute path of the output directory
     * 
     * @param htmlOutputDirectory the htmlOutputDirectory to set
     */
    public void setHtmlOutputDirectory(String htmlOutputDirectory) {
        this.htmlOutputDirectory = htmlOutputDirectory;
    }
}
