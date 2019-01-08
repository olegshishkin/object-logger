package com.fleemer.util.log.aop;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * The annotation applies to the parameters of methods whose values should be output to the log.
 */
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
public @interface Log {
    LogFormat[] value() default LogFormat.XML;
}
