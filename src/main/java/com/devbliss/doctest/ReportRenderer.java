package com.devbliss.doctest;

import java.util.List;

import com.devbliss.doctest.templates.DocItem;

/**
 * Renders a report in a special format.
 * 
 * @author bmary
 * 
 */
public interface ReportRenderer {

    void render(List<DocItem> listTemplates, String string);

}
