package com.example.sdk;

import com.google.gson.annotations.SerializedName;

import java.util.Map;

/**
 * Class defining an App.
 *
 * @author Feng Zheng
 * @version 1.0
 * @since 1.0
 */
public class App extends ApiNode {
  @SerializedName(value = "id", alternate = "appId")
  private String id = null;

  @SerializedName("token")
  private String token = null;

  /**
   * Constructs a new App with an instance of {@link ApiContext}.
   *
   * @param context the instance of {@link ApiContext}
   */
  public App(ApiContext context) {
    this.id = context.getAppId();
    this.token = context.getAppToken();
    this.setContext(context);
  }

  /**
   * Creates an instance of {@link RequestCreateToken} to generate a new app access token.
   *
   * @return the instance of {@link RequestCreateToken}
   */
  public RequestCreateToken createToken() {
    return new RequestCreateToken(this);
  }

  public String getId() {
    return id;
  }

  /**
   * Sets the app id.
   *
   * @param id the app id
   */
  public void setId(String id) {
    this.id = id;
  }

  /**
   * Gets the app access token.
   *
   * @return the app access token
   */
  public String getToken() {
    return token;
  }

  /**
   * Class defining a public API request to generate a new app access token.
   */
  public static class RequestCreateToken extends ApiRequest {
    /**
     * The last API response.
     */
    App lastResponse = null;

    /**
     * Constructs a new {@link ApiRequest} to generate a new app access token.
     *
     * @param app the instance of {@link App}
     */
    RequestCreateToken(App app) {
      super(app.getContext(), ApiEndPoints.CREATE_ACCESS_TOKEN, app.id);
      setParam("appSecret", app.getContext().getAppSecret());
    }

    @Override
    public App getLastResponse() {
      return lastResponse;
    }

    @Override
    public App execute() throws ApiException, InterruptedException {
      return execute(null);
    }

    @Override
    public App execute(Map<String, Object> extraParams) throws ApiException, InterruptedException {
      lastResponse = App.parseResponse(App.class,
          executeInternal(extraParams), getContext(), this).head();
      return lastResponse;
    }
  }
}
