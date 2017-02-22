package com.example.sdk;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

/**
 * The class to handle various API exceptions and
 * to deal with returning appropriate error responses.
 *
 * @author Feng Zheng
 * @version 1.0
 * @since 1.0
 */
public class ApiException extends Exception implements ApiResponse {

  /**
   * Instantiates a new API exception by default.
   */
  public ApiException() {
    super();
  }

  /**
   * Instantiates a new API exception with the object of {@link Throwable}.
   *
   * @param e the object of {@link Throwable}
   */
  public ApiException(Throwable e) {
    super(e);
  }

  /**
   * Instantiates a new API exception with the error message.
   *
   * @param message the error message
   */
  public ApiException(String message) {
    super(message);
  }

  /**
   * Instantiates a new API exception with both the object of
   * {@link Throwable} and the error message.
   *
   * @param message the error message
   * @param e       the object of {@link Throwable}
   */
  public ApiException(String message, Throwable e) {
    super(message, e);
  }

  @Override
  public ApiNode head() {
    return null;
  }

  @Override
  public String getRawResponse() {
    return this.getMessage();
  }

  @Override
  public JsonObject getRawResponseAsJsonObject() {
    JsonParser parser = new JsonParser();
    try {
      return parser.parse(this.getMessage()).getAsJsonObject();
    } catch (Exception e) {
      return null;
    }
  }

  /**
   * Gets the API exception.
   *
   * @return the object of the API exception
   */
  public ApiException getException() {
    return this;
  }

  /**
   * The class to handle malformed response exception.
   */
  public static class MalformedResponseException extends ApiException {
    /**
     * Instantiates a new malformed response exception by default.
     */
    public MalformedResponseException() {
      super();
    }

    /**
     * Instantiates a new malformed response exception with the object of {@link Throwable}.
     *
     * @param e the object of {@link Throwable}
     */
    public MalformedResponseException(Throwable e) {
      super(e);
    }

    /**
     * Instantiates a new malformed response exception with the error message.
     *
     * @param message the error message
     */
    public MalformedResponseException(String message) {
      super(message);
    }

    /**
     * Instantiates a new malformed response exception with
     * both the object of {@link Throwable} and the error message.
     *
     * @param message the error message
     * @param e       the object of {@link Throwable}
     */
    public MalformedResponseException(String message, Throwable e) {
      super(message, e);
    }
  }

  /**
   * The class to handle failed request exception.
   */
  public static class FailedRequestException extends ApiException {
    /**
     * Instantiates a new failed request exception by default.
     */
    public FailedRequestException() {
      super();
    }

    /**
     * Instantiates a new failed request exception with the object of {@link Throwable}.
     *
     * @param e the object of {@link Throwable}
     */
    public FailedRequestException(Throwable e) {
      super(e);
    }

    /**
     * Instantiates a new failed request exception with the error message.
     *
     * @param message the error message
     */
    public FailedRequestException(String message) {
      super(message);
    }

    /**
     * Instantiates a new failed request exception with
     * both the object of {@link Throwable} and the error message.
     *
     * @param message the error message
     * @param e       the object of {@link Throwable}
     */
    public FailedRequestException(String message, Throwable e) {
      super(message, e);
    }
  }

  /**
   * The class to handle failed to access token exception.
   */
  public static class FailedAccessTokenException extends ApiException {
    /**
     * Instantiates a new failed to access token exception by default.
     */
    public FailedAccessTokenException() {
      super();
    }

    /**
     * Instantiates a new failed to access token exception with the object of {@link Throwable}.
     *
     * @param e the object of {@link Throwable}
     */
    public FailedAccessTokenException(Throwable e) {
      super(e);
    }

    /**
     * Instantiates a new failed to access token exception with the error message.
     *
     * @param message the error message
     */
    public FailedAccessTokenException(String message) {
      super(message);
    }

    /**
     * Instantiates a new failed to access token exception
     * with both the object of {@link Throwable} and the error message.
     *
     * @param message the error message
     * @param e       the object of {@link Throwable}
     */
    public FailedAccessTokenException(String message, Throwable e) {
      super(message, e);
    }
  }

  /**
   * The class to handle not implemented exception.
   */
  public static class NotImplementedException extends ApiException {
    /**
     * Instantiates a new not implemented exception by default.
     */
    public NotImplementedException() {
      super();
    }

    /**
     * Instantiates a new not implemented exception with the object of {@link Throwable}.
     *
     * @param e the object of {@link Throwable}
     */
    public NotImplementedException(Throwable e) {
      super(e);
    }

    /**
     * Instantiates a new not implemented exception with the error message.
     *
     * @param message the error message
     */
    public NotImplementedException(String message) {
      super(message);
    }

    /**
     * Instantiates a new not implemented exception with
     * both the object of {@link Throwable} and the error message.
     *
     * @param message the error message
     * @param e       the object of {@link Throwable}
     */
    public NotImplementedException(String message, Throwable e) {
      super(message, e);
    }
  }
}
