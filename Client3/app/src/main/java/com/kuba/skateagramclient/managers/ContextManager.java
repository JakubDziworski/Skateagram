package com.kuba.skateagramclient.managers;

import android.content.Context;

import com.google.inject.Singleton;
import com.kuba.skateagramclient.Application;

/**
 * Created by kuba on 29.11.2015.
 */
@Singleton
public class ContextManager {
    private Context context;

    public Context getContext() {
        if(context == null) context = Application.getContext();
        return context;
    }
}
