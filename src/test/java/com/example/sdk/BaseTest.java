package com.example.sdk;

import com.example.sdk.types.HttpMethod;
import com.github.javafaker.Faker;
import com.google.gson.annotations.SerializedName;
import com.example.sdk.ApiEndPoints.ApiEndPoint;
import org.mockserver.client.server.MockServerClient;
import org.mockserver.integration.ClientAndServer;
import org.mockserver.matchers.Times;
import org.mockserver.model.*;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonBuilderFactory;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import java.lang.reflect.Constructor;
import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertTrue;

/**
 * The base class of test suite.
 *
 * @author Feng Zheng
 * @version 1.0
 * @since 1.0
 */
class BaseTest {
  /**
   * The new instance of {@link Faker}, which provides utility methods for generating fake strings, such as names, phone
   * numbers, addresses. generate random strings with given patterns.
   * <p>
   * <p>See the <a href="http://dius.github.io/java-faker/apidocs/index.html" target="_blank">Java Faker</a> for usages</p>
   */
  final static Faker faker = new Faker();
  /**
   * The JSON string that represents an empty object.
   */
  final static String emptyObject = "{}";
  /**
   * The JSON string that represents an empty auth data object.
   */
  final static String emptyAuthDataObject = "{\"ghUsername\":null,\"ghCompany\":null,\"ghAvatar\":null,\"ghDisplayName\":null,\"bbUsername\":null,\"bbWebsite\":null,\"bbAvatar\":null,\"bbDisplayName\":null,\"email\":null,\"username\":null,\"password\":null}";
  /**
   * The mock server URL.
   */
  final static String mockServerURL = "http://localhost:9000";
  /**
   * The mock server API version.
   */
  final static String mockServerVersion = ApiConfig.DEFAULT_API_VERSION;
  final private static DateFormat dateFormat = new SimpleDateFormat(ApiConfig.DATE_FORMAT, Locale.ENGLISH);
  /**
   * The secondary key for bogus node response.
   */
  final static String secondaryKey = "bogus";
  final private String appId, appSecret, appToken;
  final private ApiContext context;

  /**
   * The fake endpoint to get the bogus app.
   */
  static ApiEndPoint GET_BOGUS_APP = BaseTest.mockAPIEndPoint("bogus/%1s", HttpMethod.GET);
  /**
   * The fake endpoint to describe the bogus app options.
   */
  static ApiEndPoint OPTIONS_BOGUS_APP = BaseTest.mockAPIEndPoint("bogus/%1s", HttpMethod.OPTIONS);
  /**
   * The fake endpoint to get the bogus apps.
   */
  static ApiEndPoint GET_BOGUS_APPS = BaseTest.mockAPIEndPoint("bogus", HttpMethod.GET);

  private ClientAndServer mockServer;
  private MockServerClient mockServerClient;

  /**
   * Instantiates a new base test suite.
   */
  BaseTest() {
    appId = fakeUUID();
    appSecret = fakeUUID();
    appToken = fakeUUID();
    context = new ApiContext(mockServerURL, mockServerVersion, appId, appSecret, appToken);
  }

  /**
   * Sets up the base test suite.
   *
   * @throws Exception the test exception
   */
  protected void setUp() throws Exception {
    URL url = new URL(mockServerURL);
    String host = url.getHost();
    int port = url.getPort();
    mockServer = ClientAndServer.startClientAndServer(port);
    mockServerClient = new MockServerClient(host, port);
  }

  /**
   * Tear down the base test suite.
   */
  protected void tearDown() {
    if (mockServer != null) {
      mockServer.stop();
      mockServer = null;
    }
    mockServerClient = null;
  }

