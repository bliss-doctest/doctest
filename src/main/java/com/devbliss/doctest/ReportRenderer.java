package com.devbliss.doctest;

import java.util.List;

import com.devbliss.doctest.templates.Item;

public interface ReportRenderer {

    void render(List<Item> listTemplates, String string);

}
