package com.example.sdk;

import com.google.gson.JsonObject;

/**
 * The interface of the API response.
 *
 * @author Feng Zheng
 * @version 1.0
 * @since 1.0
 */
interface ApiResponse {
  /**
   * Gets the raw response of the API response.
   *
   * @return the raw response
   */
  String getRawResponse();

  /**
   * Gets the raw response as a JSON object.
   *
   * @return the JSON object
   */
  JsonObject getRawResponseAsJsonObject();

  /**
   * Gets the head of the {@link ApiNode}.
   *
   * @return the head of the {@link ApiNode}
   */
  ApiNode head();
}
