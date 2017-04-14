package com.example.b2c.helper;

import android.app.Activity;

import com.example.b2c.activity.seller.HomeActivity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * activity 管理帮助类
 * Created by chenxujie on 2015/3/19.
 */
public class ActivityHelper {

    private static final List<Activity> ACTIVITYS = Collections.synchronizedList(new ArrayList<Activity>());

    public static void create(Activity activity) {
        ACTIVITYS.add(activity);
    }

    public static void destroy(Activity activity) {
        ACTIVITYS.remove(activity);
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

    /**
     * 是否为最后的activity
     *
     * @param activity
     * @return
     */
    public static boolean isLast(Activity activity) {
        return activity == last();
    }

    public static int size() {
        return ACTIVITYS.size();
    }

    /**
     * 是否包含HomeActivity
     */
    public static boolean containsHomeActivity() {
        for (Activity activity : ACTIVITYS) {
            if (activity instanceof HomeActivity) {
                return !activity.isFinishing();
            }
            break;
        }
        return false;
    }

}
