// Copyright 2009 Google Inc.
//
// This program is free software: you can redistribute it and/or modify
// it under the terms of the GNU General Public License as published by
// the Free Software Foundation, either version 3 of the License, or
// (at your option) any later version.
//
// This program is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
// GNU General Public License for more details.
//
// You should have received a copy of the GNU General Public License
// along with this program.  If not, see <http://www.gnu.org/licenses/>.

package com.google.code.twisty;

import java.io.File;

import org.brickshadow.roboglk.Glk;
import org.brickshadow.roboglk.GlkEventQueue;
import org.brickshadow.roboglk.GlkSChannel;
import org.brickshadow.roboglk.GlkWinType;
import org.brickshadow.roboglk.GlkWindow;
import org.brickshadow.roboglk.window.RoboTextBufferWindow;
import org.brickshadow.roboglk.window.StandardTextBufferIO;
import org.brickshadow.roboglk.window.TextBufferView;

import android.app.Activity;
import android.os.Message;
import android.util.Log;

public class TwistyGlk implements Glk {

    private final GlkEventQueue eventQueue;
    
    private final Activity activity;
    
    private RoboTextBufferWindow mainWin;
    private final TextBufferView tv;
    
    public TwistyGlk(Activity activity, TextBufferView tv) {
        this.activity = activity;
        this.tv = tv;
        eventQueue = new GlkEventQueue();
    }
    
    @Override
    public void cancelTimer() {}

    @Override
    public void clearStyleHint(int wintype, int styl, int hint) {}

    @Override
    public GlkSChannel createChannel() {
        return null;
    }

    @Override
    public void destroyChannel(GlkSChannel schan) {}

    @Override
    public int gestalt(int sel, int val, int[] arr) {
        return 0;
    }

    @Override
    public boolean getImageInfo(int num, int[] dim) {
        return false;
    }

    @Override
    public File namedFile(String filename, int usage) {
        return null;
    }

    @Override
    public void poll(int[] event) {
        Message msg = eventQueue.poll();
        GlkEventQueue.translateEvent(msg, event);
    }

    @Override
    public File promptFile(int usage, int fmode) {
        return null;
    }

    @Override
    public void requestTimer(int millisecs) {}

    @Override
    public void select(int[] event) {
        Message msg = eventQueue.select();
        GlkEventQueue.translateEvent(msg, event);
    }

    @Override
    public void setSoundLoadHint(int num, boolean flag) {}

    @Override
    public void setStyleHint(int wintype, int styl, int hint, int val) {}

    @Override
    public void windowClose(GlkWindow win) {
        if (win != mainWin) {
            Log.e("roboglk", "window_close: invalid id");
            return;
        }
        
        mainWin = null;
    }

    @Override
    public void windowOpen(GlkWindow splitwin, int method, int size,
            int wintype, int id, GlkWindow[] wins) {

        if (splitwin != null && mainWin != null) {
            return;
        }
        if (wintype != GlkWinType.TextBuffer) {
            return;
        }

        
        mainWin = new RoboTextBufferWindow(
                activity,
                eventQueue,
                new StandardTextBufferIO(tv),
                //new TwistyTextBufferIO(tv), /* styled text = slow input */
                id);
        wins[0] = mainWin;
    }

}