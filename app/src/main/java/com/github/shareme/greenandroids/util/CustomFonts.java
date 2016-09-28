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
package com.github.shareme.greenandroids.util;

/**
 * Using View Decorator
 * Usage:
 * <code>
 *   public class AppCompatDemoActivity extends AppCompatActivity implements ViewDecorator {
 *       @Override
 *       protected void onCreate(Bundle savedInstanceState) {
 *            installViewDecorator();
 *            super.onCreate(savedInstanceState);
 *            setContentView(R.layout.activity_demo);
 *       }
 *
 *      void installViewDecorator() {
 *          ViewDecoratorInstaller installer = new ViewDecoratorInstaller(this);
 *          installer.install(this);
 *      }
 *
 *      @Override
 *      public void decorate(View parent, View view, Context context, AttributeSet attrs) {
 *             if(view instanceof TextView) {
 *                   TextView textView = (TextView) view;
 *                    textView.setTypeface(getTypeFace(context, attrs));
 *             }
 *       }
 *
 *       Typeface getTypeFace(Context context, AttributeSet attrs) {
 *               Typeface typeFace = null;
 *               TypedArray ta = context.getTheme().obtainStyledAttributes(attrs, R.styleable.Font, 0, 0);
 *               int ordinal = ta.getInt(R.styleable.Font_font, -1);
 *                if(ordinal != -1) {
 *                    CustomFonts font = Font.values()[ordinal];
 *                     typeFace = font.getTypeFace(context);
 *                }
 *                ta.recycle();
 *                return typeFace;
 *         }
 *     }
 *
 * </code>
 *
 * Created by fgrott on 9/27/2016.
 */

public enum CustomFonts {

  NotCourierSans("NotCourierSans"),
  NotCourierSans_Bold("NotCourierSans_Bold"),
  UniversElse_Bold("UniversElse_Bold"),
  UniversElse_Ligth("UniversElse_Light"),
  UniversElse_Regular("UniversElse_Regular");
  private final String name;

  CustomFonts(String name){
    this.name = name;
  }
}
