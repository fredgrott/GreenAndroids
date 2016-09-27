/*
  Copyright (C) 2015 Nemi
  Modifications Copyright(C) 2016 Fred Grott(GrottWorkShop)

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
package com.github.shareme.greenandroids.greenandroid.viewdecorator;

import android.app.Activity;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.UiThread;
import android.support.v4.view.LayoutInflaterCompat;
import android.support.v4.view.LayoutInflaterFactory;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.util.Log;
import android.view.LayoutInflater;

/**
 * Utility class for installing a ViewDecorator with an activity
 * */
public class ViewDecoratorInstaller {
    private static final String TAG = "ViewDecoratorInstaller";

    private ViewDecorator viewDecorator;

    /**
     * Constructor
     *
     * @param viewDecorator view decorator to be used by the helper. Can't be null
     * */
    public ViewDecoratorInstaller(@NonNull ViewDecorator viewDecorator) {
        this.viewDecorator = viewDecorator;
    }

    /**
     * Installs the helper to an activity. Once the helper is installed to an activity it can't be installed to another activty
     *
     * @param activity activity to install the helper to
     * @return true if installation was successful, false otherwise
     * @throws IllegalStateException if helper is already install with the activity
     * */
    @UiThread
    public boolean install(@NonNull AppCompatActivity activity) {
        AppCompatDelegate delegate = activity.getDelegate();

        LayoutInflater layoutInflater = activity.getLayoutInflater();

        if(layoutInflater.getFactory() == null) {
            LayoutInflaterFactory layoutInflaterFactory = new DecoratingLayoutInflaterFactory(
                    new CompositingViewFactory(getPlatformViewFactory(layoutInflater),
                            new AppCompatViewFactory(delegate, activity)), viewDecorator);
            LayoutInflaterCompat.setFactory(layoutInflater, layoutInflaterFactory);
            return true;
        }

        Log.w(TAG, "view decorator can't be installed, layout infalter already has view factory installed");

        return false;
    }

    @UiThread
    public boolean install(@NonNull Activity activity) {
        LayoutInflater layoutInflater = activity.getLayoutInflater();

        if(layoutInflater.getFactory() == null) {
            LayoutInflaterFactory layoutInflaterFactory = new DecoratingLayoutInflaterFactory(
                    getPlatformViewFactory(layoutInflater), viewDecorator);
            LayoutInflaterCompat.setFactory(layoutInflater, layoutInflaterFactory);
            return true;
        }

        return false;
    }

    private ViewFactory getPlatformViewFactory(LayoutInflater layoutInflater) {
        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB) {
            return new ViewFactoryBase(layoutInflater);
        }

        return new ViewFactoryV11(layoutInflater);
    }


}
