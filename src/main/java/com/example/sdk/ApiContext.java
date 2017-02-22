package com.example.sdk;


/**
 * The context class to hold API request base url and version,
 * also to maintain user's app id, app secret and app access token.
 *
 * @author Feng Zheng
 * @version 1.0
 * @since 1.0
 */
public class ApiContext {
  /**
   * The base URL of public API.
   */
  private final String apiBase;
  /**
   * The public API version.
   */
  private final String version;
  /**
   * The app id.
   */
  private final String appId;
  /**
   * The app secret key.
   */
  private final String appSecret;
  /**
   * The app assess token.
   */
  private String appToken;

  /**
   * Instantiates a new API context with specific API base url,
   * specific API version, app id, app secret and app token.
   *
   * @param apiBase   the API base
   * @param version   the API version
   * @param appId     the app id
   * @param appSecret the app secret
   * @param appToken  the app access token
   */
  public ApiContext(String apiBase, String version,
                    String appId, String appSecret, String appToken) {
    this.apiBase = apiBase;
    this.version = version;
    this.appId = appId;
    this.appSecret = appSecret;
    this.appToken = appToken;
  }

  /**
   * Instantiates a new API context with app id, app secret and app token.
   * API base url and API version are {@link ApiConfig#DEFAULT_API_BASE}
   * and {@link ApiConfig#DEFAULT_API_VERSION} respectively.
   *
   * @param appId     the app id
   * @param appSecret the app secret
   * @param appToken  the app access token
   */
  public ApiContext(String appId, String appSecret, String appToken) {
    this(ApiConfig.DEFAULT_API_BASE, ApiConfig.DEFAULT_API_VERSION, appId, appSecret, appToken);
  }

  /**
   * Instantiates a new API context with app id and app secret.
   * API base url and API version are {@link ApiConfig#DEFAULT_API_BASE}
   * and {@link ApiConfig#DEFAULT_API_VERSION} respectively.
   * API access token is set to null
   *
   * @param appId     the app id
   * @param appSecret the app secret
   */
  public ApiContext(String appId, String appSecret) {
    this(ApiConfig.DEFAULT_API_BASE, ApiConfig.DEFAULT_API_VERSION, appId, appSecret, null);
  }

  /**
   * Gets API base URL.
   *
   * @return the API base URL
   */
  public String getApiBase() {
    return apiBase;
  }

  /**
   * Gets the API version.
   *
   * @return the API version
   */
  public String getVersion() {
    return version;
  }

  /**
   * Gets the app id.
   *
   * @return the app id
   */
  public String getAppId() {
    return appId;
  }

  /**
   * Gets the app secret.
   *
   * @return the app secret
   */
  public String getAppSecret() {
    return appSecret;
  }

  /**
   * Gets the app access token.
   *
   * @return the app access token
   */
  public String getAppToken() {
    return appToken;
  }

  /**
   * Sets the app access token of the API Context.
   *
   * @param appToken the new app assess token
   */
  public void setAppToken(String appToken) {
    this.appToken = appToken;
  }

  /**
   * Clear the app access token.
   */
  public void clearAppToken() {
    this.appToken = null;
  }

  /**
   * Returns whether the API context has the app access token.
   *
   * @return whether the API context has the app access token.
   */
  public boolean hasAppToken() {
    return appToken != null;
  }
}
