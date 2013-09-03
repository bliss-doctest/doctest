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

import static org.junit.Assert.fail;

import java.util.Arrays;

import org.junit.Test;

import com.devbliss.doctest.DocTest;

/**
 * Example implementation of a unit test class extending the {@link DocTest}.
 * 
 * This class is used in the {@link ReportCreatedIntegrationTest} class.
 * 
 * @author bmary
 * 
 */
public class CompareObjectsIntegrationTest extends DocTest {

    @SuppressWarnings("unused")
    private class A {
        private final String a;
        private final String b;
        private final String c;

        public A(String a, String b, String c) {
            this.a = a;
            this.b = b;
            this.c = c;
        }

    }

    @SuppressWarnings("unused")
    private class B {
        private final String a;
        private final String b;

        public B(String a, String b) {
            this.a = a;
            this.b = b;
        }

    }

    @Override
    public String getFileName() {
        return "CompareObjects";
    }

    @Test(expected = AssertionError.class)
    public void compareTwoDifferentObjectsWithoutExcluding() throws Exception {
        try {
            sayNextSection("Comparing two different objects without excluding fields");
            say("We can sort out fields while serializing to JSON. This can be used to string compare the Json of objects with fields that shouldn't compared.");
            say("At first we will create two object of different classes which differ on one field named 'c'.");
            A a = new A("a", "b", "c");
            sayObject(a);
            B b = new B("a", "b");
            sayObject(b);
            say("Comparing this results in failure: assertJsonEqualsAndSay(a, b);");
            assertJsonEqualsAndSay(a, b);
        } catch (AssertionError e) {
            assertFalseAndSay(false, "assert failed");
            throw (e);
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void compareTwoDifferentObjectsWithExcluding() throws Exception {
        sayNextSection("Comparing two different objects with excluding fields");
        say("We can sort out fields while serializing to JSON. This can be used to string compare the Json of objects with fields that shouldn't compared.");
        say("At first we will create two object of different classes which differ on one field named 'c'.");
        A a = new A("a", "b", "c");
        sayObject(a);
        B b = new B("a", "b");
        sayObject(b);
        say("Comparing this results in failure.");
        assertJsonEqualsAndSay(a, b, "message", Arrays.asList("c"));
    }

    @Test(expected = AssertionError.class)
    public void assertJsonFalse() throws Exception {
        assertJsonEqualsAndSay(new A("a", "b", "c"), new B("a", "blalal"), "message", Arrays
                .asList("c"));
    }
}