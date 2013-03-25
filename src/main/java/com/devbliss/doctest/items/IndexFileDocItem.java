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

import java.util.Date;

public class IndexFileDocItem implements DocItem {

    private String css;
    private String name;
    private String items;
    private final String date;

    public IndexFileDocItem(String name, String items) {
        this("", name, items);
    }

    public IndexFileDocItem(String css, String name, String items) {
        this.css = css;
        this.name = name;
        this.items = items;
        this.date = new Date().toString();
    }

    public String getCss() {
        return css;
    }

    public String getName() {
        return name;
    }

    public void setCss(String css) {
        this.css = css;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getItems() {
        return items;
    }

    public void setItems(String items) {
        this.items = items;
    }

    public String getDate() {
        return this.date;
    }

    public String getItemName() {
        return "index";
    }

}
