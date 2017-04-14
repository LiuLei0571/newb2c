package com.example.b2c.helper.gunlun;

import android.app.Dialog;
import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;


import com.example.b2c.R;

import java.util.ArrayList;

/**
 * Created by YCKJ-0001 on 2016/7/29.
 */
public class MyGunlunDialog {
    private static int positionOne;
    private static int positionTwo;
    private static String two;
    private static String one;
    private static AlertDialog aa;
    /**
     * 显示选择器的对话框
     * @pama title 对话框的标题
     */
    public static void showMyDiolag(Context context,String title, final ArrayList<String> oness, final ArrayList<String>twoss, final TextView tv,final TextView tv2)
    {
        final AlertDialog.Builder builder=new AlertDialog.Builder(context);
//        builder.setTitle(title);
        final View inflate = LayoutInflater.from(context).inflate(R.layout.adapter_gun_lun, null);
        TextView dialogTitle= (TextView) inflate.findViewById(R.id.dialog_title);
        final LoopView gunlunOne= (LoopView) inflate.findViewById(R.id.one);
        final LoopView gunlunTwo= (LoopView) inflate.findViewById(R.id.two);
        final LoopView gunlunTHree= (LoopView) inflate.findViewById(R.id.three);
        TextView tv_dialog_queding= (TextView) inflate.findViewById(R.id.tv_dialog_queding);
        TextView tv_dialog_quxiao= (TextView) inflate.findViewById(R.id.tv_dialog_quxiao);
        dialogTitle.setText(title);
        if (twoss==null){
            //说明是学科
            gunlunOne.setVisibility(View.GONE);
            gunlunTwo.setVisibility(View.VISIBLE);
            gunlunTHree.setVisibility(View.GONE);
            gunlunTwo.setItems(oness);
            gunlunTwo.setInitPosition(0);
            gunlunTwo.setTextSize(30);
            gunlunTwo.setListener(new OnItemSelectedListener() {

                @Override
                public void onItemSelected(int index) {
                    positionOne=index;
                    one=oness.get(index);
                }
            });

        }else {
            //说明不是学科
            gunlunOne.setItems(oness);
            gunlunOne.setInitPosition(0);
            gunlunOne.setTextSize(30);
            gunlunTwo.setItems(twoss);
            gunlunTwo.setInitPosition(0);
            gunlunTwo.setTextSize(30);
            gunlunOne.setListener(new OnItemSelectedListener() {

                @Override
                public void onItemSelected(int index) {
                    positionOne = index;
                    one = oness.get(index);

                }
            });
            gunlunTwo.setListener(new OnItemSelectedListener() {


                @Override
                public void onItemSelected(int index) {
                    positionTwo = index;
                    two = twoss.get(index);

                }
            });
        }
        tv_dialog_queding.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (one == null||two==null) {//说明没有滑动
                    if (tv2==null) {
                        //说明没有滑动，用的是两个集合的第一个元素，所以要把集合的第一个元素对应的集合放进去
                        String zhiwu = twoss.get(0);
                        tv.setText(zhiwu);
                    }else{
                        String nianji = oness.get(0);
                        String zhiwu = twoss.get(0);
                        tv.setText(zhiwu);
                        tv2.setText(nianji);
                    }

                }else{
                    if (tv2==null) {
                        //说明没有滑动，用的是两个集合的第一个元素，所以要把集合的第一个元素对应的集合放进去
                        String zhiwu = twoss.get(positionTwo);
                        tv.setText(zhiwu);
                    }else{
                        //不等于空说明有滑动,要将对应的元素从集合中取出来
                        String nianji = oness.get(positionOne);
                        String zhiwu = twoss.get(positionTwo);
                        tv.setText(zhiwu);
                        tv2.setText(nianji);
                    }

                }
                aa.dismiss();
            }
        });
        tv_dialog_quxiao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                aa.dismiss();
            }
        });
        builder.setView(inflate);
        aa= builder.show();
    }
}
