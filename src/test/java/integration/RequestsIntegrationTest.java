package integration;

import static org.junit.Assert.assertNull;
import static org.mockito.Matchers.eq;
import static org.mockito.Matchers.isA;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.File;
import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpStatus;
import org.apache.http.client.utils.URIBuilder;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import com.devbliss.doctest.DocTest;
import com.devbliss.doctest.httpfactory.PostUploadWithoutRedirectImpl;

import de.devbliss.apitester.ApiRequest;
import de.devbliss.apitester.ApiResponse;
import de.devbliss.apitester.ApiTest;
import de.devbliss.apitester.Context;

/**
 * Example implementation of a unit test class extending the {@link DocTest}.
 * 
 * This class is used in the {@link ReportCreatedIntegrationTest} class and describes how to use the
 * doctest library to make some http requests.
 * 
 * @author bmary
 * 
 */
@RunWith(MockitoJUnitRunner.class)
public class RequestsIntegrationTest extends DocTest {

    private static final String JSON_TEXT = "The response contains a JSON payload";
    private static final String HTTP_TEXT = "The response contains the HTTP_STATUS of the request";
    private static final String PAYLOAD =
            "{'abc':'123', 'cde': {'start': 'today', 'end':'tomorrow'}}";
    private static final String REASON_PHRASE = "This is not a normal response code";
    private static final String HEADER_VALUE1 = "application/json";
    private static final String HEADER_VALUE2 = "value2";
    private static final String HEADER_NAME1 = "content-type";
    private static final String HEADER_NAME2 = "name2";
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

    @Override
    public List<String> showHeaders() {
        ArrayList<String> headersToShow = new ArrayList<String>();
        headersToShow.add(HEADER_NAME1);
        headersToShow.add("Cookie");
        return headersToShow;
    }

    private Object obj;
    private ApiResponse apiResponse;
    private URI uri;
    private Context context;
    private ApiRequest apiRequest;
    private Map<String, String> headers;

    @Before
    public void setUp() throws Exception {
        obj = new TestObject();
        uri =
                new URIBuilder().setScheme("http").setHost("www.hostname.com").setPort(8080)
                        .setPath("/resource/id:12345").build();
        headers = new HashMap<String, String>();
        headers.put(HEADER_NAME1, HEADER_VALUE1);
        headers.put(HEADER_NAME2, HEADER_VALUE2);
    }

    @Test
    public void get() throws Exception {
        apiRequest = new ApiRequest(uri, "get", headers);
        apiResponse = new ApiResponse(HttpStatus.SC_OK, REASON_PHRASE, PAYLOAD, headers);
        context = new Context(apiResponse, apiRequest);
        when(API.get(uri)).thenReturn(context);

        sayNextSection("Making a get request");
        ApiResponse resp = makeGetRequest(uri);

        assertEqualsAndSay(HttpStatus.SC_OK, resp.httpStatus, HTTP_TEXT);
        assertEqualsAndSay(PAYLOAD, resp.payload, JSON_TEXT);
    }

    @Test
    public void delete() throws Exception {
        apiRequest = new ApiRequest(uri, "delete", headers);
        apiResponse = new ApiResponse(HttpStatus.SC_NO_CONTENT, REASON_PHRASE, null, headers);
        context = new Context(apiResponse, apiRequest);
        when(API.delete(uri, null)).thenReturn(context);

        sayNextSection("Making a delete request");
        ApiResponse response = makeDeleteRequest(uri);

        assertEqualsAndSay(HttpStatus.SC_NO_CONTENT, response.httpStatus, HTTP_TEXT);
        assertNull(response.payload);
    }

