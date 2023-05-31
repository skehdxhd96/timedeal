package com.example.timedeal.common.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.concurrent.TimeUnit;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface DistributedLock {
    String lockName();

    long waitTime();

    long leaseTime();

    TimeUnit unit() default TimeUnit.MILLISECONDS;
}
