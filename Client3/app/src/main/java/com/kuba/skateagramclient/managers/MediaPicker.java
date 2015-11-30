package com.kuba.skateagramclient.managers;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;

import com.google.inject.Inject;

import org.apache.commons.lang.StringUtils;

import java.io.File;

/**
 * Created by kuba on 29.11.2015.
 */
public class MediaPicker {
    public static final int PICK_VIDEO_REQUEST_CODE = 1;
    private String currentVideoPath;

    @Inject
    Context context;

    public void pickVideo(Activity activity) {
        Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("video/*");
        activity.startActivityForResult(intent, PICK_VIDEO_REQUEST_CODE);
    }

    public void handleOnActivityResults(int requestCode, int resultCode, Intent data)  {
        if(requestCode != PICK_VIDEO_REQUEST_CODE) return;
        if(resultCode != Activity.RESULT_OK) return;
        Uri selectedImage = data.getData();
        String[] filePathColumn = {MediaStore.Video.Media.DATA};

        Cursor cursor = context.getContentResolver().query(
                selectedImage, filePathColumn, null, null, null);
        cursor.moveToFirst();

        int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
        String filePath = cursor.getString(columnIndex);
        File file = new File(filePath);
        if(file.exists()) {
            currentVideoPath = filePath;
        }
        cursor.close();
    }

    public String getCurrentVideoPath() {
        return currentVideoPath;
    }
}