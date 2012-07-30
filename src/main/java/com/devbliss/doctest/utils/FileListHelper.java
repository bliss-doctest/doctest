package com.devbliss.doctest.utils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.devbliss.doctest.items.LinkDocItem;

public class FileListHelper {

    public List<LinkDocItem> getListOfFileAsString(String nameWithCompletePath,
            String shortName, String extension) {
        File[] files = fetchFilesInDirectory(nameWithCompletePath);
        List<LinkDocItem> list = new ArrayList<LinkDocItem>();
        // fetch neither the file itself nor the hidden files
        for (File file : files) {
            if (!file.getName().equals(shortName + extension)) {
                if (!file.isHidden()) {
                    list.add(new LinkDocItem(file.getName(), file.getName()));
                }
            }
        }
        return list;
    }

    /**
     * Fetch all the files of the doctests directory. Each file corresponds to a test case.
     * 
     * @param indexFileWithCompletePath
     * @return
     */
    private File[] fetchFilesInDirectory(String indexFileWithCompletePath) {
        return new File(indexFileWithCompletePath).getParentFile().listFiles();
    }

}
