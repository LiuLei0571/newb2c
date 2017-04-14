package com.example.b2c.widget.util;

import com.example.b2c.helper.ThreadHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * 用途：
 * Created by milk on 16/10/12.
 * 邮箱：649444395@qq.com
 */

public class SendSmsTimerHelper {
    private SendSmsTimerHelper() {
    }

    private static SendSmsTimerHelper instance;
    private List<UpdateListener> mListeners = new ArrayList<>();
    private int left = 0;
    private TimerTask mTimerTask = null;
    private Timer timer;
    private final int time = 60;

    public static synchronized SendSmsTimerHelper getInstance() {
        if (instance == null) {
            instance = new SendSmsTimerHelper();
        }
        return instance;
    }

    public void start() {
        start(time);
    }

    public boolean start(int start) {
        if (start > 0 && left <= 0) {
            if (mTimerTask == null) {
                mTimerTask = new TimerTask() {
                    @Override
                    public void run() {
                        if (left > 0) {
                            left = left - 1;
                        }
                        dispatchUpdate();
                    }
                };
                timer = new Timer();
                timer.schedule(mTimerTask, 0, 1000);
            }
            left = start;
            dispatchUpdate();
        }
        return false;
    }


    private void dispatchUpdate() {
        for (UpdateListener listener : mListeners) {
            listener.onUpdate(left);
        }
    }

    public void addListener(UpdateListener listener) {
        mListeners.add(listener);
        dispatchUpdate();
    }

    public void removeListener(UpdateListener listener) {
        mListeners.remove(listener);
    }

    public boolean hasFinished() {
        return left > 0;
    }

    public interface UpdateListener {
        void onUpdate(int left);
    }

    public static abstract class UiUpdateListener implements UpdateListener {
        /**
         * 在ui线程更新进度
         *
         * @param left
         */
        public abstract void onUIUpdate(int left);

        public void onUpdate(final int left) {
            if (ThreadHelper.inMainThread()) {
                onUIUpdate(left);
            } else {
                ThreadHelper.postMain(new Runnable() {
                    @Override
                    public void run() {
                        onUIUpdate(left);
                    }
                });
            }
        }
    }

    public void reset() {
        left = 0;
    }

}

