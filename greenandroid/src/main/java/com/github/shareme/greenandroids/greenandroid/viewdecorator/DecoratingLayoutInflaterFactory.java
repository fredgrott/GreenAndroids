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

import android.content.Context;
import android.support.v4.view.LayoutInflaterFactory;
import android.util.AttributeSet;
import android.view.View;

class DecoratingLayoutInflaterFactory implements LayoutInflaterFactory {
    private ViewDecorator viewDecorator;
    private ViewFactory viewFactory;

    public DecoratingLayoutInflaterFactory(ViewFactory viewFactory, ViewDecorator viewDecorator) {
        this.viewDecorator = viewDecorator;
        this.viewFactory = viewFactory;
    }

    @Override
    public View onCreateView(View parent, String name, Context context, AttributeSet attrs) {
        View view = viewFactory.createView(parent, name, attrs);
        if(view != null) {
            viewDecorator.decorate(parent, view, context, attrs);
        }
        return view;
    }
}
