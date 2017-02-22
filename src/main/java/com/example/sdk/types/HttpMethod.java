package com.example.sdk.types;

/**
 * Enum type of HTTP request methods to indicate
 * the desired action to be performed for a given resource
 *
 * @author Feng Zheng
 * @version 1.0
 * @since 1.0
 */
@SuppressWarnings("unused")
public enum HttpMethod {
  /**
   * The GET method requests a representation
   * of the specified resource.
   */
  GET,
  /**
   * The PUT method replaces all current representations
   * of the target resource with the request payload.
   */
  PUT,
  /**
   * The POST method is used to submit an entity to
   * the specified resource, often causing a change in state
   * or side effects on the server.
   */
  POST,
  /**
   * The DELETE method deletes the specified resource.
   */
  DELETE,
  /**
   * The OPTIONS method is used to describe
   * the communication options for the target resource.
   */
  OPTIONS
}
