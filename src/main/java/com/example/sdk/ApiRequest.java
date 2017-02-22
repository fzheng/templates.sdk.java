package com.example.sdk;

import com.google.gson.Gson;
import com.example.sdk.ApiEndPoints.ApiEndPoint;
import com.example.sdk.ApiException.FailedAccessTokenException;
import com.example.sdk.ApiException.FailedRequestException;
import com.example.sdk.types.HttpMethod;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The class to handle all API requests from entities.
 *
 * @author Feng Zheng
 * @version 1.0
 * @since 1.0
 */
public abstract class ApiRequest {
  /**
   * The instance of {@link ApiContext} used to make API request.
   */
  private ApiContext context;

  private static final RequestExecutor executor = new RequestExecutor();
  private final ApiEndPoint apiEndPoint;
  private Map<String, Object> params = new HashMap<>();

  /**
   * Constructs a new API request with an instance of {@link ApiContext},
   * an instance of {@link ApiEndPoint} and a list of uri parameters.
   *
   * @param context     the instance of {@link ApiContext}
   * @param apiEndPoint the instance of {@link ApiEndPoint}
   * @param uriParams   the uri params
   */
  public ApiRequest(ApiContext context, ApiEndPoint apiEndPoint, String... uriParams) {
    this.context = context;
    this.apiEndPoint = apiEndPoint.resolveUriParams(uriParams);
  }

  /**
   * Gets current API context.
   *
   * @return the instance of {@link ApiContext}
   */
  public ApiContext getContext() {
    return this.context;
  }

  /**
   * Gets the last response.
   *
   * @return the last response in {@link ApiResponse}
   */
  public abstract ApiResponse getLastResponse();

  /**
   * Execute the API request without additional parameters and returns {@link ApiResponse}.
   *
   * @return the instance of {@link ApiResponse}
   * @throws ApiException         the api exception
   * @throws InterruptedException the interrupted exception
   */
  public abstract ApiResponse execute() throws ApiException, InterruptedException;

  /**
   * Execute the API request with additional parameters and returns {@link ApiResponse}.
   *
   * @param extraParams the extra params
   * @return the instance of {@link ApiResponse}
   * @throws ApiException         the api exception
   * @throws InterruptedException the interrupted exception
   */
  public abstract ApiResponse execute(Map<String, Object> extraParams)
      throws ApiException, InterruptedException;

  /**
   * Sets parameters for the API request.
   *
   * @param param the parameter name
   * @param value the parameter value
   */
  protected void setParam(String param, Object value) {
    setParamInternal(param, value);
  }

  /**
   * Sets parameters for the API request.
   *
   * @param params the parameters in map format
   */
  protected void setParams(Map<String, Object> params) {
    setParamsInternal(params);
  }

  /**
   * Internal method to execute API request.
   *
   * @param extraParams extra API request parameters
   * @return the API response in string
   * @throws ApiException         the api exception
   * @throws InterruptedException the interrupted exception
   */
  protected String executeInternal(Map<String, Object> extraParams)
      throws ApiException, InterruptedException {
    try {
      Map<String, Object> allParams = new HashMap<>(params);
      if (extraParams != null) {
        allParams.putAll(extraParams);
      }
      return executor.execute(apiEndPoint, allParams, context);
    } catch (IOException e) {
      throw new FailedRequestException(e);
    }
  }

  private void setParamInternal(String param, Object value) {
    params.put(param, value);
  }

  private void setParamsInternal(Map<String, Object> params) {
    this.params = params;
  }

  private static class RequestExecutor {
    private String execute(ApiEndPoint apiEndPoint,
                           Map<String, Object> allParams,
                           ApiContext context)
        throws ApiException, IOException, InterruptedException {
      int retry = 0;
      HttpMethod method = apiEndPoint.getMethod();
      String apiUrl = apiEndPoint.getApiUrl(context);
      do {
        if (retry > 0) {
          Thread.sleep(ApiConfig.RETRY_TOKEN_DELAY_MS);
        }
        try {
          switch (method) {
            case GET:
              return get(apiUrl, allParams, context);
            case POST:
              return post(apiUrl, allParams, context);
            case PUT:
              return put(apiUrl, allParams, context);
            case DELETE:
              return delete(apiUrl, allParams, context);
            default:
              throw new IllegalArgumentException("Unsupported http request method");
          }
        } catch (FailedAccessTokenException tokenError) {
          retry++;
          App app = new App(context);
          app.createToken().execute();
        }
      } while (retry <= ApiConfig.RETRY_TOKEN_MAX);
      throw new FailedRequestException("Retry timeout exceeded");
    }