    @Test
    public void post() throws Exception {
        apiRequest = new ApiRequest(uri, "post", headers);
        apiResponse = new ApiResponse(HttpStatus.SC_CREATED, REASON_PHRASE, PAYLOAD, headers);
        context = new Context(apiResponse, apiRequest);
        when(API.post(uri, obj)).thenReturn(context);

        sayNextSection("Making a post request");
        ApiResponse response = makePostRequest(uri, obj);

        assertEqualsAndSay(HttpStatus.SC_CREATED, response.httpStatus, HTTP_TEXT);
        assertEqualsAndSay(PAYLOAD, response.payload, JSON_TEXT);
    }

    @Test
    public void put() throws Exception {
        apiRequest = new ApiRequest(uri, "put", headers);
        apiResponse = new ApiResponse(HttpStatus.SC_NO_CONTENT, REASON_PHRASE, PAYLOAD, headers);
        context = new Context(apiResponse, apiRequest);
        when(API.put(uri, obj)).thenReturn(context);

        sayNextSection("Making a put request with encöding chäracters");
        ApiResponse response = makePutRequest(uri, obj);

        assertEqualsAndSay(HttpStatus.SC_NO_CONTENT, response.httpStatus, HTTP_TEXT);
        assertEqualsAndSay(PAYLOAD, response.payload, JSON_TEXT);
    }

    @Test
    public void postUploadText() throws Exception {
        apiRequest = new ApiRequest(uri, "post", headers);
        apiResponse = new ApiResponse(HttpStatus.SC_CREATED, "", null, headers);
        context = new Context(apiResponse, apiRequest);
        when(API.post(eq(uri), eq(null), isA(PostUploadWithoutRedirectImpl.class))).thenReturn(
                context);
        sayNextSection("Making an upload post request");

        ApiResponse response =
                makePostUploadRequest(uri, new File("src/test/resources/file.txt"), "paramName");
        assertEqualsAndSay(HttpStatus.SC_CREATED, response.httpStatus, HTTP_TEXT);
        assertNull(response.payload);
    }

    @Test
    public void postUploadImage() throws Exception {
        apiRequest = new ApiRequest(uri, "post", headers);
        apiResponse = new ApiResponse(HttpStatus.SC_CREATED, "", null, headers);
        context = new Context(apiResponse, apiRequest);
        when(API.post(eq(uri), eq(null), isA(PostUploadWithoutRedirectImpl.class))).thenReturn(
                context);
        sayNextSection("Making an upload post request with an image file");

        ApiResponse response =
                makePostUploadRequest(uri, new File("src/test/resources/picture.png"), "paramName");

        assertEqualsAndSay(HttpStatus.SC_CREATED, response.httpStatus, HTTP_TEXT);
        assertNull(response.payload);
    }

    @Test
    public void suiteRequests() throws Exception {
        sayNextSection("Suite of requests");

        say("All requests are independent and can be done in a sequentially way.");
        say("Let's first upload a resource:");

        apiRequest = new ApiRequest(uri, "post", headers);
        apiResponse = new ApiResponse(HttpStatus.SC_CREATED, "", null, headers);
        context = new Context(apiResponse, apiRequest);
        when(API.post(eq(uri), eq(null), isA(PostUploadWithoutRedirectImpl.class))).thenReturn(
                context);

        ApiResponse response =
                makePostUploadRequest(uri, new File("src/test/resources/picture.png"), "paramName");

        assertEqualsAndSay(HttpStatus.SC_CREATED, response.httpStatus, HTTP_TEXT);
        assertNull(response.payload);

        say("And now we would like to update another resource:");
        apiRequest = new ApiRequest(uri, "put", headers);
        apiResponse = new ApiResponse(HttpStatus.SC_OK, "", PAYLOAD, headers);
        context = new Context(apiResponse, apiRequest);
        when(API.put(uri, obj)).thenReturn(context);

        response = makePutRequest(uri, obj);

        assertEqualsAndSay(HttpStatus.SC_OK, response.httpStatus, HTTP_TEXT);
        assertEqualsAndSay(PAYLOAD, response.payload, JSON_TEXT);
    }
}