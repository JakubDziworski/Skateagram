package com.kuba.skateagramclient.managers;

import android.content.Context;
import android.content.res.Resources;
import android.util.Log;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.kuba.skateagramclient.R;

import org.apache.commons.io.input.CountingInputStream;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * Created by kuba on 29.11.2015.
 */
@Singleton
public class FTPFileUploader {

    @Inject
    Context context;
    FTPClient client;

    public interface UploadListener {
        void progressChanged(int percentage);
        void start();
        void failure();
        void success();
    }

    public void connect(UploadListener uploadListener) throws IOException {
        if(client != null && client.isConnected() && client.isAvailable()) {
            Log.d(this.getClass().getName(), "Client already connected");
        }
        Log.v(this.getClass().getName(),"Connecting");
        client = new FTPClient();
        client.setBufferSize(102400);
        final Resources resources = context.getResources();
        final String sFTP = resources.getString(R.string.ftp_server_url);
        final String sUser = resources.getString(R.string.ftp_server_user);
        final String sPassword = resources.getString(R.string.ftp_server_pass);
        client.connect(sFTP);
        boolean login = client.login(sUser,sPassword);
        if(login) {
            Log.v(this.getClass().getName(),"Succesfully connected");
        } else {
            Log.v(this.getClass().getName(), "Unsecesfull connection");
            uploadListener.failure();
        }
    }

    public void upload(String filename,final UploadListener uploadListener) {
        uploadListener.start();
        FileInputStream fis = null;
        try {
            connect(uploadListener);
            client.setFileType(FTP.BINARY_FILE_TYPE,FTP.BINARY_FILE_TYPE);
            client.setFileTransferMode(FTP.BINARY_FILE_TYPE);
            File file = new File(filename);
            final long total = file.length();
            fis = new FileInputStream(file);
            CountingInputStream countingInputStream = new CountingInputStream(fis) {
                @Override
                protected synchronized void afterRead(int n) {
                    super.afterRead(n);
                    float percentageCompleted = (float) (100*getCount()) /(float)  total;
                    Log.v(this.getClass().getName(),"uploaded = " + percentageCompleted + "%");
                    uploadListener.progressChanged((int) percentageCompleted);
                }
            };
            Log.d(this.getClass().getName(), "About to start uploading file");
            client.storeFile("/htdocs/vidlo2.mp4", countingInputStream);
            Log.d(this.getClass().getName(), "File uploaded");
            fis.close();
            client.logout();
            uploadListener.success();
        } catch (IOException e) {
            e.printStackTrace();
            Log.v(this.getClass().getName(),"Error " + e.getMessage());
            uploadListener.failure();
        }
    }
}
