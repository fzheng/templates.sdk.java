package com.example.sdk;

import com.google.gson.JsonObject;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonBuilderFactory;

import static org.junit.Assert.*;

/**
 * The test suite for {@link ApiNode}.
 *
 * @author Feng Zheng
 * @version 1.0
 * @since 1.0
 */
public class ApiNodeTest extends BaseTest {
  /**
   * Sets up test suite.
   *
   * @throws Exception the test exception
   */
  @Before
  public void setUpTest() throws Exception {
    super.setUp();
  }

  /**
   * Tear down test suite.
   */
  @After
  public void tearDownTest() {
    super.tearDown();
  }

  /**
   * Tests {@link ApiNode#getRawResponse()}.
   *
   * @throws Exception the test exception
   */
  @Test
  public void getRawResponseTest() throws Exception {
    MockApp mockApp = new MockApp();
    JsonBuilderFactory factory = Json.createBuilderFactory(null);
    String res = factory.createObjectBuilder().add("id", mockApp.appId).add("token", mockApp.appToken).build().toString();
    addAuthenticateServerClient(GET_BOGUS_APP.resolveUriParams(mockApp.appId), null, null, 200, res);
    BogusApp bogusAppReq = new BogusApp(mockApp.appId, getContext());
    BogusApp bogusApp = bogusAppReq.getBogusApp().execute();
    assertEquals(mockApp.appId, bogusApp.getId());
    assertEquals(mockApp.appToken, bogusApp.getToken());
    assertEquals(res, bogusApp.getRawResponse());
  }

  /**
   * Tests {@link ApiNode#getRawResponseAsJsonObject()}.
   *
   * @throws Exception the test exception
   */
  @Test
  public void getRawResponseAsJsonObjectTest() throws Exception {
    MockApp mockApp = new MockApp();
    JsonBuilderFactory factory = Json.createBuilderFactory(null);
    String res = factory.createObjectBuilder().add("id", mockApp.appId).add("token", mockApp.appToken).build().toString();
    addAuthenticateServerClient(GET_BOGUS_APP.resolveUriParams(mockApp.appId), null, null, 200, res);
    BogusApp bogusAppReq = new BogusApp(mockApp.appId, getContext());
    BogusApp bogusApp = bogusAppReq.getBogusApp().execute();
    assertEquals(mockApp.appId, bogusApp.getId());
    assertEquals(mockApp.appToken, bogusApp.getToken());
    JsonObject rawResponseJsonObject = bogusApp.getRawResponseAsJsonObject();
    assertEquals(mockApp.appId, rawResponseJsonObject.get("id").getAsString());
    assertEquals(mockApp.appToken, rawResponseJsonObject.get("token").getAsString());
  }

  /**
   * Tests {@link ApiNode#head()}.
   *
   * @throws Exception the test exception
   */
  @Test
  public void headTest() throws Exception {
    MockApp mockApp = new MockApp();
    JsonBuilderFactory factory = Json.createBuilderFactory(null);
    String res = factory.createObjectBuilder().add("id", mockApp.appId).add("token", mockApp.appToken).build().toString();
    addAuthenticateServerClient(GET_BOGUS_APP.resolveUriParams(mockApp.appId), null, null, 200, res);
    BogusApp bogusAppReq = new BogusApp(mockApp.appId, getContext());
    BogusApp bogusApp = bogusAppReq.getBogusApp().execute();
    assertEquals(mockApp.appId, bogusApp.getId());
    assertEquals(mockApp.appToken, bogusApp.getToken());
    ApiNode bogusApp1 = bogusApp.head();
    assertEquals(bogusApp, bogusApp1);
  }

  /**
   * Tests {@link ApiNode#setContext(ApiContext)}.
   *
   * @throws Exception the test exception
   */
  @Test
  public void setContextTest() throws Exception {
    MockApp mockApp = new MockApp();
    MockAPIContext mockAPIContext = new MockAPIContext();
    ApiContext originalContext = getContext();
    ApiContext apiContext = new ApiContext(mockAPIContext.appId, mockAPIContext.appSecret, mockAPIContext.appToken);
    BogusApp bogusApp = new BogusApp(mockApp.appId, originalContext);
    assertEquals(originalContext, bogusApp.getContext());
    bogusApp.setContext(apiContext);
    assertEquals(apiContext, bogusApp.getContext());
  }