  /**
   * Add an anonymous mock server client.
   *
   * @param endpoint              the endpoint
   * @param queryStringParameters the query string parameters
   * @param requestBody           the request body
   * @param statusCode            the status code
   * @param responseBody          the response body
   */
  void addAnonymousServerClient(ApiEndPoint endpoint, List<Parameter> queryStringParameters, String requestBody, int statusCode, String responseBody) {
    addMockServerClient(endpoint, null, queryStringParameters, requestBody, statusCode, responseBody);
  }

  /**
   * Add an authenticate mock server client.
   *
   * @param endpoint              the endpoint
   * @param queryStringParameters the query string parameters
   * @param requestBody           the request body
   * @param statusCode            the status code
   * @param responseBody          the response body
   */
  void addAuthenticateServerClient(ApiEndPoint endpoint, List<Parameter> queryStringParameters, String requestBody, int statusCode, String responseBody) {
    addMockServerClient(endpoint, appToken, queryStringParameters, requestBody, statusCode, responseBody);
  }

  /**
   * Add an authenticate mock server client with customized {@link ApiContext}.
   *
   * @param endpoint              the endpoint
   * @param context               the context
   * @param queryStringParameters the query string parameters
   * @param requestBody           the request body
   * @param statusCode            the status code
   * @param responseBody          the response body
   */
  void addAuthenticateServerClient(ApiEndPoint endpoint, ApiContext context, List<Parameter> queryStringParameters, String requestBody, int statusCode, String responseBody) {
    addMockServerClient(endpoint, context.getAppToken(), queryStringParameters, requestBody, statusCode, responseBody);
  }

  /**
   * Parse date string date.
   *
   * @param dateString the date string
   * @return the date
   * @throws ParseException the parse exception
   */
  static synchronized Date parseDateString(String dateString) throws ParseException {
    return dateFormat.parse(dateString);
  }

  /**
   * Format date string string.
   *
   * @param date the date
   * @return the string
   */
  static synchronized String formatDateString(Date date) {
    return dateFormat.format(date);
  }

  /**
   * Fake uuid string.
   *
   * @return the string
   */
  static String fakeUUID() {
    return UUID.randomUUID().toString();
  }

  /**
   * Gets mock server url.
   *
   * @return the mock server url
   */
  String getMockServerURL() {
    return mockServerURL;
  }

  /**
   * Gets mock server version.
   *
   * @return the mock server version
   */
  String getMockServerVersion() {
    return mockServerVersion;
  }

  /**
   * Gets app id.
   *
   * @return the app id
   */
  String getAppId() {
    return appId;
  }

  /**
   * Gets app secret.
   *
   * @return the app secret
   */
  String getAppSecret() {
    return appSecret;
  }

  /**
   * Gets app token.
   *
   * @return the app token
   */
  String getAppToken() {
    return appToken;
  }

  /**
   * Gets context.
   *
   * @return the context
   */
  ApiContext getContext() {
    return context;
  }

  /**
   * Gets invalid token response.
   *
   * @return the invalid token response
   */
  String getInvalidTokenResponse() {
    JsonBuilderFactory factory = Json.createBuilderFactory(null);
    return factory.createObjectBuilder()
        .add("errors", factory.createArrayBuilder()
            .add(factory.createObjectBuilder()
                .add("status", 11)
                .add("title", "Authentication Error")
                .add("detail", "Invalid token")))
        .build().toString();
  }

  /**
   * Generate pagination response string.
   *
   * @param jsonObjectArray the json object array
   * @param limit           the limit
   * @param offset          the offset
   * @return the string
   */
  String generatePaginationResponse(JsonObjectBuilder[] jsonObjectArray, int limit, int offset) {
    int total = jsonObjectArray.length;
    assertTrue(total > 0);
    assertTrue(limit > 0 && limit <= total);
    assertTrue(offset >= 0 && offset < total);
    JsonBuilderFactory factory = Json.createBuilderFactory(null);
    JsonArrayBuilder jsonArrayBuilder = factory.createArrayBuilder();
    for (int i = offset; i < Math.min(limit + offset, total); ++i) {
      jsonArrayBuilder.add(jsonObjectArray[i]);
    }
    JsonObject value = factory.createObjectBuilder()
        .add("data", jsonArrayBuilder)
        .add("meta", factory.createObjectBuilder()
            .add("pagination", factory.createObjectBuilder()
                .add("limit", limit)
                .add("offset", offset)
                .add("total", total)))
        .build();
    return value.toString();
  }

