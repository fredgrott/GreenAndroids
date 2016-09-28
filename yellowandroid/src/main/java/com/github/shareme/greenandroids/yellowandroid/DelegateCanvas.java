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
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.DrawFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Picture;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Region;
import android.support.annotation.NonNull;

import static android.os.Build.VERSION_CODES.HONEYCOMB;
import static android.os.Build.VERSION_CODES.ICE_CREAM_SANDWICH;

/** A {@link Canvas} which delegates all method calls to another {@link Canvas}. */
class DelegateCanvas extends Canvas {
  private final boolean isHardwareAccelerated;

  private Canvas delegate;

  /**
   * Create a new delegating {@link Canvas} instance..
   * <p>
   * You <strong>must</strong> call {@link #setDelegate(Canvas)} before any calls to this object
   * are made.
   * <p>
   * Because the superclass constructor immediately calls {@link #isHardwareAccelerated()} this
   * value must be provided up front.
   */
  public DelegateCanvas(boolean isHardwareAccelerated) {
    this.isHardwareAccelerated = isHardwareAccelerated;
  }

  /** Set the {@link Canvas} to which all calls are delegated. */
  public void setDelegate(Canvas delegate) {
    this.delegate = delegate;
  }

  /**
   * Clear the the delegate {@link Canvas}. Any subsequent calls to this object will throw an
   * exception unless you first call {@link #setDelegate(Canvas)} again.
   */
  public void clearDelegate() {
    delegate = null;
  }

  @TargetApi(HONEYCOMB) //
  @Override public final boolean isHardwareAccelerated() {
    return isHardwareAccelerated;
  }

  @Override public void setBitmap(Bitmap bitmap) {
    delegate.setBitmap(bitmap);
  }

  @Override public boolean isOpaque() {
    return delegate.isOpaque();
  }

  @Override public int getWidth() {
    return delegate.getWidth();
  }

  @Override public int getHeight() {
    return delegate.getHeight();
  }

  @Override public int getDensity() {
    return delegate.getDensity();
  }

  @Override public void setDensity(int density) {
    delegate.setDensity(density);
  }

  @TargetApi(ICE_CREAM_SANDWICH) //
  @Override public int getMaximumBitmapWidth() {
    return delegate.getMaximumBitmapWidth();
  }

  @TargetApi(ICE_CREAM_SANDWICH) //
  @Override public int getMaximumBitmapHeight() {
    return delegate.getMaximumBitmapHeight();
  }

  @Override public int save() {
    return delegate.save();
  }

  @Override public int save(int saveFlags) {
    return delegate.save(saveFlags);
  }

  @Override public int saveLayer(RectF bounds, Paint paint, int saveFlags) {
    return delegate.saveLayer(bounds, paint, saveFlags);
  }

  @Override public int saveLayer(float left, float top, float right, float bottom, Paint paint,
      int saveFlags) {
    return delegate.saveLayer(left, top, right, bottom, paint, saveFlags);
  }

  @Override public int saveLayerAlpha(RectF bounds, int alpha, int saveFlags) {
    return delegate.saveLayerAlpha(bounds, alpha, saveFlags);
  }

  @Override public int saveLayerAlpha(float left, float top, float right, float bottom, int alpha,
      int saveFlags) {
    return delegate.saveLayerAlpha(left, top, right, bottom, alpha, saveFlags);
  }

  @Override public void restore() {
    delegate.restore();
  }

  @Override public int getSaveCount() {
    return delegate.getSaveCount();
  }

  @Override public void restoreToCount(int saveCount) {
    delegate.restoreToCount(saveCount);
  }

  @Override public void translate(float dx, float dy) {
    delegate.translate(dx, dy);
  }

  @Override public void scale(float sx, float sy) {
    delegate.scale(sx, sy);
  }

  @Override public void rotate(float degrees) {
    delegate.rotate(degrees);
  }

  @Override public void skew(float sx, float sy) {
    delegate.skew(sx, sy);
  }

  @Override public void concat(Matrix matrix) {
    delegate.concat(matrix);
  }

  @Override public void setMatrix(Matrix matrix) {
    delegate.setMatrix(matrix);
  }

  @SuppressWarnings("deprecation") //
  @Override public void getMatrix(@NonNull Matrix ctm) {
    delegate.getMatrix(ctm);
  }

  @Override public boolean clipRect(@NonNull RectF rect, @NonNull Region.Op op) {
    return delegate.clipRect(rect, op);
  }

