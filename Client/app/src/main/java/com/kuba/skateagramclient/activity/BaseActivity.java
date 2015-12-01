package com.kuba.skateagramclient.activity;

import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;

import com.kuba.skateagramclient.R;
import com.kuba.skateagramclient.domain.Credentials;

import roboguice.activity.RoboActionBarActivity;
import roboguice.activity.RoboActivity;

/**
 * Created by kuba on 01.12.2015.
 */
public class BaseActivity extends RoboActionBarActivity {
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_logout) {
            Intent intent = new Intent(this,AuthenticateActivity.class);
            startActivity(intent);
        }
        return true;
    }
}
