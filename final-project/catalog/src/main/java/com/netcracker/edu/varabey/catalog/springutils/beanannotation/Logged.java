package com.netcracker.edu.varabey.catalog.springutils.beanannotation;

import org.springframework.boot.logging.LogLevel;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Logged {
    String messageBefore() default "";

    String messageAfter() default "";

    LogLevel level() default LogLevel.INFO;

    boolean startFromNewLine() default false;
}
