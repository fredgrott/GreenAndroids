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

import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;

import java.lang.reflect.Method;

class ViewFactoryBase implements ViewFactory {
    private Method method;
    private LayoutInflater layoutInflater;

    public ViewFactoryBase(LayoutInflater layoutInflater) {
        this.layoutInflater = layoutInflater;
        this.method = getOnCreateViewMethod();
    }

    private Method getOnCreateViewMethod() {
        try {
            Method method = LayoutInflater.class.getDeclaredMethod("onCreateView",
                    String.class, AttributeSet.class);
            method.setAccessible(true);
            return method;
        } catch (NoSuchMethodException e) {
            return null;
        }
    }

    @Override
    public View createView(View parent, String name, AttributeSet attrs) {
        if(method != null) {
            return onCreateView(name, attrs);
        }

        return null;
    }

    private View onCreateView(String name, AttributeSet attrs){
        try {
            return (View) method.invoke(layoutInflater, name, attrs);
        } catch (Exception e) {
            return null;
        }
    }
}
