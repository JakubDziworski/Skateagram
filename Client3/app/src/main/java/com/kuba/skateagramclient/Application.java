package com.kuba.skateagramclient;

import android.content.Context;

/**
 * Created by kuba on 28.11.2015.
 */
public class Application extends android.app.Application{
    private static Context context;

    @Override
    public void onCreate() {
        context = getApplicationContext();
    }

    public static Context getContext() {
        return context;
    }
}
