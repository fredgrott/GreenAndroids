/*
 * Copyright (C) 2015 Mantas Palaima
 * Modifications Copyright(C) 2016 Fred Grott(GrottWorkShop)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.github.shareme.greenandroids.whiteandroid.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import com.github.shareme.greenandroids.whiteandroid.base.DebugModule;


public class DebugView extends LinearLayout {

    private DebugModule[] mDrawerItems;

    public DebugView(Context context) {
        super(context);
        setOrientation(VERTICAL);
    }

    public DebugView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setOrientation(VERTICAL);
    }

    public DebugView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setOrientation(VERTICAL);
    }

    /**
     * Calls modules {@link DebugModule#onResume()} method
     */
    public void onResume() {
        if (mDrawerItems != null) {
            for (DebugModule drawerItem : mDrawerItems) {
                drawerItem.onResume();
            }
        }
    }

    /**
     * Calls modules {@link DebugModule#onPause()} method
     */
    public void onPause() {
        if (mDrawerItems != null) {
            for (DebugModule drawerItem : mDrawerItems) {
                drawerItem.onPause();
            }
        }
    }

    /**
     * Starts all modules and calls their {@link DebugModule#onStart()} method
     */
    public void onStart() {
        if (mDrawerItems != null) {
            for (DebugModule drawerItem : mDrawerItems) {
                drawerItem.onStart();
            }
        }
    }

    /**
     * Removes all modules and calls their {@link DebugModule#onStop()} method
     */
    public void onStop() {
        if (mDrawerItems != null) {
            for (DebugModule drawerItem : mDrawerItems) {
                drawerItem.onStop();
            }
        }
    }

    public void modules(DebugModule... drawerItems) {
        mDrawerItems = drawerItems;
        if (this.mDrawerItems != null && this.mDrawerItems.length != 0) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            DebugModule drawerItem;
            for (int i = 0; i < this.mDrawerItems.length; i++) {
                drawerItem = this.mDrawerItems[i];
                addView(drawerItem.onCreateView(inflater, this));
            }
        }
    }
}
