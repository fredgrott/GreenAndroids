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
package com.github.shareme.greenandroids.util;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.preference.PreferenceManager;

import com.github.shareme.greenandroids.R;

/**
 * Just a simple Eula
 *
 * Usage:
 *
 * in the main app activity
 *
 * <code>
 *   @Override
 *   public void onCreate(Bundle savedInstanceState) {
 *       super.onCreate(savedInstanceState);
 *       setContentView(R.layout.main);
 *
 *       new SimpleEula(this).show();
 *   }
 *
 * </code>
 * Created by fgrott on 9/28/2016.
 */
@SuppressWarnings("unused")
public class SimpleEula {

  private String EULA_PREFIX = "eula_";
  private Activity mActivity;

  public SimpleEula(Activity context) {
    mActivity = context;
  }

  private PackageInfo getPackageInfo() {
    PackageInfo pi = null;
    try {
      pi = mActivity.getPackageManager().getPackageInfo(mActivity.getPackageName(), PackageManager.GET_ACTIVITIES);
    } catch (PackageManager.NameNotFoundException e) {
      e.printStackTrace();
    }
    return pi;
  }

  public void show() {
    PackageInfo versionInfo = getPackageInfo();

    // the eulaKey changes every time you increment the version number in the AndroidManifest.xml
    final String eulaKey = EULA_PREFIX + versionInfo.versionCode;
    final SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(mActivity);
    boolean hasBeenShown = prefs.getBoolean(eulaKey, false);
    if(!hasBeenShown){

      // Show the Eula
      String title = mActivity.getString(R.string.app_name) + " v" + versionInfo.versionName;

      //Includes the updates as well so users know what changed.
      String message = mActivity.getString(R.string.updates) + "\n\n" + mActivity.getString(R.string.eula);

      AlertDialog.Builder builder = new AlertDialog.Builder(mActivity)
              .setTitle(title)
              .setMessage(message)
              .setPositiveButton(android.R.string.ok, new Dialog.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                  // Mark this version as read.
                  SharedPreferences.Editor editor = prefs.edit();
                  editor.putBoolean(eulaKey, true);
                  editor.apply();
                  dialogInterface.dismiss();
                }
              })
              .setNegativeButton(android.R.string.cancel, new Dialog.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {
                  // Close the activity as they have declined the EULA
                  mActivity.finish();
                }

              });
      builder.create().show();
    }
  }

}
