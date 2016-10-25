package com.zmy.gradledemo.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by zmy on 2016/10/24.
 */

@Retention(RetentionPolicy.CLASS) @Target(ElementType.FIELD)
public @interface ZmyBind {
    int[] value();
}
