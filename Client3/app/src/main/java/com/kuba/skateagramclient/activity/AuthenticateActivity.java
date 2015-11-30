package com.kuba.skateagramclient.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;

import com.google.inject.Inject;
import com.kuba.skateagramclient.R;
import com.kuba.skateagramclient.domain.Credentials;
import com.kuba.skateagramclient.domain.User;
import com.kuba.skateagramclient.managers.RequestBuilder;
import com.kuba.skateagramclient.managers.SharedPrefsManager;
import com.kuba.skateagramclient.managers.ToastManager;
import com.kuba.skateagramclient.web.Urls;
import com.kuba.skateagramclient.web.task.AuthenticateTask;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;


import java.util.concurrent.Executor;

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
    private Executor executor;

    private class CreateUserTask extends AuthenticateTask {

        public CreateUserTask(Credentials credentials) {
            super(credentials);
        }

        @Override
        protected ResponseEntity<?> onStart(User user) {
            sharedPrefsManager.saveCredentials(user.getUsername(),user.getPassword());
            return requestBuilder.postToUrlWithoutCredentials(Urls.USERS,user,Object.class);
        }

        @Override
        protected void onPostExecute(ResponseEntity<?> response) {
            if(response == null) {
                toastManager.showToast("unknown error");
            } else if (response.getStatusCode() == HttpStatus.CONFLICT) {
                toastManager.showToast("user already exists:");
                toastManager.showToast("Invalid form:" + response.getBody());
            } else if (response.getStatusCode() == HttpStatus.CREATED) {
                toastManager.showToast("user registered:" + response);
                goToWall();
            } else if (response.getStatusCode() == HttpStatus.BAD_REQUEST) {
            }
            else {
                toastManager.showToast("error:" + response.getStatusCode());
            }
        }
    }

    private class LoginTask extends AuthenticateTask {

        public LoginTask(Credentials credentials) {
            super(credentials);
        }

        @Override
        protected ResponseEntity<?> onStart(User user) {
            sharedPrefsManager.saveCredentials(user.getUsername(),user.getPassword());
            return requestBuilder.getForUrl(Urls.USERS + "/" + user.getUsername(), User.class);
        }

        @Override
        protected void onPostExecute(ResponseEntity<?> user) {
            if(user == null) {
                toastManager.showToast("unknown error");
            } else if(user.getStatusCode() == HttpStatus.OK) {
                toastManager.showToast("succesfully logged in");
                goToWall();
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
        Intent intent = new Intent(this,SubmitPostActivity.class);
        startActivity(intent);
        final CreateUserTask createUserTask = new CreateUserTask(buildCredentialsFromEditTexts());
        createUserTask.execute();
    }

    @OnClick(R.id.loginBtn)
    void onLoginBtnClick() {
        final LoginTask loginTask = new LoginTask(buildCredentialsFromEditTexts());
        loginTask.execute();
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

    private Credentials buildCredentialsFromEditTexts() {
        final String username = loginText.getText().toString();
        final String password = passText.getText().toString();
        return new Credentials(username,password);
    }


    private void goToWall() {
        Intent intent = new Intent(this,WallActivity.class);
        startActivity(intent);
    }
}
