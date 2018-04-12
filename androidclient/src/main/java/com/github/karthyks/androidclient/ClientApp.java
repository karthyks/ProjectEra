package com.github.karthyks.androidclient;


import android.app.Application;
import android.support.multidex.MultiDex;

public class ClientApp extends Application {

  @Override
  public void onCreate() {
    super.onCreate();
    MultiDex.install(this);
  }
}
