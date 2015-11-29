package com.kuba.skateagramclient.web;

import android.os.AsyncTask;

import com.kuba.skateagramclient.domain.ResourceSupport;
import com.kuba.skateagramclient.domain.User;

import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.ExecutionException;

/**
 * Created by kuba on 28.11.2015.
 */
public class Urls {
    public final static String ROOT = "http://10.7.78.199:8080/skateagram";
    public final static String RESOURCES = ROOT + "/resources";
    public final static String USERS = ROOT + "/users";
    public final static String POSTS = ROOT + "/posts";
    public final static String UPLOAD = ROOT + "/upload";
}
