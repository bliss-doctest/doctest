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

package integration;

import static org.junit.Assert.assertEquals;
import static testutils.Utils.cleanUpTheTargetDirectory;
import static testutils.Utils.getFilesInOutputDirectory;
import static testutils.Utils.verifyTheFileHasBeenCreated;
import static testutils.Utils.verifyTheFileHasThisContent;

import java.io.File;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.JUnitCore;

/**
 * This integration test checks that a report file is created after a DocTest class is run.
 * 
 * @author mbankmann
 * 
 */
public class EncodingIntegrationTest {

    @Before
    public void setUp() {
        cleanUpTheTargetDirectory();
    }

    @Test
    public void verifyThatTheReportsAreCreated() {
        makeSomeTests();
        // verify that new files have been created
        List<File> listFiles = getFilesInOutputDirectory();
        assertEquals(2, listFiles.size());
        verifyTheFileHasBeenCreated("HttpRequests.html");

        for (File file : listFiles) {
            if (file.getAbsolutePath().endsWith("HttpRequests.html")) {
                verifyTheFileHasThisContent(listFiles.get(0),
                        "Making a put request with encöding chäracters");
            }
        }
    }

    /**
     * Because the report file is created during the @AfterClass cycle of every test class, we have
     * to run a test class with the {@link JUnitCore}.
     */
    private void makeSomeTests() {
        JUnitCore.runClasses(RequestsIntegrationTest.class);
    }
}



