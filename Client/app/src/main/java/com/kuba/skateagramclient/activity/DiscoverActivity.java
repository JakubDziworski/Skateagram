package com.kuba.skateagramclient.activity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.google.inject.Inject;
import com.kuba.skateagramclient.R;
import com.kuba.skateagramclient.domain.Post;
import com.kuba.skateagramclient.domain.User;
import com.kuba.skateagramclient.domain.User;
import com.kuba.skateagramclient.managers.RequestBuilder;
import com.kuba.skateagramclient.view.DiscoverItem;
import com.kuba.skateagramclient.view.PostView;
import com.kuba.skateagramclient.web.Urls;
import com.quentindommerc.superlistview.SuperListview;

import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by kuba on 01.12.2015.
 */
public class DiscoverActivity extends BaseActivity{

    @Inject
    RequestBuilder requestBuilder;

    @Bind(R.id.dicoverListView)
    SuperListview listView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_discover);
        ButterKnife.bind(this);

        DiscoverAdapter adapter = new DiscoverAdapter();
        listView.setAdapter(adapter);
        listView.setRefreshListener(adapter);
    }


    private class DiscoverAdapter extends BaseAdapter implements SwipeRefreshLayout.OnRefreshListener {

        private List<User> users;
        private DiscoverAdapter() {
            users = Collections.emptyList();
            fetch();
        }

        public void fetch() {
            new AsyncTask<Void,Void,ResponseEntity <?>>() {

                @Override
                protected ResponseEntity<?> doInBackground(Void... params) {
                    return requestBuilder.getForUrl(Urls.UNFOLLOWED,User[].class );
                }

                @Override
                protected void onPostExecute(ResponseEntity<?> response) {
                    final Object body = response.getBody();
                    final User[] users = (User[]) body;
                    final List<User> usersList = Arrays.asList(users);
                    DiscoverAdapter.this.users = usersList;
                    DiscoverAdapter.this.notifyDataSetInvalidated();
                    listView.getSwipeToRefresh().setRefreshing(false);
                }
            }.execute();
        }

        @Override
        public int getCount() {
            return users.size();
        }

        @Override
        public Object getItem(int position) {
            return users.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            DiscoverItem discoverItemView = DiscoverItem.instance(parent);
            discoverItemView.populate((User) getItem(position));
            return discoverItemView;
        }

        @Override
        public void onRefresh() {
            fetch();
        }
    }
}
