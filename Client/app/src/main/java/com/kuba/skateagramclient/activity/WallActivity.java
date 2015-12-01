package com.kuba.skateagramclient.activity;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
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

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import roboguice.activity.RoboActivity;
import roboguice.activity.RoboListActivity;

/**
 * Created by kuba on 29.11.2015.
 */
public class WallActivity extends BaseActivity {

    @Inject
    RequestBuilder requestBuilder;

    @Bind(R.id.postsListView)
    SuperListview listView;

    @OnClick(R.id.wallSubmitPostBtn)
     void submitBtnClick() {
        Intent intent = new Intent(this,SubmitPostActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.wallDiscoverPeopleBtn)
    void discoverBtnClick() {
        Intent intent = new Intent(this,DiscoverActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wall);
        ButterKnife.bind(this);

        PostArrayAdapter adapter = new PostArrayAdapter();
        listView.setAdapter(adapter);
        listView.setRefreshListener(adapter);
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    private class PostArrayAdapter extends BaseAdapter  implements SwipeRefreshLayout.OnRefreshListener {

        private List<Post> posts;
        private PostArrayAdapter() {
            posts = Collections.emptyList();
            fetch();
        }

        public void fetch() {
            new FetchPosts(new FetchPostListener() {
                @Override
                public void fetched(List<Post> posts) {
                    PostArrayAdapter.this.posts = posts;
                    PostArrayAdapter.this.notifyDataSetInvalidated();
                    listView.getSwipeToRefresh().setRefreshing(false);
                }
            }).execute();
        }

        @Override
        public int getCount() {
            return posts.size();
        }

        @Override
        public Object getItem(int position) {
            return posts.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            PostView postView = PostView.instance(parent);
            postView.populate((Post) getItem(position));
            return postView;
        }

        @Override
        public void onRefresh() {
            fetch();
        }
    }

    interface FetchPostListener {
        void fetched(List<Post> posts);
    }

    private class FetchPosts extends AsyncTask<Void,Void,ResponseEntity<?>> {

        private FetchPostListener listener;

        private FetchPosts(FetchPostListener listener) {
            this.listener = listener;
        }

        @Override
        protected ResponseEntity<?> doInBackground(Void... params) {
            return requestBuilder.getForUrl(Urls.WALL,Post[].class);
        }

        @Override
        protected void onPostExecute(ResponseEntity<?> responseEntity) {
            final Object body = responseEntity.getBody();
            final Post[] posts = (Post[]) body;
            final List<Post> postsList = Arrays.asList(posts);
            listener.fetched(postsList);
        }
    }
}
