/*
  Copyright 2013 Jake Wharton
  Modifications Copyright(C) 2016 Fred Grott(GrottWorkShop)

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.

 */
package com.github.shareme.greenandroids.yellowandroid;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

import static android.os.Build.VERSION_CODES.HONEYCOMB;

@SuppressWarnings("unused")
public final class MadgeFrameLayout extends FrameLayout {
  private final MadgeCanvas canvasDelegate;
  private boolean enabled;

  public MadgeFrameLayout(Context context) {
    super(context);
    canvasDelegate = new MadgeCanvas(context);
  }

  public MadgeFrameLayout(Context context, AttributeSet attrs) {
    super(context, attrs);
    canvasDelegate = new MadgeCanvas(context);
  }

  public MadgeFrameLayout(Context context, AttributeSet attrs, int defStyle) {
    super(context, attrs, defStyle);
    canvasDelegate = new MadgeCanvas(context);
  }

  /** Set the color for the pixel grid overlay. */
  public void setOverlayColor(int color) {
    canvasDelegate.setColor(color);
  }

  /** The color used by the pixel grid overlay. */
  public int getOverlayColor() {
    return canvasDelegate.getColor();
  }

  /** Set whether the scale ratio will be drawn as text on top of the pixel grid. */
  public void setOverlayRatioEnabled(boolean overlayRatioEnabled) {
    canvasDelegate.setOverlayRatioEnabled(overlayRatioEnabled);
    invalidate(); // Trigger a re-draw so we see the scale text.
  }

  /** Returns true if the scale ratio is drawing on top of the pixel grid. */
  public boolean isOverlayRatioEnabled() {
    return canvasDelegate.isOverlayRatioEnabled();
  }

  /** Set whether the pixel grid overlay is enabled. */
  public void setOverlayEnabled(boolean enabled) {
    if (enabled != this.enabled) {
      if (Build.VERSION.SDK_INT >= HONEYCOMB) {
        layerize(enabled);
      }
      this.enabled = enabled;

      if (!enabled) {
        canvasDelegate.clearCache();
      }

      invalidate(); // Trigger a re-draw so we see the grid.
    }
  }

  /** Returns true if the pixel grid overlay is enabled. */
  public boolean isOverlayEnabled() {
    return enabled;
  }

  @TargetApi(HONEYCOMB)
  private void layerize(boolean enabled) {
    if (enabled) {
      setLayerType(View.LAYER_TYPE_SOFTWARE, null);
    } else {
      setLayerType(View.LAYER_TYPE_NONE, null);
    }
  }

  @Override protected boolean drawChild(Canvas canvas, View child, long drawingTime) {
    if (!enabled) {
      return super.drawChild(canvas, child, drawingTime);
    }
    MadgeCanvas delegate = canvasDelegate;
    try {
      delegate.setDelegate(canvas);
      return super.drawChild(delegate, child, drawingTime);
    } finally {
      delegate.clearDelegate();
    }
  }

  @TargetApi(HONEYCOMB)
  @Override public boolean isHardwareAccelerated() {
    return !enabled && super.isHardwareAccelerated();
  }
}
