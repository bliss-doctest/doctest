package com.devbliss.doctest;

import java.util.List;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.GsonBuilder;

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

        GsonBuilder builder = new GsonBuilder().addSerializationExclusionStrategy(strategy)
                        .addDeserializationExclusionStrategy(strategy);

        if (prettyPrint)
            builder.setPrettyPrinting();

        return builder.create().toJson(obj);
    }

}
