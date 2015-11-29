package com.kuba.skateagramclient.managers;

import android.content.Context;
import android.widget.Toast;

import com.google.inject.Inject;
import com.google.inject.Singleton;

/**
 * Created by kuba on 29.11.2015.
 */
@Singleton
public class ToastManager {
    @Inject
    Context context;

    @Inject
    public ToastManager() {
    }

    public void showToast(String message) {
        showToast(message,Toast.LENGTH_LONG);
    }

    public void showToast(String message,int duration) {
        Toast.makeText(context, message, duration).show();
    }
}
