package integration;

import static org.junit.Assert.assertEquals;

import java.net.URI;

import org.junit.Test;

import com.devbliss.doctest.DocTest;

import de.devbliss.apitester.ApiResponse;

/**
 * Example implementation of a unit test class extending the {@link DocTest}.
 * 
 * @author bmary
 * 
 */
public class ExampleIntegrationTest extends DocTest {

    @Test
    public void get() throws Exception {
        ApiResponse resp = makeGetRequest(new URI("http://www.google.com"));
        assertEquals(200, resp.httpStatus);
    }
}