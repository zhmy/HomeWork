package com.example;

/**
 * Created by zmy on 2017/2/10.
 */

public class DisplayMessage implements Runnable {
    private String message;

    public DisplayMessage(String message) {
        this.message = message;
    }

    public void run() {
//        while(true) {
            System.out.println(message);
//        }
    }
}
