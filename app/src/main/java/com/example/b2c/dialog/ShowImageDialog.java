package com.example.b2c.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.b2c.R;
import com.example.b2c.activity.common.TempBaseActivity;
import com.example.b2c.widget.util.DialogUtil;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;

import uk.co.senab.photoview.PhotoView;

/**
 * Created by Liu on 2016/4/12.
 * TODO:
 */
public class ShowImageDialog extends Dialog implements View.OnClickListener{
    private Context mContext;
    private boolean isShowButton;
    private int id;
    private String url;

    public ShowImageDialog(TempBaseActivity acitivity, int id, String url) {
        super(acitivity, R.style.FullDialog);
        mContext=getContext();
        this.id = id;
        this.url = url;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
    }

    public void init() {
        View view = LayoutInflater.from(mContext).inflate(R.layout.dialog_show_image,null);
        setContentView(view);
        PhotoView mIvShow=(PhotoView)view.findViewById(R.id.photo_view);
        ImageView mIvClose=(ImageView)view.findViewById(R.id.dialog_show_close);
        mIvClose.setOnClickListener(this);
        DisplayImageOptions.Builder options=new DisplayImageOptions.Builder();
        options.cacheOnDisk(true);
        options.cacheInMemory(true);
        options.displayer(new FadeInBitmapDisplayer(500, true, true, true));
        ImageLoader.getInstance().displayImage(url,mIvShow,options.build());
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.dialog_show_close:
                DialogUtil.dismiss(this);
                break;
        }
    }
}
