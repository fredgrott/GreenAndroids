/*
  Copyright (C) 2016 Fred Grott(aka shareme GrottWorkShop)

Licensed under the Apache License, Version 2.0 (the "License"); you
may not use this file except in compliance with the License. You may
obtain a copy of the License at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an
"AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
either express or implied. See the License for the specific language
governing permissions and limitations under License.
 */

package com.github.shareme.greenandroids;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Intent;
import android.os.Build;
import android.os.Looper;
import android.os.StrictMode;
import android.util.Log;

import com.github.shareme.greenandroids.util.ANRError;
import com.github.shareme.greenandroids.util.ANRWatchDog;
import com.github.shareme.greenandroids.util.DroidUuidFactory;
import com.github.shareme.greenandroids.util.MyCrashLibrary;
import com.squareup.leakcanary.LeakCanary;

import timber.log.Timber;

/**
 * Created by fgrott on 9/27/2016.
 */

public class MyApplication extends Application {

  /**
   * an Universal UI that is privacy safe and can be used for
   * analytics.
   */
  public DroidUuidFactory myUUId;

  /**
   * you set this appTag to a string so that all through the
   * app any time you use a Timber log wrapper call it will
   * have the appTag label prefix.
   */
  public String appTag = "GreenAndroids";

  /**
   * The domain string for invokeLogActivity feature of topExceptionSetup,
   * you need to set this if you use that feature of the topExceptionSetup.
   * It must match android:name in activity meta and intentiflter meta in
   * your Android Manifest.
   */
  public String invokeLogActivityDomain = "com.mydomain.SendLog";

  @Override
  public void onCreate() {
    strictModeSetUp();

    super.onCreate();

    setUpUUID();

    topLevelExceptionSetUp();


    leakCanarySetUp();

    timberLogWrapperSetUp();

    anrWatchDogSetUp();


  }

  /**
   * sets up leakcanary for memory leak detection on
   * debug builds
   */
  public void leakCanarySetUp(){
    if (LeakCanary.isInAnalyzerProcess(this)) {
      // This process is dedicated to LeakCanary for heap analysis.
      // You should not init your app in this process.
      return;
    }
    LeakCanary.install(this);
    // Normal app init code...
  }



  /**
   * Sets up a User Unique ID that is encrypted for such uses
   * as analytics tracking, etc. You should never have to
   * override this.
   */
  public void setUpUUID(){
    myUUId =new DroidUuidFactory(this.getApplicationContext());

  }

  /**
   * sets up the timber log wrapper
   */
  public void timberLogWrapperSetUp(){

    if (BuildConfig.DEBUG) {
      Timber.plant(new Timber.DebugTree(){
        @Override
        protected void log(int priority, String tag, String message, Throwable t) {
          super.log(priority, appTag + tag, message, t);
        }

      });
    } else {
      Timber.plant(new CrashReportingTree());
    }

  }


  /** A tree which logs important information for crash reporting.
   *  For your specific Analytics Crash library extend
   *  MyCrashLibrary, override this and replace
   *  MyCrashLibrary refs with the extended class
   *
   */
  private static class CrashReportingTree extends Timber.Tree {
    @Override
    protected void log(int priority, String tag, String message, Throwable t) {
      if (priority == Log.VERBOSE || priority == Log.DEBUG) {
        return;
      }

      MyCrashLibrary.log(priority, tag, message);

      if (t != null) {
        if (priority == Log.ERROR) {
          MyCrashLibrary.logError(t);
        } else if (priority == Log.WARN) {
          MyCrashLibrary.logWarning(t);
        }
      }
    }
  }