    private String get(String apiUrl,
                       Map<String, Object> allParams,
                       ApiContext context) throws ApiException, IOException {
      StringBuilder urlString = new StringBuilder(apiUrl);
      boolean firstEntry = true;
      for (Map.Entry entry : allParams.entrySet()) {
        urlString.append(firstEntry ? "?" : "&")
            .append(URLEncoder.encode(entry.getKey().toString(), "UTF-8"))
            .append("=")
            .append(URLEncoder.encode(convertToString(entry.getValue()), "UTF-8"));
        firstEntry = false;
      }
      URL url = new URL(urlString.toString());
      HttpURLConnection connection = (HttpURLConnection) url.openConnection();
      connection.setRequestMethod(HttpMethod.GET.toString());
      connection.setRequestProperty("Content-Type", "application/json");
      if (context.hasAppToken()) {
        connection.setRequestProperty("Authorization", context.getAppToken());
      }
      return readResponse(connection, context);
    }

    private String post(String apiUrl,
                        Map<String, Object> allParams,
                        ApiContext context) throws ApiException, IOException {
      return sendRequest(HttpMethod.POST, apiUrl, allParams, context);
    }

    private String put(String apiUrl,
                       Map<String, Object> allParams,
                       ApiContext context) throws ApiException, IOException {
      return sendRequest(HttpMethod.PUT, apiUrl, allParams, context);
    }

    private String delete(String apiUrl,
                          Map<String, Object> allParams,
                          ApiContext context) throws ApiException, IOException {
      return sendRequest(HttpMethod.DELETE, apiUrl, allParams, context);
    }

    private String sendRequest(HttpMethod method,
                               String apiUrl,
                               Map<String, Object> allParams,
                               ApiContext context) throws ApiException, IOException {
      URL url = new URL(apiUrl);
      HttpURLConnection connection = (HttpURLConnection) url.openConnection();
      connection.setRequestMethod(method.toString());
      connection.setRequestProperty("Content-Type", "application/json");
      if (context.hasAppToken()) {
        connection.setRequestProperty("Authorization", context.getAppToken());
      }
      connection.setDoOutput(true);
      DataOutputStream stream = new DataOutputStream(connection.getOutputStream());
      stream.write(convertToString(allParams).getBytes("UTF-8"));
      stream.flush();
      stream.close();
      return readResponse(connection, context);
    }
  }

  private static String readResponse(HttpURLConnection connection, ApiContext context)
      throws ApiException, IOException {
    try {
      int responseCode = connection.getResponseCode();
      if (responseCode == HttpURLConnection.HTTP_UNAUTHORIZED && context.hasAppToken()) {
        context.clearAppToken();
        throw new FailedAccessTokenException();
      }
      InputStreamReader reader = new InputStreamReader(connection.getInputStream(), "UTF-8");
      BufferedReader in = new BufferedReader(reader);
      String inputLine;
      StringBuilder response = new StringBuilder();
      while ((inputLine = in.readLine()) != null) {
        response.append(inputLine);
      }
      in.close();
      return response.toString();
    } catch (FailedAccessTokenException tokenError) {
      // Error 401
      throw tokenError;
    } catch (Exception e) {
      InputStream inputStream = connection.getErrorStream();
      if (inputStream == null) {
        throw new FailedRequestException(e);
      }
      InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
      BufferedReader in = new BufferedReader(inputStreamReader);
      String inputLine;
      StringBuilder response = new StringBuilder();
      while ((inputLine = in.readLine()) != null) {
        response.append(inputLine);
      }
      in.close();
      throw new FailedRequestException(response.toString(), e);
    }
  }

  private static String convertToString(Object input) {
    if (input instanceof Map || input instanceof List) {
      Gson gson = new Gson();
      return gson.toJson(input);
    } else {
      return String.valueOf(input);
    }
  }
}