  private void addMockServerClient(ApiEndPoint endpoint, String appToken, List<Parameter> queryStringParameters, String requestBody, int statusCode, String responseBody) {
    if (queryStringParameters == null) {
      queryStringParameters = Collections.emptyList();
    }
    HttpRequest request = HttpRequest.request()
        .withMethod(endpoint.getMethod().toString())
        .withPath("/" + mockServerVersion + "/" + endpoint.getUri())
        .withQueryStringParameters(queryStringParameters)
        .withBody(requestBody);
    if (appToken != null) {
      request.withHeader(new Header("Authorization", appToken));
    }
    HttpResponse response = HttpResponse.response()
        .withStatusCode(statusCode)
        .withHeaders(
            new Header("Content-Type", "application/json; charset=utf-8")
        )
        .withBody(responseBody)
        .withDelay(new Delay(TimeUnit.MILLISECONDS, 10));
    mockServerClient.when(request, Times.exactly(1)).respond(response);
  }

  /**
   * The class of a Bogus app.
   */
  static class BogusApp extends ApiNode {
    @SerializedName("id")
    private String id = null;

    @SerializedName("token")
    private String token = null;

    /**
     * Instantiates a new Bogus app.
     *
     * @param context the context
     */
    BogusApp(ApiContext context) {
      this(null, context);
    }

    /**
     * Instantiates a new Bogus app.
     *
     * @param id      the id
     * @param context the context
     */
    BogusApp(String id, ApiContext context) {
      this.id = id;
      this.setContext(context);
    }

    public String getId() {
      return id;
    }

    /**
     * Gets token.
     *
     * @return the token
     */
    String getToken() {
      return token;
    }

    /**
     * Gets bogus app.
     *
     * @return the bogus app
     */
    RequestGetBogusApp getBogusApp() {
      return new RequestGetBogusApp(this);
    }

    /**
     * Gets bogus app with secondary key in response.
     *
     * @return the bogus app with secondary key in response
     */
    RequestGetBogusApp2 getBogusApp2() {
      return new RequestGetBogusApp2(this);
    }

    /**
     * Gets bogus app with secondary key in response.
     *
     * @return the bogus app with secondary key in response
     */
    RequestOptionsBogusApp fakeActBogusApp() {
      return new RequestOptionsBogusApp(this);
    }

    /**
     * Gets bogus apps.
     *
     * @return the bogus apps
     */
    RequestGetBogusApps getBogusApps() {
      return new RequestGetBogusApps(this.getContext());
    }

    /**
     * The type Request get bogus app.
     */
    static class RequestGetBogusApp extends ApiRequest {
      /**
       * The Last response.
       */
      BogusApp lastResponse = null;

      /**
       * Instantiates a new Request get bogus app.
       *
       * @param bogusApp the bogus app
       */
      RequestGetBogusApp(BogusApp bogusApp) {
        super(bogusApp.getContext(), BaseTest.GET_BOGUS_APP, bogusApp.id);
      }

      @Override
      public BogusApp getLastResponse() {
        return lastResponse;
      }

      @Override
      public BogusApp execute() throws ApiException, InterruptedException {
        return execute(null);
      }

      @Override
      public BogusApp execute(Map<String, Object> extraParams) throws ApiException, InterruptedException {
        lastResponse = BogusApp.parseResponse(BogusApp.class, executeInternal(extraParams), getContext(), this).head();
        return lastResponse;
      }
    }

