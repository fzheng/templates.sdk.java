package com.example.sdk;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * The list containing the elements of {@link ApiNode}.
 * The class implements API request operations and handles pagination of {@link ApiNode} collection.
 *
 * @param <T> the type of {@link ApiNode} or its subclass
 * @author Feng Zheng
 * @version 1.0
 * @since 1.0
 */
public class ApiNodeList<T extends ApiNode> extends ArrayList<T> implements ApiResponse {
  /**
   * Serial version UID.
   */
  private static final long serialVersionUID = 1L;
  /**
   * Raw response value in string.
   */
  private final String rawValue;
  /**
   * The pagination info of the response.
   */
  private transient Pagination pagination;
  /**
   * The API request sent to public API.
   */
  private final transient ApiRequest request;

  /**
   * Constructs a new {@link ApiNodeList} with a {@link ApiRequest} and raw response JSON string.
   *
   * @param request  the request
   * @param rawValue the raw value
   */
  public ApiNodeList(ApiRequest request, String rawValue) {
    this.request = request;
    this.rawValue = rawValue;
  }

  /**
   * Next page of the {@link ApiNodeList}, and each page has {@code itemsPerPage} elements.
   *
   * @param itemsPerPage the items per page
   * @return the list of {@link ApiNode}
   * @throws ApiException         the api exception
   * @throws InterruptedException the interrupted exception
   */
  public ApiNodeList<T> nextPage(int itemsPerPage) throws ApiException, InterruptedException {
    Map<String, Object> extraParams = new HashMap<>();
    extraParams.put("limit", itemsPerPage);
    extraParams.put("offset", getOffset() + this.size());
    @SuppressWarnings("unchecked")
    ApiNodeList<T> response = (ApiNodeList<T>) request.execute(extraParams);
    return response;
  }

  /**
   * Next page of the {@link ApiNodeList}, and each page has
   * {@link ApiConfig#DEFAULT_ITEMS_PER_PAGE} elements.
   *
   * @return the list of {@link ApiNode}
   * @throws ApiException         the api exception
   * @throws InterruptedException the interrupted exception
   */
  public ApiNodeList<T> nextPage() throws ApiException, InterruptedException {
    return nextPage(ApiConfig.DEFAULT_ITEMS_PER_PAGE);
  }

  /**
   * Sets pagination.
   *
   * @param pagination the instance of {@link Pagination}
   */
  public void setPagination(Pagination pagination) {
    this.pagination = pagination;
  }

  /**
   * Gets offset.
   *
   * @return the pagination offset
   */
  public int getOffset() {
    return (pagination != null) ? pagination.getOffset() : 0;
  }

  /**
   * Whether there are any more elements after the current page.
   *
   * @return the boolean
   */
  public boolean hasNextPage() {
    return pagination != null && pagination.hasNextPage();
  }

  @Override
  public String getRawResponse() {
    return rawValue;
  }

  @Override
  public JsonObject getRawResponseAsJsonObject() {
    JsonParser parser = new JsonParser();
    return parser.parse(rawValue).getAsJsonObject();
  }

  /**
   * Gets the first element of the {@link ApiNodeList}.
   *
   * @return the type of {@link ApiNode} or its subclass
   */
  @Override
  public T head() {
    return this.size() > 0 ? this.get(0) : null;
  }

  /**
   * Gets the last element of the {@link ApiNodeList}.
   *
   * @return the type of {@link ApiNode} or its subclass
   */
  public T tail() {
    return this.size() > 0 ? this.get(this.size() - 1) : null;
  }
}
