package com.example.chad.menuapp;

import android.app.Application;

import com.parse.Parse;
import com.parse.ParseObject;

/**
 * Created by lab430 on 2017/8/15.
 */

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        ParseObject.registerSubclass(DrinkInfo.class);
        Parse.initialize(new Parse.Configuration.Builder(this)
        .applicationId("uRe7PiGAm0ymxKKAZXDdwjBCM1BU0vALRKmLbYhd")
                .clientKey("8brqMPsG4WO75MFpXEp7BYYh1h5P0boPqjljqLLm")
                .server("https://parseapi.back4app.com/")
                .build()
        );


    }
}
