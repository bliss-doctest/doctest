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
