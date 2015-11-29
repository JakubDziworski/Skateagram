package com.kuba.skateagramclient.activity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.inject.Inject;
import com.kuba.skateagramclient.R;
import com.kuba.skateagramclient.domain.Credentials;
import com.kuba.skateagramclient.domain.User;
import com.kuba.skateagramclient.managers.RequestBuilder;
import com.kuba.skateagramclient.managers.SharedPrefsManager;
import com.kuba.skateagramclient.managers.ToastManager;
import com.kuba.skateagramclient.web.Urls;

import org.apache.http.client.HttpResponseException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;


import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import roboguice.activity.RoboActionBarActivity;


public class AuthenticateActivity extends RoboActionBarActivity {
    @Inject
    SharedPrefsManager sharedPrefsManager;
    @Inject
    RequestBuilder requestBuilder;
    @Inject
    ToastManager toastManager;

    private class CreateUserTask extends AsyncTask<Void, Void, ResponseEntity<?>> {
        @Override
        protected ResponseEntity<?> doInBackground(Void... params) {
            User user = createUserFromEditTexts();
            sharedPrefsManager.saveCredentials(user.getUsername(),user.getPassword());
            return requestBuilder.postToUrlWithoutCredentials(Urls.USERS,user,Object.class);
        }

        @Override
        protected void onPostExecute(ResponseEntity<?> response) {
            if(response == null) {
                toastManager.showToast("unknown error");
            } else if (response.getStatusCode() == HttpStatus.CONFLICT) {
                toastManager.showToast("user already exists:");
            } else if (response.getStatusCode() == HttpStatus.CREATED) {
                toastManager.showToast("user registered:" + response);
            } else if (response.getStatusCode() == HttpStatus.BAD_REQUEST) {
                toastManager.showToast("Invalid form:" + response.getBody());
            }
            else {
                toastManager.showToast("error:" + response.getStatusCode());
            }
        }
    }

    private class LoginUserRequest extends AsyncTask<Void, Void, ResponseEntity<?>> {
        @Override
        protected ResponseEntity<?> doInBackground(Void... params) {
            User user = createUserFromEditTexts();
            sharedPrefsManager.saveCredentials(user.getUsername(),user.getPassword());
            return requestBuilder.getForUrl(Urls.USERS + "/" +user.getUsername(),User.class);
        }

        @Override
        protected void onPostExecute(ResponseEntity<?> user) {
            if(user == null) {
                toastManager.showToast("unknown error");
            } else if(user.getStatusCode() == HttpStatus.OK) {
                toastManager.showToast("succesfully logged in");
            } else {
                toastManager.showToast("Error " + user.getStatusCode());
            }
        }
    }

    @Bind(R.id.loginText)
    EditText loginText;

    @Bind(R.id.passText)
    EditText passText;

    @Bind(R.id.loginBtn)
    Button loginBtn;

    @Bind(R.id.registerBtn)
    Button registerBtn;

    @OnClick(R.id.registerBtn)
    void onRegisterBtnClick() {
        final String username = loginText.getText().toString();
        final String password = passText.getText().toString();
        final CreateUserTask createUserTask = new CreateUserTask();
        createUserTask.execute();
    }

    @OnClick(R.id.loginBtn)
    void onLoginBtnClick() {
        final String username = loginText.getText().toString();
        final String password = passText.getText().toString();
        final LoginUserRequest loginUserRequest = new LoginUserRequest();
        loginUserRequest.execute();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authenticate);
        ButterKnife.bind(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_log_in, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private User createUserFromEditTexts() {
        final String username = loginText.getText().toString();
        final String password = passText.getText().toString();
        return new User(username,password);
    }
}
