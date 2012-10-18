package integration;

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

import org.apache.http.client.utils.URIBuilder;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
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
 * This class is used in the {@link ReportCreatedIntegrationTest} class.
 * 
 * @author bmary
 * 
 */
@RunWith(MockitoJUnitRunner.class)
public class RequestsIntegrationTest extends DocTest {

    private static final String JSON_TEXT = "The response contains a JSON payload";
    private static final String HTTP_TEXT = "The response contains the HTTP_STATUS of the request";
    private static final String PAYLOAD = "{'abc':'123'}";
    private static final int HTTP_STATUS = 230;
    private static final String REASON_PHRASE = "This is not a normal response code";
    private static final String HEADER_VALUE1 = "application/json";
    private static final String HEADER_VALUE2 = "value2";
    private static final String HEADER_NAME1 = "Content-type";
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
        headersToShow.add("Content-type");
        headersToShow.add("Cookie");
        return headersToShow;
    }

    private Object obj;
    private ApiResponse apiResponse;
    private URI uri;
    private Context context;
    private ApiRequest apiRequest;
    private final Map<String, String> headers = new HashMap<String, String>();

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        obj = new Object();
        uri =
                new URIBuilder().setScheme("http").setHost("www.hostname.com").setPort(8080)
                        .setPath("/resource/id:12345").build();

        headers.put(HEADER_NAME1, HEADER_VALUE1);
        headers.put(HEADER_NAME2, HEADER_VALUE2);

        apiResponse = new ApiResponse(HTTP_STATUS, REASON_PHRASE, PAYLOAD, headers);
    }

    @Test
    public void get() throws Exception {
        apiRequest = new ApiRequest(uri, "get", headers);
        context = new Context(apiResponse, apiRequest);
        when(API.get(uri)).thenReturn(context);

        sayNextSection("Making a get request");
        ApiResponse resp = makeGetRequest(uri);

        assertEqualsAndSay(HTTP_STATUS, resp.httpStatus, HTTP_TEXT);
        assertEqualsAndSay(PAYLOAD, resp.payload, JSON_TEXT);
    }

    @Test
    public void delete() throws Exception {
        apiRequest = new ApiRequest(uri, "delete", headers);
        context = new Context(apiResponse, apiRequest);
        when(API.delete(uri, null)).thenReturn(context);

        sayNextSection("Making a delete request");
        ApiResponse response = makeDeleteRequest(uri);

        assertEqualsAndSay(HTTP_STATUS, response.httpStatus, HTTP_TEXT);
        assertEqualsAndSay(PAYLOAD, response.payload, JSON_TEXT);
    }

    @Test
    public void post() throws Exception {
        apiRequest = new ApiRequest(uri, "post", headers);
        context = new Context(apiResponse, apiRequest);
        when(API.post(uri, obj)).thenReturn(context);

        sayNextSection("Making a post request");
        ApiResponse response = makePostRequest(uri, obj);

        assertEqualsAndSay(HTTP_STATUS, response.httpStatus, HTTP_TEXT);
        assertEqualsAndSay(PAYLOAD, response.payload, JSON_TEXT);
    }

    @Test
    public void put() throws Exception {
        apiRequest = new ApiRequest(uri, "put", headers);
        context = new Context(apiResponse, apiRequest);
        when(API.put(uri, obj)).thenReturn(context);

        sayNextSection("Making a put request with encöding chäracters");
        ApiResponse response = makePutRequest(uri, obj);

        assertEqualsAndSay(HTTP_STATUS, response.httpStatus, HTTP_TEXT);
        assertEqualsAndSay(PAYLOAD, response.payload, JSON_TEXT);
    }

    @Test
    public void postUploadText() throws Exception {
        apiRequest = new ApiRequest(uri, "post", headers);
        context = new Context(apiResponse, apiRequest);
        when(API.post(eq(uri), eq(null), isA(PostUploadWithoutRedirectImpl.class))).thenReturn(
                context);
        sayNextSection("Making an upload post request");

        ApiResponse response =
                makePostUploadRequest(uri, new File("src/test/resources/file.txt"), "paramName");
        assertEqualsAndSay(HTTP_STATUS, response.httpStatus, HTTP_TEXT);
        assertEqualsAndSay(PAYLOAD, response.payload, JSON_TEXT);
    }

    @Test
    public void postUploadImage() throws Exception {
        apiRequest = new ApiRequest(uri, "post", headers);
        context = new Context(apiResponse, apiRequest);
        when(API.post(eq(uri), eq(null), isA(PostUploadWithoutRedirectImpl.class))).thenReturn(
                context);
        sayNextSection("Making an upload post request with an image file");

        ApiResponse response =
                makePostUploadRequest(uri, new File("src/test/resources/picture.png"), "paramName");

        assertEqualsAndSay(HTTP_STATUS, response.httpStatus, HTTP_TEXT);
        assertEqualsAndSay(PAYLOAD, response.payload, JSON_TEXT);
    }
}