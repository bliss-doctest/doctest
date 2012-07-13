package com.devbliss.doctest;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

public abstract class AbstractReportRenderer implements ReportRenderer {

    /**
     * By convention we are using the maven project structure.
     * Therefore doctest will be written into ./target/doctests/.
     */
    public static final String OUTPUT_DIRECTORY = new File("").getAbsolutePath()
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
}
