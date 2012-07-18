package integration;

import static org.junit.Assert.assertEquals;
import static testutils.Utils.cleanUpTheTargetDirectory;
import static testutils.Utils.getFilesInOutputDirectory;
import static testutils.Utils.verifyTheFileHasBeenCreated;

import java.io.File;
import java.util.List;

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

    @Before
    public void setUp() {
        cleanUpTheTargetDirectory();
    }

    @After
    public void tearDown() {
        cleanUpTheTargetDirectory();
    }

    @Test
    public void verifyThatTheReportsAreCreated() {
        makeSomeTests();

        // verify that new files have been created
        List<File> listFiles = getFilesInOutputDirectory();
        assertEquals(3, listFiles.size());
        verifyTheFileHasBeenCreated("index.html");
        verifyTheFileHasBeenCreated("integration.RequestsIntegrationTest.html");
        verifyTheFileHasBeenCreated("integration.CompareObjectsIntegrationTest.html");
    }

    /**
     * Because the report file is created during the @AfterClass cycle of every test class, we have
     * to run a test class with the {@link JUnitCore}.
     */
    private void makeSomeTests() {
        JUnitCore.runClasses(RequestsIntegrationTest.class, CompareObjectsIntegrationTest.class);
    }
}
