package com.example.b2c.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.ImageView;

import com.example.b2c.activity.GoodsDetailActivity;
import com.example.b2c.activity.HomeIntentAdWebviewActivity;
import com.example.b2c.net.util.Logs;

import java.util.ArrayList;

public class BannerAdapter extends PagerAdapter {
    private ArrayList<ImageView> viewlist;
    private Context context;

    public BannerAdapter(ArrayList<ImageView> viewList) {
        super();
        this.viewlist = viewList;
    }

    public BannerAdapter(ArrayList<ImageView> list, Context context) {
        super();
        this.viewlist = list;
        this.context = context;
    }


    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return Integer.MAX_VALUE;
    }

    @Override
    public boolean isViewFromObject(View arg0, Object arg1) {
        // TODO Auto-generated method stub
        return arg0 == arg1;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        // TODO Auto-generated method stub
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        // TODO Auto-generated method stub
        //对ViewPager页号求模取出View列表中要显示的项
        position %= viewlist.size();
        if (position < 0) {
            position = viewlist.size() + position;
        }
        Logs.i("position", String.valueOf(position));
        ImageView view = viewlist.get(position);
        //如果View已经在之前添加到了一个父组件，则必须先remove，否则会抛出IllegalStateException。
        ViewParent vp = view.getParent();
        if (vp != null) {
            ViewGroup parent = (ViewGroup) vp;
            parent.removeView(view);
        }

        container.addView(view);
        //add listeners here if necessary
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO Auto-generated method stub
                String picLink = (String) view.getTag();
                Logs.d("picLink", picLink);
                int sampleId = getSampleId(picLink);
                if (sampleId != 0) {
                    Intent intent = new Intent(context, GoodsDetailActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putInt("sampleId", sampleId);
                    intent.putExtras(bundle);
                    context.startActivity(intent);
                } else {
                    Intent intent = new Intent(context, HomeIntentAdWebviewActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("url", picLink);
                    intent.putExtras(bundle);
                    context.startActivity(intent);
                }
            }

            private int getSampleId(String picLink) {
                // TODO Auto-generated method stub
                String picLinkP = picLink;
                String id = null;
                String idStr = null;
                if (picLinkP != null) {
                    if (picLinkP.lastIndexOf("/") != picLinkP.length() - 1) {
                        idStr = picLinkP.substring(picLinkP.lastIndexOf("/") + 1);
                        if (idStr.indexOf(".") == -1) {
                            id = idStr;
                        }
                    }
                }
                if (id == null) {
                    return 0;
                } else {
                    return Integer.parseInt(id);
                }

            }

        });
        return view;
    }

}
