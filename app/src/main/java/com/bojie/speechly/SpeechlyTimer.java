package com.bojie.speechly;

import android.content.Context;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import java.util.concurrent.TimeUnit;

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
        } else {
            onTimerStopped();
        }
    }


    public static long convertToMilliseconds(Context context, String timeInput) {

        try {
            int minutes = Integer.parseInt(timeInput.substring(0, 2));
            int seconds = Integer.parseInt(timeInput.substring(3, timeInput.length()));
            long milliseconds = (minutes * 60 + seconds) * 1000;
            Toast.makeText(context, "Timer set to " + minutes + " minutes " + seconds + " seconds " + milliseconds + " milliseconds", Toast.LENGTH_LONG).show();
            return milliseconds;
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    public static String convertMillisecondsToString(long timeInput) {
        String time = String.format("%02d:%02d",
                TimeUnit.MILLISECONDS.toMinutes(timeInput),
                TimeUnit.MILLISECONDS.toSeconds(timeInput) -
                        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(timeInput))
        );
        return time;
    }

    public static boolean isValidInput(String timeInput) {
        if (timeInput == null || timeInput.isEmpty()) {
            return false;
        }
        String trimmedInput = timeInput.trim();
        if (trimmedInput.length() == 5 && trimmedInput.indexOf(':') == 2) {
            return true;
        } else {
            return false;
        }
    }

    public abstract void updateUI(long timeRemaining);

    public abstract void onTimerStopped();
}
