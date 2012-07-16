package com.devbliss.doctest.renderer;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

/**
 * Defines some general methods used by a {@link ReportRenderer} and which do not depend on the
 * format of the report.
 * 
 * Each {@link ReportRenderer} should extend this class to write the file and build its correct
 * name.
 * 
 * @author bmary
 * 
 */
public abstract class AbstractReportRenderer implements ReportRenderer {

    /**
     * By convention we are using the maven project structure.
     * Therefore doctest will be written into ./target/doctests/.
     */
    private static final String OUTPUT_DIRECTORY = new File("").getAbsolutePath()
            + "/target/doctests/";

    /**
     * This writes out the file and retries if some other task has
     * locked the file.
     * 
     * This could cause a StackOverflowException, but I cannot
     * think of any real case where this happens...
     * 
     * @param nameOfFile
     */
    public static void writeOutFile(String nameOfFile, String content) {
        Writer fw = null;

        try {
            fw = new FileWriter(nameOfFile);
            fw.write(content);

        } catch (IOException e) {

            try {
                Thread.sleep(200);
                writeOutFile(nameOfFile, content);
            } catch (InterruptedException err2) {
                writeOutFile(nameOfFile, content);

            }

        } finally {
            if (fw != null)
                try {
                    fw.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
        }

    }

    protected void writeFile(String nameCompletePath, String finalDoc) {
        // make sure the directory exists
        new File(nameCompletePath).getParentFile().mkdirs();
        writeOutFile(nameCompletePath, finalDoc);
    }

    protected static String getCompleteFileName(String name, String extension) {
        return OUTPUT_DIRECTORY + name + extension;
    }
}
