package de.devbliss;


import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.net.URI;
import java.util.Date;

import org.json.JSONException;
import org.json.JSONObject;

import de.devbliss.apitester.ApiTest.HTTP_REQUEST;
import de.devbliss.templates.Templates;

public class DocTestMachineImpl implements DocTestMachine {

    /**
     * By convention we are using the maven project structure.
     * Therefore doctest will be written into ./target/doctests/.
     */
    public static final String OUTPUT_DIRECTORY = new File("").getAbsolutePath()
            + "/target/doctests/";

    // some html formatting

    public String htmlFormat =
            "<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01//EN\" \"http://www.w3.org/TR/html4/strict.dtd\">"
                    + "<html>%s</html>";
    public String headerFormat = "<head><title>DocTest for class %s</title></head>";
    public String bodyFormat = "<body><a href=\"index.html\">back to index page</a><br/>%s<body>";

    public String h1 = "<h1>%s</h1>";
    public String simpleLine = "%s<br/>";

    // The class under test.
    // Usually I don't like that, but we don't have another option to generate
    // the name under test because of the static way JUnit runs files and uses
    // AfterClass and BeforeClass.
    @SuppressWarnings("rawtypes")
    public Class classUnderTest;

    public StringBuffer outputOfTestsBuffer = new StringBuffer();

    /**
     * Main method => this lets you write out stuff.
     * 
     * Usually after a say a newline is appended.
     * 
     */
    public void say(String say) {
        outputOfTestsBuffer.append(String.format(simpleLine, say));

    }

    /**
     * Inits the new file for writing stuff out.
     */
    public void beginDoctest(@SuppressWarnings("rawtypes") Class clazz) {
        if (classUnderTest == null) {
            classUnderTest = clazz;
            say("Doctest originally perfomed at: " + new Date());
        }
    }

    /**
     * This would be a header. Maybe a new test inside a testcase.
     */
    public void sayNextSection(String sectionName) {

        outputOfTestsBuffer.append(String.format(h1, sectionName));

    }

    /**
     * At the end of a successful test stuff gets written out.
     */
    public void endDocTest() {
        // assemble the html page:

        // this will be not SaySysoutImpl, but the name of the Unit test :)
        String finalHeader = String.format(headerFormat, classUnderTest.getCanonicalName());
        String finalBody = String.format(bodyFormat, outputOfTestsBuffer.toString());

        String finalDocument = finalHeader + finalBody;

        String fileNameForCompleteTestOutput =
                OUTPUT_DIRECTORY + classUnderTest.getCanonicalName() + ".html";

        // make sure the directories have been generated:
        new File(fileNameForCompleteTestOutput).getParentFile().mkdirs();

        writeOutFile(fileNameForCompleteTestOutput, finalDocument);

        IndexFileGenerator.generatIndexFileForTests();

    }

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

    private String getJson(String json) throws JSONException {
        if (isJsonValid(json)) {
            return Templates.getJsonTemplate(new JSONObject(json).toString(2));
        } else {
            return "";
        }
    }

    private boolean isJsonValid(String json) {
        return json != null && !json.equals("null") && !json.isEmpty() && json.startsWith("{");
    }

    public void sayRequest(URI uri, String payload, HTTP_REQUEST httpRequest) throws JSONException {
        if (uri != null) {
            say(Templates.getUriTemplate(uri.toString(), getJson(payload), httpRequest));
        }
    }

    public void sayResponse(int responseCode, String payload) throws Exception {
        say(Templates.getResponseTemplate(responseCode, getJson(payload)));
    }
}
