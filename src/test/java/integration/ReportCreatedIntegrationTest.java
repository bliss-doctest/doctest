package integration;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.JUnitCore;

/**
 * This integration test checks that a report file is created after a DocTest class is run.
 * 
 * @author bmary
 * 
 */
public class ReportCreatedIntegrationTest {

    private static final String OUTPUT_DIRECTORY = "./target/doctests";
    private File[] listFiles;

    @Before
    public void setUp() {
        cleanUpTheTargetDirectory();
    }

    @After
    public void tearDown() {
        cleanUpTheTargetDirectory();
    }

    @Test
    public void verifyThatTheReportIsCreated() {
        makeSomeTests();

        // verify that two files have been created
        File outputDirectory = new File(OUTPUT_DIRECTORY);
        listFiles = outputDirectory.listFiles();
        assertEquals(2, listFiles.length);
        verifyTheFileHasBeenCreated("/index.html");
        verifyTheFileHasBeenCreated("/integration.ExampleIntegrationTest.html");
    }

    private void verifyTheFileHasBeenCreated(String fileName) {
        boolean isFilePresent = false;
        for (File f : listFiles) {
            if (f.getPath().equals(OUTPUT_DIRECTORY + fileName)) {
                isFilePresent = true;
                break;
            }
        }
        assertTrue(isFilePresent);
    }

    /**
     * Because the report file is created during the @AfterClass cycle of every test class, we have
     * to run a test class with the {@link JUnitCore}.
     */
    private void makeSomeTests() {
        JUnitCore.runClasses(ExampleIntegrationTest.class);
    }

    private void cleanUpTheTargetDirectory() {
        File outputDirectory = new File(OUTPUT_DIRECTORY);

        File[] files = outputDirectory.listFiles();
        for (File file : files) {
            file.delete();
        }
        assertEquals(0, outputDirectory.listFiles().length);
    }
}
