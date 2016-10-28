package com.jub3r.publlicappshub;

import android.app.Application;

import com.parse.Parse;
import com.parse.ParseObject;

import static com.parse.Parse.Configuration.Builder;

/**
 * Created by Juber on 07/05/2016.
 */
public class ParseApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        ParseObject.registerSubclass(ParseAppClass.class);
        ParseObject.registerSubclass(ParseCodeClass.class);
        ParseObject.registerSubclass(ParseDeploymentClass.class);
        ParseObject.registerSubclass(ParseAuthorClass.class);

        Parse.initialize(new Builder(getApplicationContext())
                .applicationId("myAppId")
                .clientKey("empty")
                .server("https://jub3r-arqsoft.herokuapp.com/parse/")   // '/' important after 'parse'
                .build());
    }
}
