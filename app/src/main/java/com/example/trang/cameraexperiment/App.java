package com.example.trang.cameraexperiment;

import android.app.Application;
import android.content.Context;

/**
 * Created by Trang on 4/10/2017.
 */

public class App extends Application {
public static Context context;
    public static Context getContext() {
        return context;
    }

    @Override
    public void onCreate() {
        this.context = this;
        super.onCreate();

    }



}
