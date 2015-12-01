package com.kuba.skateagramclient.managers;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Base64;

import com.google.inject.Singleton;
import com.kuba.skateagramclient.domain.User;

import org.springframework.http.HttpHeaders;

import javax.inject.Inject;

/**
 * Created by kuba on 28.11.2015.
 */
@Singleton
public class SharedPrefsManager {
    private final String USERNAME_KEY = "username";
    private final String CREDENTIALS_SHARED_PREFS_KEY = "user_credentials";
    private final String SHARED_PREFS_NAME = "shared_preferences";
    private SharedPreferences sharedPreferences;

    @Inject
    public SharedPrefsManager(Context context) {
        this.sharedPreferences = context.getSharedPreferences(SHARED_PREFS_NAME,Context.MODE_PRIVATE);
    }

    public String getUserCred() {
        return sharedPreferences.getString(CREDENTIALS_SHARED_PREFS_KEY,null);
    }

    public String getUserName() {
        return sharedPreferences.getString(USERNAME_KEY,null);
    }

    public String saveCredentials(String userName,String password) {
        String baseAuthEncodedCreds = encode(userName,password);
        sharedPreferences.edit()
                .putString(CREDENTIALS_SHARED_PREFS_KEY,baseAuthEncodedCreds)
                .putString(USERNAME_KEY,userName).commit();
        return baseAuthEncodedCreds;
    }


    private String encode(String userName,String password) {
        String plainCreds = userName+":"+password;
        byte[] plainCredsBytes = plainCreds.getBytes();
        byte[] base64CredsBytes = Base64.encode(plainCredsBytes, 0);
        return new String(base64CredsBytes);
    }
}
