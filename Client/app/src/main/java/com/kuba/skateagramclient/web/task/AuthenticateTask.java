package com.kuba.skateagramclient.web.task;

import android.os.AsyncTask;

import com.google.inject.Inject;
import com.kuba.skateagramclient.domain.Credentials;
import com.kuba.skateagramclient.domain.User;
import com.kuba.skateagramclient.managers.SharedPrefsManager;

import org.springframework.http.ResponseEntity;

import roboguice.RoboGuice;
import roboguice.inject.RoboInjector;

/**
 * Created by kuba on 29.11.2015.
 */
public abstract class AuthenticateTask extends AsyncTask<Void, Void, ResponseEntity<?>> {
    private Credentials credentials;

    public AuthenticateTask(Credentials credentials) {
        this.credentials = credentials;
    }

    @Override
    protected ResponseEntity<?> doInBackground(Void... params) {
        User user = new User(credentials);
        return onStart(user);
    }

    protected abstract ResponseEntity<?> onStart(User user);
}