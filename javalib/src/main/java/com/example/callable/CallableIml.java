package com.example.callable;

import java.util.concurrent.Callable;

/**
 * Created by zmy on 2017/2/10.
 */

public class CallableIml implements Callable<Integer> {
    @Override
    public Integer call() throws Exception {
        int i = 0;
        for(;i<10;i++)
        {
            System.out.println(Thread.currentThread().getName()+" "+i);
        }
        return i;
    }
}
