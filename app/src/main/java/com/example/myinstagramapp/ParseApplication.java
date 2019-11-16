package com.example.myinstagramapp;

import android.app.Application;

import com.parse.Parse;
import com.parse.ParseObject;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;

public class ParseApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        ParseObject.registerSubclass(Post.class);

        // USED FOR DEBUGGING
        Parse.setLogLevel(Parse.LOG_LEVEL_DEBUG);
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        builder.networkInterceptors().add(loggingInterceptor);

        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId("nigina-instagram")
                .clientBuilder(builder)
                .clientKey("NiginaInstagramMasterKey")
                .server("http://nigina-instagram.herokuapp.com/parse").build());
    }
}
