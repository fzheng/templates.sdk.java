package com.example.sdk;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

/**
 * The test suite for {@link Pagination}.
 *
 * @author <a href="mailto:feng.zheng@synopsys.com">Feng Zheng</a>
 * @version 1.0
 * @since 1.0
 */
public class PaginationTest extends BaseTest {
  /**
   * Tests {@link Pagination#getId()}.
   *
   * @throws Exception the test exception
   */
  @Test
  public void getIdTest() throws Exception {
    Pagination pagination = new Pagination();
    assertNull(pagination.getId());
  }

  /**
   * Tests {@link Pagination#getLimit()}.
   *
   * @throws Exception the test exception
   */
  @Test
  public void getLimitTest() throws Exception {
    Pagination pagination = new Pagination();
    assertEquals(0, pagination.getLimit());
    int limit = faker.number().numberBetween(0, 100);
    int offset = faker.number().numberBetween(0, 100);
    int total = faker.number().numberBetween(0, 100);
    pagination = new Pagination(limit, offset, total);
    assertEquals(limit, pagination.getLimit());
  }

  /**
   * Tests {@link Pagination#getOffset()}.
   *
   * @throws Exception the test exception
   */
  @Test
  public void getOffsetTest() throws Exception {
    Pagination pagination = new Pagination();
    assertEquals(0, pagination.getOffset());
    int limit = faker.number().numberBetween(0, 100);
    int offset = faker.number().numberBetween(0, 100);
    int total = faker.number().numberBetween(0, 100);
    pagination = new Pagination(limit, offset, total);
    assertEquals(offset, pagination.getOffset());
  }

  /**
   * Tests {@link Pagination#getTotal()}.
   *
   * @throws Exception the test exception
   */
  @Test
  public void getTotalTest() throws Exception {
    Pagination pagination = new Pagination();
    assertEquals(0, pagination.getTotal());
    int limit = faker.number().numberBetween(0, 100);
    int offset = faker.number().numberBetween(0, 100);
    int total = faker.number().numberBetween(0, 100);
    pagination = new Pagination(limit, offset, total);
    assertEquals(total, pagination.getTotal());
  }
}