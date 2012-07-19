package com.devbliss.doctest.renderer.html;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.StringWriter;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

import com.devbliss.doctest.items.AssertDocItem;
import com.devbliss.doctest.items.DocItem;
import com.devbliss.doctest.items.FileDocItem;
import com.devbliss.doctest.items.JsonDocItem;
import com.devbliss.doctest.items.MenuDocItem;
import com.devbliss.doctest.items.RequestDocItem;
import com.devbliss.doctest.items.ResponseDocItem;
import com.devbliss.doctest.items.SectionDocItem;
import com.google.inject.Inject;

import freemarker.template.Configuration;
import freemarker.template.Template;

/**
 * Items used by the {@link HtmlRenderer} to build the html report.
 * 
 * @author bmary
 * 
 */
public class HtmlItems {

    private final Configuration configuration;

    @Inject
    public HtmlItems(Configuration configuration) {
        this.configuration = configuration;
    }

    public String getJsonTemplate(JsonDocItem item) {
        return init("json.ftl", item);
    }

    private String init(String templateName, DocItem item) {
        StringWriter writer = new StringWriter();
        Template template;
        try {
            template = configuration.getTemplate(templateName);
            template.process(item, writer);
            return writer.toString();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public String getRequestTemplate(RequestDocItem item) {
        return init("request.ftl", item);
    }

    public String getResponseTemplate(ResponseDocItem item) {
        return init("response.ftl", item);
    }

    public String getAssertTemplate(AssertDocItem item) {
        return init("assert.ftl", item);
    }

    public String getSectionTemplate(SectionDocItem item) {
        return init("section.ftl", item);
    }

    private static String getCss() {
        try {
            return readFile(new File(new File("").getAbsolutePath()
                    + "/src/main/resources/htmlStyle.css"));
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

    public String getReportTemplate(FileDocItem item) {
        item.setCss(getCss());
        return init("htmlFile.ftl", item);
    }

    public String getIndexTemplate(FileDocItem item) {
        item.setCss(getCss());
        return init("index.ftl", item);
    }

    public String getListFilesTemplate(MenuDocItem item) {
        StringWriter writer = new StringWriter();
        Template template;
        try {
            template = configuration.getTemplate("listFiles.ftl");
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("files", item.getFiles());
            template.process(map, writer);
            return writer.toString();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
