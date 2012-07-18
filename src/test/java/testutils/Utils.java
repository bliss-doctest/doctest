package testutils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Utils {

    public static final String OUTPUT_DIRECTORY = new File("").getAbsolutePath()
            + "/target/site/doctests/";

    public static void verifyTheFileHasBeenCreated(String fileName) {
        verifyTheFileHasBeenCreated(fileName, new File(OUTPUT_DIRECTORY).listFiles());
    }

    public static void verifyTheFileHasBeenCreated(String fileName, File[] listFiles) {
        boolean isFilePresent = false;
        for (File f : listFiles) {
            if (f.getAbsolutePath().equals(OUTPUT_DIRECTORY + fileName)) {
                isFilePresent = true;
                break;
            }
        }
        assertTrue("The report '" + OUTPUT_DIRECTORY + fileName
                + "' has not been created. The created files are: " + toString(listFiles),
                isFilePresent);
    }

    private static String toString(File[] listFiles) {
        StringBuffer buffer = new StringBuffer();
        for (File file : listFiles) {
            buffer.append("\n + ");
            buffer.append(file.getAbsolutePath());
        }
        if (buffer.length() > 0) {
            return buffer.substring(0, buffer.length());
        } else {
            return "none";
        }
    }

    public static void cleanUpTheTargetDirectory() {
        File outputDirectory = new File(OUTPUT_DIRECTORY);

        File[] files = outputDirectory.listFiles();
        if (files != null) {
            for (File file : files) {
                file.delete();
            }
            assertEquals(0, getFilesInOutputDirectory().size());
        }
    }

    /**
     * Returns all the files from the output directory without taking care of the directories (/css,
     * /images)
     * 
     * @return
     */
    public static List<File> getFilesInOutputDirectory() {
        List<File> files = new ArrayList<File>();
        for (File file : new File(OUTPUT_DIRECTORY).listFiles()) {
            if (!file.isDirectory()) {
                files.add(file);
            }
        }
        return files;
    }
}
