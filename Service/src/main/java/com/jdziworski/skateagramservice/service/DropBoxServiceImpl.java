package com.jdziworski.skateagramservice.service;

import com.dropbox.core.*;
import org.springframework.stereotype.Service;
import javax.annotation.PostConstruct;
import java.util.Locale;

/**
 * Created by kuba on 20.11.2015.
 */
@Service
public class DropBoxServiceImpl implements DropBoxService {

//    @PostConstruct
//    public void init() {
//        final String APP_KEY = "0z7d2xb8qpugvak";
//        final String APP_SECRET = "gi0vp0htqnebwny";
//        DbxAppInfo appInfo = new DbxAppInfo(APP_KEY, APP_SECRET);
//        DbxRequestConfig config = new DbxRequestConfig("Skateagram", Locale.getDefault().toString());
//        DbxWebAuthNoRedirect webAuth = new DbxWebAuthNoRedirect(config, appInfo);
//        String authorizeUrl = webAuth.start();
//        try {
//            webAuth.finish("2");
//        } catch (DbxException e) {
//            e.printStackTrace();
//        }
//        System.out.println(authorizeUrl);
//    }
}