  /**
   * Tests {@link ApiNode#toString()}.
   *
   * @throws Exception the test exception
   */
  @Test
  public void toStringTest() throws Exception {
    MockApp mockApp = new MockApp();
    JsonBuilderFactory factory = Json.createBuilderFactory(null);
    String res = factory.createObjectBuilder().add("id", mockApp.appId).add("token", mockApp.appToken).build().toString();
    addAuthenticateServerClient(GET_BOGUS_APP.resolveUriParams(mockApp.appId), null, null, 200, res);
    BogusApp bogusAppReq = new BogusApp(mockApp.appId, getContext());
    BogusApp bogusApp = bogusAppReq.getBogusApp().execute();
    assertEquals(res, bogusApp.toString());
  }

  /**
   * Tests {@link ApiNode#parseResponse(Class, String, ApiContext, ApiRequest)}.
   *
   * @throws Exception the test exception
   */
  @Test
  public void parseResponseTest() throws Exception {
    JsonBuilderFactory factory = Json.createBuilderFactory(null);
    JsonArrayBuilder jsonArrayBuilder = factory.createArrayBuilder();
    int numOfApps = faker.number().numberBetween(1, 10);
    for (int i = 0; i < numOfApps; ++i) {
      MockApp mockApp = new MockApp();
      jsonArrayBuilder.add(factory.createObjectBuilder().add("id", mockApp.appId).add("token", mockApp.appToken));
    }
    String res = jsonArrayBuilder.build().toString();
    addAuthenticateServerClient(GET_BOGUS_APPS, null, null, 200, res);
    BogusApp bogusAppReq = new BogusApp(getContext());
    BogusApp.RequestGetBogusApps getBogusAppsRequest = bogusAppReq.getBogusApps();
    ApiNodeList<BogusApp> bogusApps = getBogusAppsRequest.execute();
    assertEquals(numOfApps, bogusApps.size());
  }

  /**
   * Tests invalid response in {@link ApiNode#parseResponse(Class, String, ApiContext, ApiRequest)}.
   *
   * @throws Exception the test exception
   */
  @Test
  public void parseResponseTestInvalidResponse() throws Exception {
    MockApp mockApp = new MockApp();
    String res = "{null}";
    addAuthenticateServerClient(GET_BOGUS_APP.resolveUriParams(mockApp.appId), null, null, 200, res);
    BogusApp bogusAppReq = new BogusApp(mockApp.appId, getContext());
    try {
      BogusApp bogusApp = bogusAppReq.getBogusApp().execute();
      assertNull(bogusApp);
    } catch (ApiException.MalformedResponseException e) {
      assertEquals("Invalid response string: " + res, e.getMessage());
    }
  }

  /**
   * Tests missing secondary key in {@link ApiNode#parseResponse(Class, String, ApiContext, ApiRequest)}.
   *
   * @throws Exception the test exception
   */
  @Test
  public void parseResponseTestMissingSecondaryKeyInResponse() throws Exception {
    MockApp mockApp = new MockApp();
    JsonBuilderFactory factory = Json.createBuilderFactory(null);
    String res = factory.createObjectBuilder()
        .add("data", factory.createObjectBuilder()
            .add(secondaryKey, factory.createObjectBuilder()
                .add("id", mockApp.appId)
                .add("token", mockApp.appToken)))
        .build().toString();
    addAuthenticateServerClient(GET_BOGUS_APP.resolveUriParams(mockApp.appId), null, null, 200, res);
    BogusApp bogusAppReq = new BogusApp(mockApp.appId, getContext());
    BogusApp bogusApp = bogusAppReq.getBogusApp2().execute();
    assertNotNull(bogusApp);
    assertEquals(mockApp.appId, bogusApp.getId());
    assertEquals(mockApp.appToken, bogusApp.getToken());
  }

  /**
   * Tests missing secondary key failure in {@link ApiNode#parseResponse(Class, String, ApiContext, ApiRequest)}.
   *
   * @throws Exception the test exception
   */
  @Test
  public void parseResponseTestMissingSecondaryKeyInResponseFailure() throws Exception {
    MockApp mockApp = new MockApp();
    JsonBuilderFactory factory = Json.createBuilderFactory(null);
    String res = factory.createObjectBuilder()
        .add("data", factory.createObjectBuilder()
            .add("id", mockApp.appId)
            .add("token", mockApp.appToken))
        .build().toString();
    addAuthenticateServerClient(GET_BOGUS_APP.resolveUriParams(mockApp.appId), null, null, 200, res);
    BogusApp bogusAppReq = new BogusApp(mockApp.appId, getContext());
    BogusApp bogusApp = bogusAppReq.getBogusApp2().execute();
    assertNull(bogusApp);
  }
}