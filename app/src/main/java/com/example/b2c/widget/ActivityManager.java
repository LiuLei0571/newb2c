package com.example.b2c.widget;

import android.app.Activity;


import com.example.b2c.activity.MainActivity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by chenxujie on 2015/3/19.
 */
public class ActivityManager {

    private static final String TAG = "Activity";
    private static final List<Activity> ACTIVITYS = Collections.synchronizedList(new ArrayList<Activity>());

    public static void create(Activity activity) {
        ACTIVITYS.add(activity);
    }

    public static void destroy(Activity activity) {
        ACTIVITYS.remove(activity);
    }

    public static void resume(Activity activity) {

    }

    public static void pause(Activity activity) {

    }

    public static void finishAll() {
        for (Activity activity : ACTIVITYS) {
            activity.finish();
        }
    }


    public static Activity last() {
        int size = ACTIVITYS.size();
        if (size >= 1) {
            return ACTIVITYS.get(size - 1);
        }
        return null;
    }

    public static int size() {
        return ACTIVITYS.size();
    }

    public static boolean containsHomeActivity() {
        for (Activity activity : ACTIVITYS) {
            if (activity instanceof MainActivity) {
                return true;
            }
            break;
        }
        return false;
    }
}
