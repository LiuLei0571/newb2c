package com.example.b2c.helper;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.provider.Settings;

import com.example.b2c.R;
import com.example.b2c.activity.LaunchActivity;

/**
 * 用途：
 * Created by milk on 16/10/25.
 * 邮箱：649444395@qq.com
 */

public class SysmHelper {
    protected static Dialog dialogsupport;
    public  static boolean validateInternet(Context context) {
        ConnectivityManager manager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (manager == null) {
            openWirelessSet(context);
            return false;
        } else {
            NetworkInfo[] info = manager.getAllNetworkInfo();
            if (info != null) {
                for (int i = 0; i < info.length; i++) {
                    if (info[i].getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
                }
            }
        }
        openWirelessSet(context);
        return false;
    }

    public static void openWirelessSet(final Context context) {
        if (null != dialogsupport && dialogsupport.isShowing()) {
            dialogsupport.cancel();
        }
        dialogsupport = new AlertDialog.Builder(context)
                .setTitle(R.string.str_prompt)
                .setMessage(context.getString(R.string.str_no_connection))
                .setPositiveButton(R.string.str_yes,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog,
                                                int which) {
                                dialog.cancel();
                                Intent intent = new Intent(
                                        Settings.ACTION_WIRELESS_SETTINGS);
                                context.startActivity(intent);
                            }
                        })
                .setNegativeButton(R.string.str_no,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog,
                                                int whichButton) {
                                dialog.cancel();
                            }
                        }).create();
        dialogsupport.setCanceledOnTouchOutside(false);
        dialogsupport.show();
    }
    public static void restartApp(Context context, int time) {
        Intent intent = new Intent(context, LaunchActivity.class);
        PendingIntent restartIntent = PendingIntent.getActivity(
                context, 0, intent, 0);
        AlarmManager mgr = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        mgr.set(AlarmManager.RTC, System.currentTimeMillis() + time, restartIntent);
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        android.os.Process.killProcess(android.os.Process.myPid());
    }
}
