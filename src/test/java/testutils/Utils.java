package testutils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
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

    /**
     * 
     * Verifies the given File does have the given content somewhere in the file.
     * This also checks, whether the UTF-8 encoding was used properly throughout the whole process.
     * 
     * @param file
     * @param content
     */
    public static void verifyTheFileHasThisContent(File file, String content) {
        byte[] buffer = new byte[ (int) file.length() ];
        InputStream in;
        try {
            in = new FileInputStream(file);
            in.read(buffer);
            in.close();

            String fileContent = new String(buffer, "UTF-8");

            assertTrue("The report " + file.getAbsolutePath() + "doesn't contain the content '"
                    + content + "'.", fileContent.contains(content));

        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
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
     * @return list of files in this directory
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

    public static boolean deleteDirectory(File path) {
        if (path.exists()) {
            File[] files = path.listFiles();
            for (int i = 0; i < files.length; i++) {
                if (files[i].isDirectory()) {
                    deleteDirectory(files[i]);
                } else {
                    files[i].delete();
                }
            }
        }
        return (path.delete());
    }
}
