package integration;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.eq;
import static org.mockito.Matchers.isA;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.File;
import java.net.URI;
import java.util.Collections;

import org.apache.http.client.utils.URIBuilder;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.MockitoAnnotations;

import com.devbliss.doctest.DocTest;
import com.devbliss.doctest.Response;
import com.devbliss.doctest.httpfactory.PostUploadWithoutRedirectImpl;

import de.devbliss.apitester.ApiResponse;
import de.devbliss.apitester.ApiTest;

/**
 * Example implementation of a unit test class extending the {@link DocTest}.
 * 
 * This class is used in the {@link ReportCreatedIntegrationTest} class.
 * 
 * @author bmary
 * 
 */
public class RequestsIntegrationTest extends DocTest {

    private static final String JSON_TEXT = "The response contains a JSON payload";
    private static final String HTTP_TEXT = "The response contains the HTTP_STATUS of the request";
    private static final String PAYLOAD = "{'abc':'123'}";
    private static final int HTTP_STATUS = 230;
    private static final String REASON_PHRASE = "This is not a normal response code";
    private static ApiTest API;

    @BeforeClass
    public static void beforeClass() {
        API = mock(ApiTest.class);
        DocTest.setApi(API);
    }

    @Override
    protected String getFileName() {
        return "HttpRequests";
    }

    @Override
    public String getIntroduction() {
        return "This documentation describes the input/output of the four http methods.";
    }

    private Object obj;
    private ApiResponse response;
    private URI uri;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        obj = new Object();
        response =
                new ApiResponse(HTTP_STATUS, REASON_PHRASE, PAYLOAD, Collections
                        .<String, String> emptyMap());
        uri =
                new URIBuilder().setScheme("http").setHost("www.hostname.com").setPort(8080)
                        .setPath("/resource/id:12345").build();
        when(API.put(uri, obj)).thenReturn(response);
        when(API.get(uri)).thenReturn(response);
        when(API.delete(uri, null)).thenReturn(response);
        when(API.post(uri, obj)).thenReturn(response);
        when(API.post(uri, null)).thenReturn(response);
        when(API.post(eq(uri), eq(null), isA(PostUploadWithoutRedirectImpl.class))).thenReturn(
                response);
    }

    @Test
    public void get() throws Exception {
        sayNextSection("Making a get request");
        Response resp = makeGetRequest(uri);

        assertEqualsAndSay(HTTP_STATUS, resp.httpStatus, HTTP_TEXT);
        assertEqualsAndSay(PAYLOAD, resp.payload, JSON_TEXT);
    }

    @Test
    public void delete() throws Exception {
        sayNextSection("Making a delete request");
        Response response = makeDeleteRequest(uri);

        assertEqualsAndSay(HTTP_STATUS, response.httpStatus, HTTP_TEXT);
        assertEqualsAndSay(PAYLOAD, response.payload, JSON_TEXT);
    }

    @Test
    public void post() throws Exception {
        sayNextSection("Making a post request");
        Response response = makePostRequest(uri, obj);

        assertEqualsAndSay(HTTP_STATUS, response.httpStatus, HTTP_TEXT);
        assertEqualsAndSay(PAYLOAD, response.payload, JSON_TEXT);
    }

    @Test
    public void put() throws Exception {
        sayNextSection("Making a put request with encöding chäracters");
        Response response = makePutRequest(uri, obj);

        assertEqualsAndSay(HTTP_STATUS, response.httpStatus, HTTP_TEXT);
        assertEqualsAndSay(PAYLOAD, response.payload, JSON_TEXT);
    }

    @Test
    public void postUpload() throws Exception {
        sayNextSection("Making an upload post request");

        Response response =
                makePostUploadRequest(uri, new File("src/test/resources/file.txt"), "paramName");
        assertEqualsAndSay(HTTP_STATUS, response.httpStatus, HTTP_TEXT);
        assertEqualsAndSay(PAYLOAD, response.payload, JSON_TEXT);
    }

    @Test
    public void postUploadImage() throws Exception {
        sayNextSection("Making an upload post request with an image file");

        Response response =
                makePostUploadRequest(uri, new File("src/test/resources/picture.png"), "paramName");

        assertEqualsAndSay(HTTP_STATUS, response.httpStatus, HTTP_TEXT);
        assertEqualsAndSay(PAYLOAD, response.payload, JSON_TEXT);
    }

    @Test
    public void suiteRequests() throws Exception {

        Response response =
                makePostUploadRequest(uri, new File("src/test/resources/file.txt"), "paramName");
        assertEquals(HTTP_STATUS, response.httpStatus);
        assertEquals(PAYLOAD, response.payload);

        response = makeGetRequest(uri);
        assertEquals(HTTP_STATUS, response.httpStatus);
        assertEquals(PAYLOAD, response.payload);

        response = makeDeleteRequest(uri);
        assertEquals(HTTP_STATUS, response.httpStatus);

        response = makePostRequest(uri, obj);
        assertEquals(HTTP_STATUS, response.httpStatus);
        assertEquals(PAYLOAD, response.payload);

        response = makePutRequest(uri, obj);
        assertEquals(HTTP_STATUS, response.httpStatus);
        assertEquals(PAYLOAD, response.payload);
    }
}