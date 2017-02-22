package com.example.sdk;

import com.google.gson.annotations.SerializedName;

/**
 * Class defining the pagination used by public API.
 *
 * @author Feng Zheng
 * @version 1.0
 * @since 1.0
 */
public class Pagination extends ApiNode {
  @SerializedName("limit")
  private int limit;

  @SerializedName("offset")
  private int offset;

  @SerializedName("total")
  private int total;

  /**
   * Constructs a new Pagination by default.
   */
  Pagination() {
    this(0, 0, 0);
  }

  /**
   * Constructs a new Pagination with the items per page, the offset and the total items.
   *
   * @param limit  the items per page
   * @param offset the offset
   * @param total  the total items
   */
  Pagination(int limit, int offset, int total) {
    this.limit = limit;
    this.offset = offset;
    this.total = total;
  }

  public String getId() {
    return null;
  }

  /**
   * Gets the number of items per page.
   *
   * @return the number of items per page
   */
  public int getLimit() {
    return limit;
  }

  /**
   * Gets the offset.
   *
   * @return the offset
   */
  public int getOffset() {
    return offset;
  }

  /**
   * Gets the total number of items.
   *
   * @return the total number of items
   */
  public int getTotal() {
    return total;
  }

  /**
   * Whether there are more items.
   *
   * @return whether there are more items
   */
  public boolean hasNextPage() {
    return this.total != 0 && this.limit != 0 && (this.total > this.offset + this.limit);
  }
}
