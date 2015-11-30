package com.kuba.skateagramclient.activity;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.google.inject.Inject;
import com.kuba.skateagramclient.R;
import com.kuba.skateagramclient.domain.Post;
import com.kuba.skateagramclient.managers.RequestBuilder;
import com.kuba.skateagramclient.view.PostView;
import com.kuba.skateagramclient.web.Urls;
import com.quentindommerc.superlistview.OnMoreListener;
import com.quentindommerc.superlistview.SuperListview;

import org.apache.http.HttpResponse;
import org.springframework.http.ResponseEntity;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import roboguice.activity.RoboActivity;
import roboguice.activity.RoboListActivity;

/**
 * Created by kuba on 29.11.2015.
 */
public class WallActivity extends RoboActivity implements SwipeRefreshLayout.OnRefreshListener, OnMoreListener {

    @Inject
    RequestBuilder requestBuilder;

    @Bind(R.id.submitPostBtn)
    Button submitPostBtn;

    @Bind(R.id.postsListView)
    SuperListview listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wall);
        ButterKnife.bind(this);

        PostArrayAdapter adapter = new PostArrayAdapter();
        listView.setAdapter(adapter);
        listView.setRefreshListener(this);
        listView.setupMoreListener(this,1);
    }

    @Override
    public void onMoreAsked(int numberOfItems, int numberBeforeMore, int currentItemPos)  {

    }

    @Override
    public void onRefresh() {

    }

    private class PostArrayAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return 5;
        }

        @Override
        public Object getItem(int position) {
            return "Itemek";
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            return PostView.inflate(parent);
        }
    }

    private class FetchPosts extends AsyncTask<Void,Void,ResponseEntity<Post[]>> {

        @Override
        protected ResponseEntity<Post[]> doInBackground(Void... params) {
            return null;
        }
    }
}
