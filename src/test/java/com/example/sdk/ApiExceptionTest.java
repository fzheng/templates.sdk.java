package com.example.sdk;

import com.google.gson.JsonObject;
import com.example.sdk.ApiException.FailedAccessTokenException;
import com.example.sdk.ApiException.FailedRequestException;
import com.example.sdk.ApiException.MalformedResponseException;
import com.example.sdk.ApiException.NotImplementedException;
import org.junit.Test;

import java.io.IOException;
import java.lang.instrument.IllegalClassFormatException;
import java.sql.SQLException;
import java.util.EmptyStackException;

import static org.junit.Assert.*;

/**
 * The test suite for {@link ApiException}.
 *
 * @author Feng Zheng
 * @version 1.0
 * @since 1.0
 */
public class ApiExceptionTest extends BaseTest {
  /**
   * Tests constructors.
   *
   * @throws Exception the test exception
   */
  @Test
  public void testConstructors() throws Exception {
    ApiException apiException = new ApiException();
    assertNull(apiException.getMessage());
    Exception exception = new EmptyStackException();
    apiException = new ApiException(exception);
    assertEquals(exception, apiException.getCause());
    String customMessage = faker.lorem().sentence();
    apiException = new ApiException(customMessage);
    assertEquals(customMessage, apiException.getMessage());
    customMessage = faker.lorem().sentence();
    assertNotEquals(customMessage, apiException.getMessage());
    exception = new SQLException();
    apiException = new ApiException(customMessage, exception);
    assertEquals(customMessage, apiException.getMessage());
    assertEquals(exception, apiException.getCause());
  }

  /**
   * Tests {@link ApiException#head()}.
   *
   * @throws Exception the test exception
   */
  @Test
  public void headTest() throws Exception {
    Exception exception = new IOException();
    ApiException apiException = new ApiException(exception);
    assertNull(apiException.head());
  }

  /**
   * Tests {@link ApiException#getRawResponse()}.
   *
   * @throws Exception the test exception
   */
  @Test
  public void getRawResponseTest() throws Exception {
    String customMessage = faker.lorem().sentence();
    Exception exception = new IllegalClassFormatException();
    ApiException apiException = new ApiException(customMessage, exception);
    assertEquals(customMessage, apiException.getRawResponse());
  }

  /**
   * Tests {@link ApiException#getRawResponseAsJsonObject()}.
   *
   * @throws Exception the test exception
   */
  @Test
  public void getRawResponseAsJsonObjectTest() throws Exception {
    String key = "data";
    String value = faker.lorem().sentence();
    String body = "{'" + key + "': '" + value + "'}";
    Exception exception = new NullPointerException();
    ApiException apiException = new ApiException(body, exception);
    JsonObject obj = apiException.getRawResponseAsJsonObject();
    assertEquals(value, obj.get(key).getAsString());
  }

  /**
   * Tests failure case of {@link ApiException#getRawResponseAsJsonObject()}.
   *
   * @throws Exception the test exception
   */
  @Test
  public void getRawResponseAsJsonObjectFailureTest() throws Exception {
    String key = "data";
    String value = faker.lorem().sentence();
    String body = "{'" + key + "': '" + value + "'"; // missing trailing curly bracket
    Exception exception = new NullPointerException();
    ApiException apiException = new ApiException(body, exception);
    JsonObject obj = apiException.getRawResponseAsJsonObject();
    assertNull(obj);
  }

  /**
   * Tests {@link ApiException#getException()}.
   *
   * @throws Exception the test exception
   */
  @Test
  public void getExceptionTest() throws Exception {
    ApiException apiException = new ApiException();
    assertEquals(apiException, apiException.getException());
  }

  /**
   * Tests {@link MalformedResponseException}.
   *
   * @throws Exception the test exception
   */
  @Test
  public void testMalformedResponseException() throws Exception {
    MalformedResponseException apiException = new MalformedResponseException();
    assertNull(apiException.getMessage());
    Exception exception = new EmptyStackException();
    apiException = new MalformedResponseException(exception);
    assertEquals(exception, apiException.getCause());
    String customMessage = faker.lorem().sentence();
    apiException = new MalformedResponseException(customMessage);
    assertEquals(customMessage, apiException.getMessage());
    customMessage = faker.lorem().sentence();
    exception = new IOException();
    apiException = new MalformedResponseException(customMessage, exception);
    assertEquals(customMessage, apiException.getMessage());
    assertEquals(exception, apiException.getCause());
  }

  /**
   * Tests {@link FailedRequestException}.
   *
   * @throws Exception the test exception
   */
  @Test
  public void testFailedRequestException() throws Exception {
    FailedRequestException apiException = new FailedRequestException();
    assertNull(apiException.getMessage());
    Exception exception = new EmptyStackException();
    apiException = new FailedRequestException(exception);
    assertEquals(exception, apiException.getCause());
    String customMessage = faker.lorem().sentence();
    apiException = new FailedRequestException(customMessage);
    assertEquals(customMessage, apiException.getMessage());
    customMessage = faker.lorem().sentence();
    exception = new IOException();
    apiException = new FailedRequestException(customMessage, exception);
    assertEquals(customMessage, apiException.getMessage());
    assertEquals(exception, apiException.getCause());
  }

  /**
   * Tests {@link FailedAccessTokenException}.
   *
   * @throws Exception the test exception
   */
  @Test
  public void testFailedAccessTokenException() throws Exception {
    FailedAccessTokenException apiException = new FailedAccessTokenException();
    assertNull(apiException.getMessage());
    Exception exception = new EmptyStackException();
    apiException = new FailedAccessTokenException(exception);
    assertEquals(exception, apiException.getCause());
    String customMessage = faker.lorem().sentence();
    apiException = new FailedAccessTokenException(customMessage);
    assertEquals(customMessage, apiException.getMessage());
    customMessage = faker.lorem().sentence();
    exception = new IOException();
    apiException = new FailedAccessTokenException(customMessage, exception);
    assertEquals(customMessage, apiException.getMessage());
    assertEquals(exception, apiException.getCause());
  }

  /**
   * Tests {@link NotImplementedException}.
   *
   * @throws Exception the test exception
   */
  @Test
  public void testNotImplementedException() throws Exception {
    NotImplementedException apiException = new NotImplementedException();
    assertNull(apiException.getMessage());
    Exception exception = new EmptyStackException();
    apiException = new NotImplementedException(exception);
    assertEquals(exception, apiException.getCause());
    String customMessage = faker.lorem().sentence();
    apiException = new NotImplementedException(customMessage);
    assertEquals(customMessage, apiException.getMessage());
    customMessage = faker.lorem().sentence();
    exception = new IOException();
    apiException = new NotImplementedException(customMessage, exception);
    assertEquals(customMessage, apiException.getMessage());
    assertEquals(exception, apiException.getCause());
  }
}