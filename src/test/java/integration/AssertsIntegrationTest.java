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

package integration;

import org.junit.Test;

import com.devbliss.doctest.DocTest;

/**
 * Example implementation of assertion provided by {@link DocTest}.
 * 
 * 
 * @author christoph.ulrich@devbliss.com
 *         Date: 19.09.12
 */
public class AssertsIntegrationTest extends DocTest {

    private static final String IS = "IS";
    private static final String IS_NOT = IS + " NOT";
    private static final String DOCU_GENERATED = "a documenting output is generated";

    @Override
    public String getFileName() {
        return "Asserts";
    }

    @Test
    public void assertNullAndSay() {
        sayNextSection("Making an assertNullAndSay(null, \"The Object " + IS_NOT
                + " instantiated\")");
        say("We want to verify the assertion is null and " + DOCU_GENERATED);

        assertNullAndSay(null, "The object " + IS_NOT + " instantiated");
    }

    @Test
    public void assertNotNullAndSay() {
        sayNextSection("Making an assertNotNull(new Object(), \"The object " + IS
                + " instantiated\");");
        say("We want to verify the assertion is not null and " + DOCU_GENERATED);

        assertNotNullAndSay(new Object(), "The object " + IS + " instantiated");
    }

    @Test
    public void assertTrueAndSay() {
        sayNextSection("Making an assertTrueAndSay(true, \"The assertion should evaluate to true\")");
        say("We want to verify the assertion is evaluated to true and " + DOCU_GENERATED);

        assertTrueAndSay(true, "The assertion " + IS + " evaluated to true");
    }

    @Test
    public void assertFalseAndSay() {
        sayNextSection("Making an assertFalseAndSay(false, \"The assertion is evaluated to false\")");
        say("We want to verify the assertion is evaluated to false and " + DOCU_GENERATED);

        assertFalseAndSay(false, "The assertion " + IS + " evaluated to false");
    }

    @Test
    public void assertEqualsAndSay() {
        sayNextSection("Making an assertEqualsAndSay(new String(\"Same\"), new String(\"Same\"), \"The expected Strings and the given String equal each other\")");
        say("We want to verify the assertion is evaluated and the expected object and the given object equal each other");
        assertEqualsAndSay(new String("Same"), new String("Same"),
                "The expected Strings and the given String equal each other");
    }
}
