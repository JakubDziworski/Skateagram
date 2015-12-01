package com.kuba.skateagramclient.activity;

import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.VideoView;

import com.github.lzyzsd.circleprogress.ArcProgress;
import com.google.inject.Inject;
import com.kuba.skateagramclient.R;
import com.kuba.skateagramclient.domain.Post;
import com.kuba.skateagramclient.domain.PostBuilder;
import com.kuba.skateagramclient.managers.FTPFileUploader;
import com.kuba.skateagramclient.managers.MediaPicker;
import com.kuba.skateagramclient.managers.RequestBuilder;
import com.kuba.skateagramclient.managers.SharedPrefsManager;
import com.kuba.skateagramclient.managers.ToastManager;
import com.kuba.skateagramclient.web.Urls;

import org.springframework.http.ResponseEntity;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTouch;
import roboguice.activity.RoboActivity;

/**
 * Created by kuba on 29.11.2015.
 */
public class SubmitPostActivity extends BaseActivity {

    @Inject
    private MediaPicker mediaPicker;
    @Inject
    private RequestBuilder requestBuilder;
    @Inject
    private SharedPrefsManager sharedPrefsManager;
    @Inject
    private ToastManager toastManager;
    @Inject
    FTPFileUploader fileUploader;

    @Bind(R.id.uploadProgress)
    ArcProgress uploadProgressBar;

    @Bind(R.id.videViewOverlay)
    protected FrameLayout videoOverlay;

    @Bind(R.id.videoViewPlaceHolderText)
    protected TextView videoViewPlaceHolderText;

    @Bind(R.id.submitBtn)
    protected Button submitPostBtn;

    @Bind(R.id.videoView)
    protected VideoView videoView;

    @Bind(R.id.spotNameText)
    protected TextView spotNameText;

    @Bind(R.id.trickNameText)
    protected TextView trickNameText;

    @OnTouch(R.id.videoView)
    boolean chooseVideoBtnClick(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            mediaPicker.pickVideo(SubmitPostActivity.this);
        }
        return super.onTouchEvent(event);
    }

    @OnClick(R.id.submitBtn)
    void submitPostBtnClick() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                fileUploader.upload(mediaPicker.getCurrentVideoPath(), uploadListener);
                goBackToWall();
            }
        }).start();
    }

    private void goBackToWall() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SubmitPostActivity.this,WallActivity.class);
                startActivity(intent);
            }
        });
    }

    private FTPFileUploader.UploadListener uploadListener = new FTPFileUploader.UploadListener() {
        @Override
        public void progressChanged(final int percentage) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    uploadProgressBar.setProgress(percentage);
                }
            });
        }

        @Override
        public void start() {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    uploadProgressBar.setVisibility(View.VISIBLE);
                }
            });
        }

        @Override
        public void failure() {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    uploadProgressBar.setFinishedStrokeColor(Color.RED);
                    uploadProgressBar.setUnfinishedStrokeColor(Color.RED);
                    uploadProgressBar.setBottomText("ERROR!");
                }
            });
        }

        @Override
        public void success(final String remoteUrl) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    uploadProgressBar.setProgress(100);
                    Post post = buildPost(remoteUrl);
                    submitPost(post);
                }
            });
        }
    };

    private Post buildPost(String remoteUrl) {
        return new PostBuilder()
                .setSpotName(spotNameText.getText().toString())
                .setTrickName(trickNameText.getText().toString())
                .setVideoURL(remoteUrl).createPost();
    }


    private void submitPost(Post post) {
        new AsyncTask<Post, Void, ResponseEntity<?>>() {
            @Override
            protected ResponseEntity<?> doInBackground(Post... params) {
                return requestBuilder.postToUrl(Urls.POSTS,params[0],Post.class);
            }
        }.execute(post);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.submit);
        ButterKnife.bind(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(!mediaPicker.isActivityResultForMediaPicker(requestCode,resultCode,data)) {
            return;
        }
        mediaPicker.handleOnActivityResults(requestCode, resultCode, data);
        videoView.setVideoPath(mediaPicker.getCurrentVideoPath());
        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mp.start();
                videoOverlay.setVisibility(View.INVISIBLE);
                submitPostBtn.setEnabled(true);
            }
        });
        videoView.setOnErrorListener(new MediaPlayer.OnErrorListener() {
            @Override
            public boolean onError(MediaPlayer mp, int what, int extra) {
                mp.stop();
                videoOverlay.setVisibility(View.VISIBLE);
                videoViewPlaceHolderText.setText("There was an error. Pick other video");
                submitPostBtn.setEnabled(false);
                return true;
            }
        });
    }
}
