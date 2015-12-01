package com.kuba.skateagramclient.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.media.MediaPlayer;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.VideoView;

import com.kuba.skateagramclient.R;
import com.kuba.skateagramclient.domain.Post;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnTouch;

/**
 * Created by kuba on 29.11.2015.
 */
public class PostView extends LinearLayout {

    @Bind(R.id.postTrickNameText)
    TextView trickNameText;

    @Bind(R.id.postSpotNameText)
    TextView spotNameText;

    @Bind(R.id.postAuthorNameText)
    TextView authorName;

    @Bind(R.id.postDateText)
    TextView dateText;

    @Bind(R.id.postVideoView)
    VideoView videoView;

    @Bind(R.id.postVideoViewProgressBar)
    FrameLayout videoViewProgressBar;

    @OnTouch(R.id.postVideoView)
    boolean chooseVideoBtnClick(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            if(videoView.isPlaying()) {
                videoView.pause();
            }
            else {
                videoView.start();
            }
        }
        return super.onTouchEvent(event);
    }

    public PostView(Context context) {
        super(context);
    }

    public PostView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public PostView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public static PostView instance(ViewGroup parent) {
        PostView view = (PostView) LayoutInflater.from(parent.getContext()).inflate(R.layout.post, parent, false);
        ButterKnife.bind(view);
        return view;
    }





    public void populate(final Post post) {
        authorName.setText(post.getUserId());
        dateText.setText(post.getDate());
        spotNameText.setText(post.getSpotId());
        trickNameText.setText(post.getTrickId());
        videoView.setVideoURI(Uri.parse(post.getVideoId()));
        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                videoViewProgressBar.setVisibility(INVISIBLE);
                videoView.setVisibility(VISIBLE);
                videoView.seekTo(500);
                mp.seekTo(500);
            }
        });
        videoView.setOnErrorListener(new MediaPlayer.OnErrorListener() {
            @Override
            public boolean onError(MediaPlayer mp, int what, int extra) {
                mp.stop();
                trickNameText.setText("There was an problem with video " + post.getVideoId() + "errors:" + what + " , " + extra);
                return true;
            }
        });
    }
}