  @Override public boolean clipRect(@NonNull Rect rect, @NonNull Region.Op op) {
    return delegate.clipRect(rect, op);
  }

  @Override public boolean clipRect(@NonNull RectF rect) {
    return delegate.clipRect(rect);
  }

  @Override public boolean clipRect(@NonNull Rect rect) {
    return delegate.clipRect(rect);
  }

  @Override
  public boolean clipRect(float left, float top, float right, float bottom, @NonNull Region.Op op) {
    return delegate.clipRect(left, top, right, bottom, op);
  }

  @Override public boolean clipRect(float left, float top, float right, float bottom) {
    return delegate.clipRect(left, top, right, bottom);
  }

  @Override public boolean clipRect(int left, int top, int right, int bottom) {
    return delegate.clipRect(left, top, right, bottom);
  }

  @Override public boolean clipPath(@NonNull Path path, @NonNull Region.Op op) {
    return delegate.clipPath(path, op);
  }

  @Override public boolean clipPath(@NonNull Path path) {
    return delegate.clipPath(path);
  }

  @Override public boolean clipRegion(@NonNull Region region, @NonNull Region.Op op) {
    return delegate.clipRegion(region, op);
  }

  @Override public boolean clipRegion(@NonNull Region region) {
    return delegate.clipRegion(region);
  }

  @Override public DrawFilter getDrawFilter() {
    return delegate.getDrawFilter();
  }

  @Override public void setDrawFilter(DrawFilter filter) {
    delegate.setDrawFilter(filter);
  }

  @Override public boolean quickReject(@NonNull RectF rect, @NonNull EdgeType type) {
    return delegate.quickReject(rect, type);
  }

  @Override public boolean quickReject(@NonNull Path path, @NonNull EdgeType type) {
    return delegate.quickReject(path, type);
  }

  @Override
  public boolean quickReject(float left, float top, float right, float bottom, @NonNull EdgeType type) {
    return delegate.quickReject(left, top, right, bottom, type);
  }

  @Override public boolean getClipBounds(Rect bounds) {
    return delegate.getClipBounds(bounds);
  }

  @Override public void drawRGB(int r, int g, int b) {
    delegate.drawRGB(r, g, b);
  }

  @Override public void drawARGB(int a, int r, int g, int b) {
    delegate.drawARGB(a, r, g, b);
  }

  @Override public void drawColor(int color) {
    delegate.drawColor(color);
  }

  @Override public void drawColor(int color, @NonNull PorterDuff.Mode mode) {
    delegate.drawColor(color, mode);
  }

  @Override public void drawPaint(@NonNull Paint paint) {
    delegate.drawPaint(paint);
  }

  @Override public void drawPoints(float[] pts, int offset, int count, @NonNull Paint paint) {
    delegate.drawPoints(pts, offset, count, paint);
  }

  @Override public void drawPoints(@NonNull float[] pts, @NonNull Paint paint) {
    delegate.drawPoints(pts, paint);
  }

  @Override public void drawPoint(float x, float y, @NonNull Paint paint) {
    delegate.drawPoint(x, y, paint);
  }

  @Override
  public void drawLine(float startX, float startY, float stopX, float stopY, @NonNull Paint paint) {
    delegate.drawLine(startX, startY, stopX, stopY, paint);
  }

  @Override public void drawLines(@NonNull float[] pts, int offset, int count, @NonNull Paint paint) {
    delegate.drawLines(pts, offset, count, paint);
  }

  @Override public void drawLines(@NonNull float[] pts, @NonNull Paint paint) {
    delegate.drawLines(pts, paint);
  }

  @Override public void drawRect(@NonNull RectF rect, @NonNull Paint paint) {
    delegate.drawRect(rect, paint);
  }

  @Override public void drawRect(@NonNull Rect r, @NonNull Paint paint) {
    delegate.drawRect(r, paint);
  }

  @Override public void drawRect(float left, float top, float right, float bottom, @NonNull Paint paint) {
    delegate.drawRect(left, top, right, bottom, paint);
  }

  @Override public void drawOval(@NonNull RectF oval, @NonNull Paint paint) {
    delegate.drawOval(oval, paint);
  }

  @Override public void drawCircle(float cx, float cy, float radius, @NonNull Paint paint) {
    delegate.drawCircle(cx, cy, radius, paint);
  }

  @Override public void drawArc(@NonNull RectF oval, float startAngle, float sweepAngle, boolean useCenter,
                                @NonNull Paint paint) {
    delegate.drawArc(oval, startAngle, sweepAngle, useCenter, paint);
  }

