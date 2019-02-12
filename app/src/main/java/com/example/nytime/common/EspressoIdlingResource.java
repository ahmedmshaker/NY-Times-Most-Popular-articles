package com.example.nytime.common;

import android.support.test.espresso.IdlingResource;

public class EspressoIdlingResource {
    static SimpleCountingIdlingResource countingIdlingResource = new SimpleCountingIdlingResource();
    private static String RESOURCE = "GLOBAL";

    public static void increment() {
        if (countingIdlingResource != null)
            countingIdlingResource.increment();
    }

    public static void decrement() {
        if (countingIdlingResource != null)
            countingIdlingResource.decrement();
    }

    public static IdlingResource getIdlingResource() {
        return countingIdlingResource;
    }


}