    /**
     * The type Request get bogus app with secondary key in response.
     */
    static class RequestGetBogusApp2 extends ApiRequest {
      /**
       * The Last response.
       */
      BogusApp lastResponse = null;

      /**
       * Instantiates a new Request get bogus app with secondary key in response.
       *
       * @param bogusApp the bogus app
       */
      RequestGetBogusApp2(BogusApp bogusApp) {
        super(bogusApp.getContext(), BaseTest.GET_BOGUS_APP, bogusApp.id);
      }

      @Override
      public BogusApp getLastResponse() {
        return lastResponse;
      }

      @Override
      public BogusApp execute() throws ApiException, InterruptedException {
        return execute(null);
      }

      @Override
      public BogusApp execute(Map<String, Object> extraParams) throws ApiException, InterruptedException {
        lastResponse = BogusApp.parseResponse(BogusApp.class, secondaryKey, executeInternal(extraParams), getContext(), this).head();
        return lastResponse;
      }
    }

    /**
     * The type Request describe bogus app options with {@link HttpMethod#OPTIONS}.
     */
    static class RequestOptionsBogusApp extends ApiRequest {
      /**
       * The Last response.
       */
      BogusApp lastResponse = null;

      /**
       * Instantiates a new Request get bogus app with secondary key in response.
       *
       * @param bogusApp the bogus app
       */
      RequestOptionsBogusApp(BogusApp bogusApp) {
        super(bogusApp.getContext(), BaseTest.OPTIONS_BOGUS_APP, bogusApp.id);
      }

      @Override
      public BogusApp getLastResponse() {
        return lastResponse;
      }

      @Override
      public BogusApp execute() throws ApiException, InterruptedException {
        return execute(null);
      }

      @Override
      public BogusApp execute(Map<String, Object> extraParams) throws ApiException, InterruptedException {
        lastResponse = BogusApp.parseResponse(BogusApp.class, executeInternal(extraParams), getContext(), this).head();
        return lastResponse;
      }
    }

    /**
     * The type Request get bogus apps.
     */
    static class RequestGetBogusApps extends ApiRequest {
      /**
       * The Last response.
       */
      ApiNodeList<BogusApp> lastResponse = null;

      /**
       * Instantiates a new Request get bogus apps.
       *
       * @param apiContext the api context
       */
      RequestGetBogusApps(ApiContext apiContext) {
        super(apiContext, BaseTest.GET_BOGUS_APPS);
      }

      @Override
      public ApiNodeList<BogusApp> getLastResponse() {
        return lastResponse;
      }

      @Override
      public ApiNodeList<BogusApp> execute() throws ApiException, InterruptedException {
        return execute(null);
      }

      @Override
      public ApiNodeList<BogusApp> execute(Map<String, Object> extraParams) throws ApiException, InterruptedException {
        lastResponse = BogusApp.parseResponse(BogusApp.class, executeInternal(extraParams), getContext(), this);
        return lastResponse;
      }
    }
  }

  /**
   * The mock type of {@link App}.
   */
  static class MockApp {
    final String appId = fakeUUID();
    final String appSecret = faker.internet().password();
    final String appToken = fakeUUID();
  }

  /**
   * The mock type of {@link ApiContext}.
   */
  static class MockAPIContext {
    final String appId = fakeUUID();
    final String appSecret = faker.internet().password();
    final String appToken = fakeUUID();
  }

  private static ApiEndPoint mockAPIEndPoint(String url, HttpMethod httpMethod) {
    Class[] cArg = new Class[2];
    cArg[0] = String.class;
    cArg[1] = HttpMethod.class;
    try {
      Constructor<ApiEndPoint> constructor = ApiEndPoint.class.getDeclaredConstructor(cArg);
      constructor.setAccessible(true);
      return constructor.newInstance(url, httpMethod);
    } catch (RuntimeException e) {
      throw e;
    } catch (Exception e) {
      return null;
    }
  }
}
