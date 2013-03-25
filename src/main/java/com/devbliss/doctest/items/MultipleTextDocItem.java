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

package com.devbliss.doctest.items;

public class MultipleTextDocItem implements DocItem {

    private String text;
    private String[] additionalStrings;

    public MultipleTextDocItem(String text, String[] strings) {
        this.text = text;
        this.additionalStrings = strings;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String[] getAdditionalStrings() {
        return additionalStrings;
    }

    public void setStrings(String[] strings) {
        this.additionalStrings = strings;
    }

    public String getItemName() {
        return "text";
    }
}
