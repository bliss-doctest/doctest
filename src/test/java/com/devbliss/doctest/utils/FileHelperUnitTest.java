package com.devbliss.doctest.utils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static testutils.Utils.getFilesInOutputDirectory;
import static testutils.Utils.verifyTheFileHasBeenCreated;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import testutils.Utils;

import com.devbliss.doctest.items.LinkDocItem;

/**
 * Unit test for {@link FileHelper}
 * 
 * @author bmary
 * 
 */
public class FileHelperUnitTest {

    private static final String FILE = "test.txt";
    private static final String EXTENSION = ".extension";
    private static final String NAME = "name";
    private static final String DOC = "finaleDoc";
    private FileHelper helper;
    private String directory;

    @Before
    public void setUp() {
        helper = new FileHelper();
        directory = FileHelper.OUTPUT_DIRECTORY;
        new File(directory).mkdirs();
    }

    @After
    public void tearDown() {
        Utils.deleteDirectory(new File(directory));
    }

    @Test
    public void getCompleteFileName() {
        String result = helper.getCompleteFileName(NAME, EXTENSION);
        assertEquals(directory + NAME + EXTENSION, result);
    }

    @Test
    public void writeFile() throws Exception {
        helper.writeFile(directory + FILE, DOC);
        verifyTheFileHasBeenCreated(FILE);
    }

    @Test
    public void writeFileContentIsNull() throws Exception {
        helper.writeFile(directory + FILE, null);
        assertEquals(0, getFilesInOutputDirectory().size());
    }

    @Test(expected = NullPointerException.class)
    public void writeFileNameIsEmpty() throws Exception {
        helper.writeFile("", DOC);
        assertEquals(0, getFilesInOutputDirectory().size());
    }

    @Test(expected = NullPointerException.class)
    public void writeFileNameIsNull() throws Exception {
        helper.writeFile(null, DOC);
        assertEquals(0, getFilesInOutputDirectory().size());
    }

    @Test(expected = NullPointerException.class)
    public void writeFileNameIsInvalid() throws Exception {
        helper.writeFile("blabla", DOC);
        assertEquals(0, getFilesInOutputDirectory().size());
    }

    @Test
    public void readFileToString() throws Exception {
        String result = helper.readFile(new File("src/test/resources/file.txt"));
        assertEquals("content<br/>öf <br/>the text<br/>file", result);
    }

    @Test(expected = FileNotFoundException.class)
    public void readFileToStringException() throws Exception {
        helper.readFile(new File("."));
    }

    @Test
    public void getListOfFile() throws Exception {
        createAFile("a.txt");
        createAFile("b.html");
        List<LinkDocItem> items = helper.getListOfFile(directory + ".");
        assertEquals(2, items.size());
        assertEquals("a.txt", items.get(0).getName());
        assertEquals("b.html", items.get(1).getName());
    }

    @Test
    public void getListOfFileDirectoryIsEmpty() throws Exception {
        List<LinkDocItem> items = helper.getListOfFile(directory + ".");
        assertTrue(items.isEmpty());
    }

    @Test
    public void doNotListTheGivenFile() throws Exception {
        createAFile("xxx.css");
        createAFile("a.pdf");
        createAFile("file.ris");
        List<LinkDocItem> items = helper.getListOfFile(directory + "xxx.css");
        assertEquals(2, items.size());
        assertEquals("a.pdf", items.get(0).getName());
        assertEquals("file.ris", items.get(1).getName());
    }

    @Test(expected = AssertionError.class)
    public void fileNameAlreadyExists() throws Exception {
        createAFile("file1");
        helper.validateFileName("file1");
    }

    @Test
    public void fileNameDoesNotAlreadyExists() throws Exception {
        createAFile("file1");
        createAFile("file1.txt");
        createAFile("file1.html");
        helper.validateFileName("file1.css");
    }

    @Test(expected = AssertionError.class)
    public void fileNameIsNull() {
        helper.validateFileName(null);
    }

    private void createAFile(String fileName) throws Exception {
        FileWriter fw = new FileWriter(new File(directory + fileName));
        fw.close();
    }
}