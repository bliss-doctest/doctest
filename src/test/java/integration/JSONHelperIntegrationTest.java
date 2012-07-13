package integration;

import java.util.Arrays;

import org.junit.Test;

import com.devbliss.doctest.DocTest;
import com.devbliss.doctest.JSONHelper;

/**
 * Example implementation of a unit test class extending the {@link DocTest}.
 * 
 * @Warning This class is used in the {@link ReportCreatedIntegrationTest} class.
 * 
 * @author bmary
 * 
 */
public class JSONHelperIntegrationTest extends DocTest {

    private class A {
        private String a;
        private String b;
        private String c;

        public A(String a, String b, String c) {
            this.a = a;
            this.b = b;
            this.c = c;
        }

    }

    private class B {
        private String a;
        private String b;

        public B(String a, String b) {
            this.a = a;
            this.b = b;
        }

    }

    @Test
    public void excludeFieldsTest() throws Exception {
        sayNextSection("Exclude fields test");

        JSONHelper jsonhelper = new JSONHelper();

        say("We can sort out fields while serializing to JSON. This can be used to string compare the Json of objects with fields that shouldn't compared.");
        say("At first we will create two object of different classes which differ on one field named 'c'.");
        A a = new A("a", "b", "c");
        B b = new B("a", "b");

        say("Converting A & B without excluding fields and compare the result.");
        String aStr = jsonhelper.toJson(a, true);
        String bStr = jsonhelper.toJson(b, true);

        say("This is Object A:");
        sayPreformattedCode(aStr);
        say("This is Object B:");
        sayPreformattedCode(bStr);

        say("Comparing this results in failure.");
        assertFalseAndSay(aStr.equals(bStr));

        say("Converting A & B while excluding the field 'c' and compare the result:");
        String aExcludedStr = jsonhelper.toJsonAndSkipCertainFields(a, Arrays.asList("c"), true);
        String bExcludedStr = jsonhelper.toJsonAndSkipCertainFields(a, Arrays.asList("c"), true);

        say("JSON Object A:");
        sayPreformattedCode(aExcludedStr);
        say("JSON Object B:");
        sayPreformattedCode(bExcludedStr);

        say("Comparing this results in success.");
        assertTrueAndSay(aExcludedStr.equals(bExcludedStr));
    }

}