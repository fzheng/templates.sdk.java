package com.example.sdk;

import com.example.sdk.ApiEndPoints.ApiEndPoint;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.json.Json;
import javax.json.JsonBuilderFactory;

import static org.junit.Assert.*;

/**
 * The test suite for {@link App}.
 *
 * @author Feng Zheng
 * @version 1.0
 * @since 1.0
 */
public class AppTest extends BaseTest {
  private String newToken = fakeUUID();

  @Before
  public void setUp() throws Exception {
    super.setUp();
    ApiEndPoint endpoint = ApiEndPoints.CREATE_ACCESS_TOKEN.resolveUriParams(getAppId());
    JsonBuilderFactory factory = Json.createBuilderFactory(null);
    String requestBody = factory.createObjectBuilder()
        .add("appSecret", getAppSecret())
        .build().toString();
    addAnonymousServerClient(endpoint, null, requestBody, 200, mockCreateTokenResponse());
  }

  @After
  public void tearDown() {
    super.tearDown();
    newToken = null;
  }

  /**
   * Tests {@link App#createToken()}.
   *
   * @throws Exception the test exception
   */
  @Test
  public void createTokenTest() throws Exception {
    App appReq = new App(getContext());
    App.RequestCreateToken request = appReq.createToken();
    App app = request.execute();
    assertEquals(app, app.head());
    assertEquals(getAppId(), app.getId());
    assertNotNull(app.getToken());
    assertNotEquals(getAppToken(), app.getToken());
    assertEquals(newToken, app.getToken());
    App lastResponse = request.getLastResponse();
    assertEquals(app, lastResponse);
  }

  /**
   * Tests {@link App#getId()}.
   *
   * @throws Exception the test exception
   */
  @Test
  public void getIdTest() throws Exception {
    MockApp mockApp = new MockApp();
    ApiContext context = new ApiContext(mockApp.appId, mockApp.appSecret);
    App app = new App(context);
    assertEquals(mockApp.appId, app.getId());
  }

  /**
   * Tests {@link App#getToken()}.
   *
   * @throws Exception the test exception
   */
  @Test
  public void getTokenTest() throws Exception {
    MockApp mockApp = new MockApp();
    ApiContext context = new ApiContext(mockApp.appId, mockApp.appSecret);
    App app = new App(context);
    assertNull(app.getToken());
    context = new ApiContext(mockApp.appId, mockApp.appSecret, mockApp.appToken);
    app = new App(context);
    assertEquals(mockApp.appToken, app.getToken());
  }

  /**
   * Gets the mocked response of {@link App#createToken()}
   *
   * @return mocked response in {@link String}
   */
  private String mockCreateTokenResponse() {
    JsonBuilderFactory factory = Json.createBuilderFactory(null);
    return factory.createObjectBuilder()
        .add("data", factory.createObjectBuilder()
            .add("token", newToken))
        .build().toString();
  }
}