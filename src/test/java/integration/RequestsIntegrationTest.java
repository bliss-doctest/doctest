package integration;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.File;
import java.net.URI;
import java.util.Collections;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.devbliss.doctest.DocTest;
import com.devbliss.doctest.Response;

import de.devbliss.apitester.ApiResponse;
import de.devbliss.apitester.ApiTest;

/**
 * Example implementation of a unit test class extending the {@link DocTest}.
 * 
 * @Warning This class is used in the {@link ReportCreatedIntegrationTest} class.
 * 
 * @author bmary
 * 
 */
public class RequestsIntegrationTest extends DocTest {

    private static final String PAYLOAD = "{'abc':'123'}";
    private static final int HTTP_STATUS = 230;
    private static final String REASON_PHRASE = "This is not a normal response code";
    private static ApiTest api;

    @BeforeClass
    public static void beforeClass() {
        api = mock(ApiTest.class);
        DocTest.setApi(api);
    }

    private Object obj;
    private ApiResponse response;
    private URI uri;

    @Before
    public void setUp() throws Exception {
        obj = new Object();
        response =
                new ApiResponse(HTTP_STATUS, REASON_PHRASE, PAYLOAD, Collections
                        .<String, String> emptyMap());
        uri = new URI("http://www.google.com");
    }

    @Test
    public void get() throws Exception {
        sayNextSection("Making a get request");
        when(api.get(uri)).thenReturn(response);
        Response resp = makeGetRequest(uri);

        assertEqualsAndSay(HTTP_STATUS, resp.httpStatus);
        assertEqualsAndSay(PAYLOAD, resp.payload);
    }

    @Test
    public void delete() throws Exception {
        sayNextSection("Making a delete request");
        when(api.delete(uri, null)).thenReturn(response);
        Response response = makeDeleteRequest(uri);

        assertEqualsAndSay(HTTP_STATUS, response.httpStatus);
        assertEqualsAndSay(PAYLOAD, response.payload);
    }

    @Test
    public void post() throws Exception {
        sayNextSection("Making a post request");
        when(api.post(uri, obj)).thenReturn(response);
        Response response = makePostRequest(uri, obj);

        assertEqualsAndSay(HTTP_STATUS, response.httpStatus);
        assertEqualsAndSay(PAYLOAD, response.payload);
    }

    @Test
    public void put() throws Exception {
        sayNextSection("Making a put request");
        when(api.put(uri, obj)).thenReturn(response);
        Response response = makePutRequest(uri, obj);

        assertEqualsAndSay(HTTP_STATUS, response.httpStatus);
        assertEqualsAndSay(PAYLOAD, response.payload);
    }

    @Test
    public void postUpload() throws Exception {
        sayNextSection("Making an upload post request");
        when(api.post(uri, null)).thenReturn(response);

        Response response = makePostUploadRequest(uri, new File("."), "paramName");
        assertEqualsAndSay(HTTP_STATUS, response.httpStatus);
        assertEqualsAndSay(PAYLOAD, response.payload);
    }
}