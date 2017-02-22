package com.example.sdk;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * The test suite for {@link ApiContext}.
 *
 * @author Feng Zheng
 * @version 1.0
 * @since 1.0
 */
public class ApiContextTest extends BaseTest {
  /**
   * Tests {@link ApiContext#getApiBase()}.
   *
   * @throws Exception the test exception
   */
  @Test
  public void getApiBaseTest() throws Exception {
    String fakeAppId = fakeUUID();
    String fakeAppSecret = fakeUUID();
    ApiContext context = new ApiContext(fakeAppId, fakeAppSecret);
    assertEquals(ApiConfig.DEFAULT_API_BASE, context.getApiBase());
  }

  /**
   * Tests {@link ApiContext#getVersion()}.
   *
   * @throws Exception the test exception
   */
  @Test
  public void getVersionTest() throws Exception {
    String fakeAppId = fakeUUID();
    String fakeAppSecret = fakeUUID();
    ApiContext context = new ApiContext(fakeAppId, fakeAppSecret);
    assertEquals(ApiConfig.DEFAULT_API_VERSION, context.getVersion());
  }

  /**
   * Tests {@link ApiContext#getAppId()}.
   *
   * @throws Exception the test exception
   */
  @Test
  public void getAppIdTest() throws Exception {
    String fakeAppId = fakeUUID();
    String fakeAppSecret = fakeUUID();
    ApiContext context = new ApiContext(fakeAppId, fakeAppSecret);
    assertEquals(fakeAppId, context.getAppId());
  }

  /**
   * Tests {@link ApiContext#getAppSecret()}.
   *
   * @throws Exception the test exception
   */
  @Test
  public void getAppSecretTest() throws Exception {
    String fakeAppId = fakeUUID();
    String fakeAppSecret = fakeUUID();
    ApiContext context = new ApiContext(fakeAppId, fakeAppSecret);
    assertEquals(fakeAppSecret, context.getAppSecret());
  }

  /**
   * Tests {@link ApiContext#getAppToken()}
   *
   * @throws Exception the test exception
   */
  @Test
  public void getAppTokenTest() throws Exception {
    String fakeAppId = fakeUUID();
    String fakeAppSecret = fakeUUID();
    String fakeAppToken = fakeUUID();
    ApiContext context = new ApiContext(fakeAppId, fakeAppSecret, fakeAppToken);
    assertEquals(fakeAppToken, context.getAppToken());
  }

  /**
   * Tests {@link ApiContext#setAppToken(String)}.
   *
   * @throws Exception the exception
   */
  @Test
  public void setAppTokenTest() throws Exception {
    String fakeAppId = fakeUUID();
    String fakeAppSecret = fakeUUID();
    String fakeAppToken = fakeUUID();
    ApiContext context = new ApiContext(fakeAppId, fakeAppSecret, fakeAppToken);
    assertEquals(fakeAppToken, context.getAppToken());
    String anotherFakeAppToken = fakeUUID();
    assertNotEquals(fakeAppToken, anotherFakeAppToken);
    context.setAppToken(anotherFakeAppToken);
    assertEquals(anotherFakeAppToken, context.getAppToken());
  }

  /**
   * Tests {@link ApiContext#clearAppToken()}.
   *
   * @throws Exception the exception
   */
  @Test
  public void clearAppTokenTest() throws Exception {
    String fakeAppId = fakeUUID();
    String fakeAppSecret = fakeUUID();
    String fakeAppToken = fakeUUID();
    ApiContext context = new ApiContext(fakeAppId, fakeAppSecret, fakeAppToken);
    assertEquals(fakeAppToken, context.getAppToken());
    context.clearAppToken();
    assertNull(context.getAppToken());
  }

  /**
   * Tests {@link ApiContext#hasAppToken()}.
   *
   * @throws Exception the exception
   */
  @Test
  public void hasAppTokenTest() throws Exception {
    String fakeAppId = fakeUUID();
    String fakeAppSecret = fakeUUID();
    String fakeAppToken = fakeUUID();
    ApiContext context = new ApiContext(fakeAppId, fakeAppSecret, fakeAppToken);
    assertTrue(context.hasAppToken());
    context.clearAppToken();
    assertFalse(context.hasAppToken());
  }
}