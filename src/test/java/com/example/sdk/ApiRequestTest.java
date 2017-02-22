package com.example.sdk;

import com.example.sdk.ApiException.FailedRequestException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.json.Json;
import javax.json.JsonBuilderFactory;
import java.net.HttpURLConnection;
import java.util.Map;

import static org.junit.Assert.*;

/**
 * The test suite for {@link ApiRequest}.
 *
 * @author Feng Zheng
 * @version 1.0
 * @since 1.0
 */
public class ApiRequestTest extends BaseTest {
  @Before
  public void setUp() throws Exception {
    super.setUp();
  }

  @After
  public void tearDown() {
    super.tearDown();
  }

  /**
   * Tests invalid HTTP Method failure in {@link ApiRequest#execute(Map)}.
   *
   * @throws Exception the test exception
   */
  @Test
  public void executeTestInvalidHTTPMethodFailure() throws Exception {
    MockApp mockApp = new MockApp();
    JsonBuilderFactory factory = Json.createBuilderFactory(null);
    String res = factory.createObjectBuilder().add("id", mockApp.appId).add("token", mockApp.appToken).build().toString();
    addAuthenticateServerClient(OPTIONS_BOGUS_APP.resolveUriParams(mockApp.appId), null, null, 200, res);
    BogusApp bogusAppReq = new BogusApp(mockApp.appId, getContext());
    try {
      BogusApp bogusApp = bogusAppReq.fakeActBogusApp().execute();
      assertNull(bogusApp);
    } catch (IllegalArgumentException e) {
      assertEquals("Unsupported http request method", e.getMessage());
    }
  }
}