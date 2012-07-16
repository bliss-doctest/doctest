package com.devbliss.doctest.renderer.html;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;

public class HtmlStyle {

    public static String getCss() {
        try {
            return readFile(new File(new File("").getAbsolutePath()
                    + "/src/main/java/com/devbliss/doctest/renderer/html/htmlStyle.css"));
        } catch (IOException e) {
            return "no css";
        }
    }

    private static String readFile(File file) throws IOException {
        FileInputStream stream = new FileInputStream(file);
        try {
            FileChannel fc = stream.getChannel();
            MappedByteBuffer bb = fc.map(FileChannel.MapMode.READ_ONLY, 0, fc.size());
            /* Instead of using default, pass in a decoder. */
            return Charset.defaultCharset().decode(bb).toString();
        } finally {
            stream.close();
        }
    }

}
