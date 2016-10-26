package com.zmy.gradledemo.annotation;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * Created by zmy on 2016/10/24.
 */

public class AnnotationBaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //注解解析
        for (Class c = this.getClass(); c != Context.class; c = c.getSuperclass()) {
            ContentView annotation = (ContentView) c.getAnnotation(ContentView.class);
            if (annotation != null) {
                try {
                    this.setContentView(annotation.value());
                } catch (RuntimeException e) {
                    e.printStackTrace();
                }
                return;
            }
        }

        Class c = this.getClass();
//        Proxy;
        Method[] methods = c.getMethods();
        for(Method method : methods) {
            method.getAnnotation(ContentView.class);
        }


    }
}
