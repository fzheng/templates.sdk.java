package com.example.sdk;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * The customized annotation type for warning.
 *
 * @author Feng Zheng
 */
@Retention(RetentionPolicy.SOURCE)
@Target({
    ElementType.ANNOTATION_TYPE,
    ElementType.CONSTRUCTOR,
    ElementType.FIELD,
    ElementType.LOCAL_VARIABLE,
    ElementType.METHOD,
    ElementType.PARAMETER,
    ElementType.TYPE
})
@interface Warning {
  /**
   * Warning message.
   *
   * @return the warning message
   */
  String value();
}
