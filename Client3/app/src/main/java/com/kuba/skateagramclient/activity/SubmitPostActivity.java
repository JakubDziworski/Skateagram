package com.kuba.skateagramclient.activity;

import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
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
import com.kuba.skateagramclient.managers.FTPFileUploader;
import com.kuba.skateagramclient.managers.MediaPicker;
import com.kuba.skateagramclient.managers.ToastManager;

import java.util.concurrent.Executor;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTouch;
import roboguice.activity.RoboActivity;

/**
 * Created by kuba on 29.11.2015.
 */
public class SubmitPostActivity extends RoboActivity {

    @Inject
    private MediaPicker mediaPicker;
    private Executor executor;

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
            }
        }).start();
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
        public void success() {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    uploadProgressBar.setProgress(100);
                }
            });
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.submit);
        ButterKnife.bind(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
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