  @Override public void drawRoundRect(@NonNull RectF rect, float rx, float ry, @NonNull Paint paint) {
    delegate.drawRoundRect(rect, rx, ry, paint);
  }

  @Override public void drawPath(@NonNull Path path, @NonNull Paint paint) {
    delegate.drawPath(path, paint);
  }

  @Override public void drawBitmap(@NonNull Bitmap bitmap, float left, float top, Paint paint) {
    delegate.drawBitmap(bitmap, left, top, paint);
  }

  @Override public void drawBitmap(@NonNull Bitmap bitmap, Rect src, @NonNull RectF dst, Paint paint) {
    delegate.drawBitmap(bitmap, src, dst, paint);
  }

  @Override public void drawBitmap(@NonNull Bitmap bitmap, Rect src, @NonNull Rect dst, Paint paint) {
    delegate.drawBitmap(bitmap, src, dst, paint);
  }

  @Override
  public void drawBitmap(@NonNull int[] colors, int offset, int stride, float x, float y, int width,
                         int height, boolean hasAlpha, Paint paint) {
    delegate.drawBitmap(colors, offset, stride, x, y, width, height, hasAlpha, paint);
  }

  @Override
  public void drawBitmap(@NonNull int[] colors, int offset, int stride, int x, int y, int width, int height,
                         boolean hasAlpha, Paint paint) {
    delegate.drawBitmap(colors, offset, stride, x, y, width, height, hasAlpha, paint);
  }

  @Override public void drawBitmap(@NonNull Bitmap bitmap, @NonNull Matrix matrix, Paint paint) {
    delegate.drawBitmap(bitmap, matrix, paint);
  }

  @Override public void drawBitmapMesh(@NonNull Bitmap bitmap, int meshWidth, int meshHeight, float[] verts,
                                       int vertOffset, int[] colors, int colorOffset, Paint paint) {
    delegate.drawBitmapMesh(bitmap, meshWidth, meshHeight, verts, vertOffset, colors, colorOffset,
        paint);
  }

  @Override
  public void drawVertices(@NonNull VertexMode mode, int vertexCount, @NonNull float[] verts, int vertOffset,
                           float[] texs, int texOffset, int[] colors, int colorOffset, short[] indices, int indexOffset,
                           int indexCount, @NonNull Paint paint) {
    delegate.drawVertices(mode, vertexCount, verts, vertOffset, texs, texOffset, colors,
        colorOffset, indices, indexOffset, indexCount, paint);
  }

  @Override public void drawText(@NonNull char[] text, int index, int count, float x, float y, Paint paint) {
    delegate.drawText(text, index, count, x, y, paint);
  }

  @Override public void drawText(@NonNull String text, float x, float y, @NonNull Paint paint) {
    delegate.drawText(text, x, y, paint);
  }

  @Override public void drawText(@NonNull String text, int start, int end, float x, float y, Paint paint) {
    delegate.drawText(text, start, end, x, y, paint);
  }

  @Override
  public void drawText(@NonNull CharSequence text, int start, int end, float x, float y, @NonNull Paint paint) {
    delegate.drawText(text, start, end, x, y, paint);
  }

  @SuppressWarnings("deprecation") //
  @Override public void drawPosText(@NonNull char[] text, int index, int count, @NonNull float[] pos, Paint paint) {
    delegate.drawPosText(text, index, count, pos, paint);
  }

  @SuppressWarnings("deprecation") //
  @Override public void drawPosText(@NonNull String text, @NonNull float[] pos, @NonNull Paint paint) {
    delegate.drawPosText(text, pos, paint);
  }

  @Override public void drawTextOnPath(@NonNull char[] text, int index, int count, @NonNull Path path, float hOffset,
                                       float vOffset, @NonNull Paint paint) {
    delegate.drawTextOnPath(text, index, count, path, hOffset, vOffset, paint);
  }

  @Override
  public void drawTextOnPath(@NonNull String text, @NonNull Path path, float hOffset, float vOffset, @NonNull Paint paint) {
    delegate.drawTextOnPath(text, path, hOffset, vOffset, paint);
  }

  @Override public void drawPicture(@NonNull Picture picture) {
    delegate.drawPicture(picture);
  }

  @Override public void drawPicture(@NonNull Picture picture, @NonNull RectF dst) {
    delegate.drawPicture(picture, dst);
  }

  @Override public void drawPicture(@NonNull Picture picture, @NonNull Rect dst) {
    delegate.drawPicture(picture, dst);
  }
}
