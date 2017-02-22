package com.example.sdk;


/**
 * Constants and configurations defined in public APIs.
 *
 * @author Feng Zheng
 * @version 1.0
 * @since 1.0
 */
@SuppressWarnings("unused")
public final class ApiConfig {
  /**
   * Private constructor to prevent new instance.
   */
  private ApiConfig() {
  }

  /**
   * Default API version.
   */
  public static final String DEFAULT_API_VERSION = "v1";
  /**
   * Default API base URL.
   */
  public static final String DEFAULT_API_BASE = "http://www.example.com";
  /**
   * Maximum attempts to refresh the access token.
   */
  public static final int RETRY_TOKEN_MAX = 1;
  /**
   * Time delay between the attempts to refresh
   * the access token (in milliseconds).
   */
  public static final int RETRY_TOKEN_DELAY_MS = 1000;
  /**
   * Default number of items per page in response.
   */
  public static final int DEFAULT_ITEMS_PER_PAGE = 20;
  /**
   * Maximum attempts to ping scan status.
   */
  public static final int RETRY_PING_SCAN_MAX = 36;
  /**
   * Time delay between the scan status pings (in milliseconds).
   */
  public static final int RETRY_PING_SCAN_INTERVAL_MS = 5000;
  /**
   * The primary key of response data.
   */
  public static final String PRIMARY_DATA_KEY = "data";
  /**
   * The primary key of response meta data.
   */
  public static final String PRIMARY_META_KEY = "meta";
  /**
   * The key of pagination information.
   */
  public static final String PAGINATION_KEY = "pagination";
  /**
   * The format of date time.
   */
  public static final String DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";
}
