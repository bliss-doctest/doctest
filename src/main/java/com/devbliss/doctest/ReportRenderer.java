package com.devbliss.doctest;

import java.util.List;

import com.devbliss.doctest.templates.DocItem;

public interface ReportRenderer {

    void render(List<DocItem> listTemplates, String string);

}