  public void topLevelExceptionSetUp() {
    Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
      @Override
      public void uncaughtException(Thread thread, Throwable e) {
        handleUncaughtException(thread, e);
      }
    });


  }

  public boolean isUIThread(){
    return Looper.getMainLooper().getThread() == Thread.currentThread();
  }

  /**
   * If you are using a 3rd party analytics and logging than you might
   * want to put those hooks to sending log file in this method, ie
   * override this method
   */
  @SuppressWarnings("StatementWithEmptyBody")
  private void handleUncaughtException (Thread thread, Throwable e){
    e.printStackTrace(); // not all Android versions will print the stack trace automatically

    if(isUIThread()) {
      //invokeLogActivity();
    }else{  //handle non UI thread throw uncaught exception

      //new Handler(Looper.getMainLooper()).post(new Runnable() {
      // @Override
      // public void run() {
      // invokeLogActivity();
      //}
      //});
    }
  }


  /**
   * and in the manifest, if you are using the invokeLogActivity option:

   <code>
   <manifest xmlns:android="http://schemas.android.com/apk/res/android" ... >
   <!-- needed for Android 4.0.x and earlier -->
   <uses-permission android:name="android.permission.READ_LOGS" />

   <application ... >
   <activity
   android:name="com.mydomain.SendLog"
   android:theme="@android:style/Theme.Dialog"
   android:textAppearance="@android:style/TextAppearance.Large"
   android:windowSoftInputMode="stateHidden">
   <intent-filter>
   <action android:name="com.mydomain.SEND_LOG" />
   <category android:name="android.intent.category.DEFAULT" />
   </intent-filter>
   </activity>
   </application>
   </manifest>
   </code>

   */
  private void invokeLogActivity(){
    Intent intent = new Intent ();
    intent.setAction (invokeLogActivityDomain);
    intent.setFlags (Intent.FLAG_ACTIVITY_NEW_TASK);
    startActivity (intent);

    System.exit(1); // kill off the crashed app
  }




  /**
   * Sets up ANRWatchDog, you should not have to override
   * this with your implementation.
   */
  public void anrWatchDogSetUp(){
    if(!BuildConfig.DEBUG){
      new ANRWatchDog().setANRListener(new ANRWatchDog.ANRListener() {
        public void onAppNotResponding(ANRError error) {
          // Handle the error. For example, log it to HockeyApp: ExceptionHandler.saveException(error, new CrashManager());
        }
      }).start();

    }
  }

  @SuppressLint("NewApi")
  public void strictModeSetUp(){
    if(BuildConfig.DEBUG){
      if(Build.VERSION.SDK_INT < Build.VERSION_CODES.M){
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
                .detectDiskReads()
                .detectDiskWrites()
                .detectNetwork()
                .detectCustomSlowCalls()
                .penaltyLog()
                .build());
      }else{
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
                .detectDiskReads()
                .detectDiskWrites()
                .detectNetwork()
                .detectCustomSlowCalls()
                .detectResourceMismatches()
                .penaltyLog()
                .build());
      }
      if(Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR2){
        StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
                .detectLeakedClosableObjects()
                .detectLeakedSqlLiteObjects()
                .detectLeakedRegistrationObjects()
                .detectActivityLeaks()
                .penaltyLog()
                .penaltyDeath()
                .build());
      }if(Build.VERSION.SDK_INT == Build.VERSION_CODES.JELLY_BEAN_MR2){
        StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
                .detectLeakedClosableObjects()
                .detectLeakedSqlLiteObjects()
                .detectLeakedRegistrationObjects()
                .detectActivityLeaks()
                .detectFileUriExposure()
                .penaltyLog()
                .penaltyDeath()
                .build());
      }if(Build.VERSION.SDK_INT> Build.VERSION_CODES.JELLY_BEAN_MR2 || Build.VERSION.SDK_INT < Build.VERSION_CODES.M){
        StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
                .detectLeakedClosableObjects()
                .detectLeakedSqlLiteObjects()
                .detectLeakedRegistrationObjects()
                .detectActivityLeaks()
                .detectFileUriExposure()
                .penaltyLog()
                .penaltyDeath()
                .build());
      }if(Build.VERSION.SDK_INT> Build.VERSION_CODES.LOLLIPOP_MR1 || Build.VERSION.SDK_INT<Build.VERSION_CODES.N){
        StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
                .detectLeakedClosableObjects()
                .detectLeakedSqlLiteObjects()
                .detectLeakedRegistrationObjects()
                .detectActivityLeaks()
                .detectFileUriExposure()
                .detectCleartextNetwork()
                .penaltyLog()
                .penaltyDeath()
                .build());
      }if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.N){
        StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
                .detectLeakedClosableObjects()
                .detectLeakedSqlLiteObjects()
                .detectLeakedRegistrationObjects()
                .detectActivityLeaks()
                .detectFileUriExposure()
                .detectCleartextNetwork()
                .penaltyLog()
                .penaltyDeath()
                .build());
      }
    }




  }

}
