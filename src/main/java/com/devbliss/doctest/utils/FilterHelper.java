package com.devbliss.doctest.utils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.devbliss.doctest.LogicDocTest;

/**
 * Helper class used to filter some datas.
 * 
 * @author katharinairrgang, bmary
 * 
 */
public class FilterHelper {

    /**
     * {@link FilterHelper} filters an original {@link Map} <code>(key = {@link String}, value =
     * {@link String})</code> depending on the keys that the user wants to show. <br/>
     * 
     * @param originalElements
     * @param elementsToShow
     * @return The returned list contains all the elements from the map (changed to lower case)
     *         which are mentioned in the elementsToShow.
     */
    public Map<String, String> filterMap(Map<String, String> originalElements,
            List<String> elementsToShow) {

        Map<String, String> elements = new HashMap<String, String>();

        if (LogicDocTest.SHOW_ALL_ELEMENTS.equals(elementsToShow)) {
            elements = originalElements;
        }

        for (String element : elementsToShow) {
            String lowerCasedElement = element.toLowerCase();
            if (originalElements.containsKey(lowerCasedElement)) {
                elements.put(lowerCasedElement, originalElements.get(lowerCasedElement));
            }
        }
        return elements;
    }
}
