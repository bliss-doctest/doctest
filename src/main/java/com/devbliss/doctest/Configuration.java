package com.devbliss.doctest;

import java.io.File;

/**
 * Doctest Configuration class. Simple instantiation of this object will setup default values.
 * 
 * @author mreinwarth
 */
public class Configuration {

    private String htmlOutputDirectory = new File("").getAbsolutePath() + "/target/site/doctests/";

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
