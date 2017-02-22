package com.example.sdk;

import com.example.sdk.types.HttpMethod;

/**
 * The collection of API endpoints.
 *
 * @author Feng Zheng
 * @version 1.0
 * @since 1.0
 */
public final class ApiEndPoints {
  /**
   * Private constructor to prevent new instance.
   */
  private ApiEndPoints() {
  }

  /**
   * The API endpoint class.
   */
  static final class ApiEndPoint {
    /**
     * Public API endpoint URI.
     */
    private final String uri;
    /**
     * Public API endpoint HTTP method.
     */
    private final HttpMethod method;

    /**
     * Instantiates a new API endpoint.
     *
     * @param uri    endpoint URI
     * @param method HTTP method
     */
    private ApiEndPoint(String uri, HttpMethod method) {
      this.uri = uri;
      this.method = method;
    }

    /**
     * Resolve the URI params API endpoint.
     *
     * @param urlParams the URL params
     * @return the API end point
     */
    ApiEndPoint resolveUriParams(String... urlParams) {
      String resolvedUri = String.format(uri, (Object[]) urlParams);
      return new ApiEndPoint(resolvedUri, method);
    }

    /**
     * Gets the endpoint URI.
     *
     * @return the URI
     */
    String getUri() {
      return uri;
    }

    /**
     * Resolves and gets the API URL.
     *
     * @param context {@link ApiContext}
     * @return the resolved API URL
     */
    String getApiUrl(ApiContext context) {
      return context.getApiBase() + "/" + context.getVersion() + "/" + uri;
    }

    /**
     * Gets the HTTP method.
     *
     * @return the HTTP method
     */
    HttpMethod getMethod() {
      return method;
    }
  }

  /**
   * The endpoint to regenerate the app access token.
   */
  static final ApiEndPoint CREATE_ACCESS_TOKEN =
      new ApiEndPoint("apps/%1s/token", HttpMethod.POST);

  /**
   * The endpoint to list the user's entities.
   */
  static final ApiEndPoint GET_ENTITIES =
      new ApiEndPoint("entities", HttpMethod.GET);

  /**
   * The endpoint to create a new entity.
   */
  static final ApiEndPoint CREATE_ENTITY =
      new ApiEndPoint("entities", HttpMethod.POST);

  /**
   * The endpoint to get the details of the entity by id.
   */
  static final ApiEndPoint GET_ENTITY_BY_ID =
      new ApiEndPoint("entities/%1s", HttpMethod.GET);

  /**
   * The endpoint to update the entity by id.
   */
  static final ApiEndPoint UPDATE_ENTITY =
      new ApiEndPoint("entities/%1s", HttpMethod.PUT);

  /**
   * The endpoint to delete the entity from user's account.
   */
  static final ApiEndPoint DELETE_USER_FROM_PROJECT =
      new ApiEndPoint("entities/%1s", HttpMethod.DELETE);
}
