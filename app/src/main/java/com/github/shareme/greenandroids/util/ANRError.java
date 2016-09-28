/*
 The MIT License (MIT)

Copyright (c) 2015 Salomon BRYS
Modifications Copyright(C) 2016 Fred Grott(GrottWorkShop)

Permission is hereby granted, free of charge, to any person obtaining a copy of
this software and associated documentation files (the "Software"), to deal in
the Software without restriction, including without limitation the rights to
use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of
the Software, and to permit persons to whom the Software is furnished to do so,
subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS
FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER
IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN
CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.

 */
package com.github.shareme.greenandroids.util;

import android.os.Looper;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by fgrott on 9/23/2016.
 */
@SuppressWarnings("unused")
public class ANRError extends Error {

  private static class $ {
    private final String _name;
    private final StackTraceElement[] _stackTrace;

    private class _Thread extends Throwable {
      private _Thread(_Thread other) {
        super(_name, other);
      }

      @Override
      public Throwable fillInStackTrace() {
        setStackTrace(_stackTrace);
        return this;
      }
    }

    private $(String name, StackTraceElement[] stackTrace) {
      _name = name;
      _stackTrace = stackTrace;
    }
  }

  private static final long serialVersionUID = 1L;

  private final Map<Thread, StackTraceElement[]> _stackTraces;

  private ANRError($._Thread st, Map<Thread, StackTraceElement[]> stackTraces) {
    super("Application Not Responding", st);
    _stackTraces = stackTraces;
  }

  /**
   * @return all the reported threads and stack traces.
   */
  public Map<Thread, StackTraceElement[]> getStackTraces() {
    return _stackTraces;
  }

  @Override
  public Throwable fillInStackTrace() {
    setStackTrace(new StackTraceElement[] {});
    return this;
  }

  static ANRError New(String prefix, boolean logThreadsWithoutStackTrace) {
    final Thread mainThread = Looper.getMainLooper().getThread();

    final Map<Thread, StackTraceElement[]> stackTraces = new TreeMap<>(new Comparator<Thread>() {
      @Override public int compare(Thread lhs, Thread rhs) {
        if (lhs == rhs)
          return 0;
        if (lhs == mainThread)
          return 1;
        if (rhs == mainThread)
          return -1;
        return rhs.getName().compareTo(lhs.getName());
      }
    });

    for (Map.Entry<Thread, StackTraceElement[]> entry : Thread.getAllStackTraces().entrySet())
      if (
              entry.getKey() == mainThread
                      ||  (
                      entry.getKey().getName().startsWith(prefix)
                              &&  (
                              logThreadsWithoutStackTrace
                                      ||
                                      entry.getValue().length > 0
                      )
              )
              )
        stackTraces.put(entry.getKey(), entry.getValue());

    $._Thread tst = null;
    for (Map.Entry<Thread, StackTraceElement[]> entry : stackTraces.entrySet())
      tst = new $(entry.getKey().getName(), entry.getValue()).new _Thread(tst);

    return new ANRError(tst, stackTraces);
  }

  static ANRError NewMainOnly() {
    final Thread mainThread = Looper.getMainLooper().getThread();
    final StackTraceElement[] mainStackTrace = mainThread.getStackTrace();

    final HashMap<Thread, StackTraceElement[]> stackTraces = new HashMap<>(1);
    stackTraces.put(mainThread, mainStackTrace);

    return new ANRError(new $(mainThread.getName(), mainStackTrace).new _Thread(null), stackTraces);
  }

}
