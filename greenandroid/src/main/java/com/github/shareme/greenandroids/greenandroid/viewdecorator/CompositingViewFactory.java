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
import android.view.View;

public class CompositingViewFactory implements ViewFactory {
    private ViewFactory[] factories;

    public CompositingViewFactory(ViewFactory... factories) {
        this.factories = factories;
    }

    @Override
    public View createView(View parent, String name, AttributeSet attrs) {
        View view = null;

        for(int i = 0; i < factories.length && view == null; ++i) {
            view = factories[i].createView(parent, name, attrs);
        }

        return view;
    }
}
