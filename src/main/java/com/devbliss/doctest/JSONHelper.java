package com.devbliss.doctest;

import com.google.gson.Gson;

public class JSONHelper {

    public String toJson(Object obj) {
        return new Gson().toJson(obj);
    }
}
