package integration;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.net.URI;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.devbliss.doctest.DocTest;
import com.devbliss.doctest.Response;

import de.devbliss.apitester.ApiTest;

/**
 * Example implementation of a unit test class extending the {@link DocTest}.
 * 
 * @Warning This class is used in the {@link ReportCreatedIntegrationTest} class.
 * 
 * @author bmary
 * 
 */
public class ExampleIntegrationTest extends DocTest {

    private static final String PAYLOAD = "payload";
    private static final int HTTP_STATUS = 230;
    private static ApiTest api;

    @BeforeClass
    public static void beforeClass() {
        api = mock(ApiTest.class);
        DocTest.setApi(api);
    }

    private Object obj;
    private Response response;
    private URI uri;

    @Before
    public void setUp() throws Exception {
        obj = new Object();
        response = new Response(HTTP_STATUS, PAYLOAD);
        uri = new URI("http://www.google.com");
    }

    @Test
    public void get() throws Exception {
        when(api.get(uri)).thenReturn(response);
        Response resp = makeGetRequest(uri);

        assertEquals(HTTP_STATUS, resp.httpStatus);
        assertEquals(PAYLOAD, resp.payload);
    }

    @Test
    public void delete() throws Exception {
        when(api.delete(uri, null)).thenReturn(response);
        Response response = makeDeleteRequest(uri);

        assertEquals(HTTP_STATUS, response.httpStatus);
        assertEquals(PAYLOAD, response.payload);
    }

    @Test
    public void post() throws Exception {
        when(api.post(uri, obj)).thenReturn(response);
        Response response = makePostRequest(uri, obj);

        assertEquals(HTTP_STATUS, response.httpStatus);
        assertEquals(PAYLOAD, response.payload);
    }

    @Test
    public void put() throws Exception {
        when(api.put(uri, obj)).thenReturn(response);
        Response response = makePutRequest(uri, obj);

        assertEquals(HTTP_STATUS, response.httpStatus);
        assertEquals(PAYLOAD, response.payload);
    }
}