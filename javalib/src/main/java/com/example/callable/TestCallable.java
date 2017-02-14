package com.example.callable;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * Created by zmy on 2017/2/10.
 */

public class TestCallable {
    public static void main(String[] args) {
        CallableIml iml = new CallableIml();
        FutureTask<Integer> ft = new FutureTask<Integer>(iml);
        for(int i = 0;i < 10;i++)
        {
            System.out.println(Thread.currentThread().getName()+" 的循环变量i的值"+i);
            if(i==5)
            {
                new Thread(ft,"有返回值的线程").start();
            }
        }
        try
        {
            System.out.println("子线程的返回值："+ft.get());
        } catch (InterruptedException e)
        {
            e.printStackTrace();
        } catch (ExecutionException e)
        {
            e.printStackTrace();
        }
    }
}
