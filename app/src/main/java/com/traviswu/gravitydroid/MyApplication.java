package com.traviswu.gravitydroid;

import android.app.Application;
import android.content.Context;

import com.parse.Parse;
import com.parse.ParseFacebookUtils;
import com.parse.ParseObject;

/**
 * Created by sylve_000 on 2015-04-24.
 */
public class MyApplication extends Application {

    private Context context = this;

    @Override
    public void onCreate() {
        super.onCreate();
        ParseObject.registerSubclass(Beam.class);
        Parse.initialize(this, "pzCn3tK5nxbPM0rh8YJywB9ObBVL1wUUZmCHR8f3", "YwSI0LpIJjRCeyOr7Lb16yJWmzgyOyQLOrdo1DXO");
        ParseFacebookUtils.initialize(context);
    }
}
