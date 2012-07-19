package com.devbliss.doctest.renderer.html;

import com.devbliss.doctest.items.JsonDocItem;

import freemarker.template.TemplateModel;

public class JsonTemplateModel extends JsonDocItem implements TemplateModel {

    public JsonTemplateModel(String expected) {
        super(expected);
    }

}
