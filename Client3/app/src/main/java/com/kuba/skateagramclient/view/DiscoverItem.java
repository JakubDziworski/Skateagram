package com.kuba.skateagramclient.view;

import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.inject.Inject;
import com.kuba.skateagramclient.R;
import com.kuba.skateagramclient.domain.User;
import com.kuba.skateagramclient.managers.RequestBuilder;
import com.kuba.skateagramclient.web.Urls;

import org.apache.http.HttpResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Random;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import roboguice.RoboGuice;

/**
 * Created by kuba on 01.12.2015.
 */
public class DiscoverItem extends RelativeLayout{

    @Inject
    RequestBuilder requestBuilder;

    @Bind(R.id.discoverItemfollowBtn)
    Button followBtn;

    @Bind(R.id.discoverItemUserNameText)
    TextView userNameText;

    @Bind(R.id.discoverItemUserPostsText)
    TextView userPostsText;

    @OnClick(R.id.discoverItemfollowBtn)
    void onFollowBtnClick() {
        new AsyncTask<Void,Void,ResponseEntity<?>>() {

            @Override
            protected ResponseEntity<?> doInBackground(Void... params) {
                final String userName = userNameText.getText().toString();
                return requestBuilder.postToUrl(Urls.USERS+"/"+userName+"/follow",null,Object.class);
            }

            @Override
            protected void onPostExecute(ResponseEntity<?> responseEntity) {
                if(responseEntity.getStatusCode() == HttpStatus.NO_CONTENT) {
                    setBackgroundColor(Color.GREEN);
                }
            }
        }.execute();
    }

    public DiscoverItem(Context context) {
        super(context);
        RoboGuice.getInjector(getContext()).injectMembers(this);
    }

    public DiscoverItem(Context context, AttributeSet attrs) {
        super(context, attrs);
        RoboGuice.getInjector(getContext()).injectMembers(this);
    }

    public DiscoverItem(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        RoboGuice.getInjector(getContext()).injectMembers(this);
    }

    public static DiscoverItem instance(ViewGroup parent) {
        DiscoverItem view = (DiscoverItem) LayoutInflater.from(parent.getContext()).inflate(R.layout.view_discover_item, parent, false);
        ButterKnife.bind(view);
        return view;
    }

    public void populate(User user) {
        userPostsText.setText(new Random().nextInt(2)+2 + " Tricks");
        userNameText.setText(user.getUsername());
    }
}
