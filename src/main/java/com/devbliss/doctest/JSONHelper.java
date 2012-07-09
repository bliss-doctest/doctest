package com.devbliss.doctest;

import com.google.gson.Gson;

/**
 * Utils class used to transform object into json-formatted {@link String}
 * 
 * @author bmary
 * 
 */
public class JSONHelper {

    public String toJson(Object obj) {
        return new Gson().toJson(obj);
    }
}
