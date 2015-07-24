package com.bojie.speechly;

import android.os.Handler;
import android.util.Log;

/**
 * Created by bojiejiang on 7/24/15.
 */
public abstract class SpeechlyTimer implements Runnable {

    private long timeRemaining;
    private Handler mHandler;

    public SpeechlyTimer(Handler handler) {
        mHandler = handler;
        timeRemaining = 0;
    }

    public SpeechlyTimer(Handler handler, long timeRemaining) {
        mHandler = handler;
        this.timeRemaining = timeRemaining;
    }

    public void start() {
        mHandler.postDelayed(this, 1000);
    }

    public void setTimeRemaining(long timeRemaining) {
        this.timeRemaining = timeRemaining;
    }

    @Override
    public void run() {
        Log.d("Bojie", "run was called");
        updateUI(timeRemaining);
        timeRemaining -= 1000;
        if (timeRemaining >= 0) {
            mHandler.postDelayed(this, 1000);
        }
    }

    public abstract void updateUI(long timeRemaining);
}
