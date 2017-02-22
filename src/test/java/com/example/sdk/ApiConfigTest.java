package com.example.sdk;

import org.junit.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;

import static org.junit.Assert.assertTrue;

/**
 * The test suite for {@link ApiConfig}.
 *
 * @author Feng Zheng
 * @version 1.0
 * @since 1.0
 */
public class ApiConfigTest {
  /**
   * Tests {@link ApiConfig#ApiConfig()} is private.
   *
   * @throws NoSuchMethodException     the no-such-method exception
   * @throws IllegalAccessException    the illegal access exception
   * @throws InvocationTargetException the invocation target exception
   * @throws InstantiationException    the instantiation exception
   */
  @Test
  public void testConstructorIsPrivate() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
    Constructor<ApiConfig> constructor = ApiConfig.class.getDeclaredConstructor();
    assertTrue(Modifier.isPrivate(constructor.getModifiers()));
    constructor.setAccessible(true);
    constructor.newInstance();
  }

  /**
   * Tests fields of {@link ApiConfig}
   *
   * @throws NoSuchFieldException the no-such-field exception
   */
  @Test
  public void testFields() throws NoSuchFieldException {
    Field field = ApiConfig.class.getDeclaredField("DEFAULT_API_VERSION");
    assertTrue(Modifier.isFinal(field.getModifiers()));
    field = ApiConfig.class.getDeclaredField("DEFAULT_API_BASE");
    assertTrue(Modifier.isFinal(field.getModifiers()));
  }
}