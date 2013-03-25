/*
 * Copyright 2013, devbliss GmbH
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */

package com.devbliss.doctest.utils;

import java.util.List;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;

/**
 * Utils class used to transform object into json-formatted {@link String}
 * 
 * @author bmary
 * @author mbankmann
 * 
 */
public class JSONHelper {

    /**
     * Converts the given POJO into a Json representation.
     * 
     * @param obj
     * @return
     */
    public String toJson(Object obj) {
        return toJson(obj, false);
    }

    /**
     * 
     * Converts the given POJO into a Json representation.
     * If prettyPrint is true, the output will be nicely formatted.
     * 
     * @param obj
     * @param prettyPrint
     * @return
     */
    public String toJson(Object obj, boolean prettyPrint) {
        GsonBuilder builder = new GsonBuilder();

        if (prettyPrint) {
            builder.setPrettyPrinting();
        }

        return builder.create().toJson(obj);
    }

    /**
     * 
     * Converts the given POJO to Json and will skip the given fields while doing so.
     * 
     * @param obj
     * @param excludedFields
     * @return
     */
    public String toJsonAndSkipCertainFields(Object obj, final List<String> excludedFields) {
        return toJsonAndSkipCertainFields(obj, excludedFields, false);
    }

    /**
     * 
     * Converts the given POJO and will skip the given fields while doing so.
     * If prettyPrint is true, the output will be nicely formatted.
     * 
     * @param obj
     * @param excludedFields
     * @param prettyPrint
     * @return
     */
    public String toJsonAndSkipCertainFields(Object obj, final List<String> excludedFields,
            boolean prettyPrint) {
        ExclusionStrategy strategy = new ExclusionStrategy() {
            public boolean shouldSkipField(FieldAttributes f) {
                if (excludedFields.contains(f.getName())) {
                    return true;
                }

                return false;
            }

            public boolean shouldSkipClass(Class<?> clazz) {
                return false;
            }
        };

        GsonBuilder builder =
                new GsonBuilder().addSerializationExclusionStrategy(strategy)
                        .addDeserializationExclusionStrategy(strategy);

        if (prettyPrint)
            builder.setPrettyPrinting();

        return builder.create().toJson(obj);
    }

    /**
     * Takes a {@link String} and checks whether it's a valid json or not.
     * 
     * <p>
     * The function returns false when the String:
     * <ul>
     * <li>is null</li>
     * <li>equals "null"</li>
     * <li>is empty</li>
     * <li>can not be deserialized to a java {@link Object}</li>
     * </ul>
     * </p>
     * 
     * @param json
     * @return
     */
    public boolean isJsonValid(String json) {
        if (json == null || json.equals("null") || json.isEmpty()) {
            return false;
        }

        boolean isJsonValid = true;
        try {
            new Gson().fromJson(json, Object.class);
        } catch (JsonSyntaxException e) {
            isJsonValid = false;
        }
        return isJsonValid;
    }

    /**
     * 
     * Pretty prints any json input.
     * 
     * @param json
     * @return
     */
    public String prettyPrintJson(String json) {
        if (!isJsonValid(json)) {
            return json;
        }

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        JsonParser jp = new JsonParser();
        JsonElement je = jp.parse(json);
        String returnvalue = gson.toJson(je);
        return returnvalue;
    }
}
