package com.example.b2c.activity.seller;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;

import com.example.b2c.R;
import com.example.b2c.activity.common.TempBaseActivity;
import com.example.b2c.activity.common.UpdateImageActivity;
import com.example.b2c.adapter.common.ImagePublishAdapter;
import com.example.b2c.config.ConstantS;
import com.example.b2c.dialog.PickImageDialog;
import com.example.b2c.event.model.DescriptionListEvent;
import com.example.b2c.helper.EventHelper;
import com.example.b2c.helper.ToastHelper;
import com.example.b2c.helper.UserHelper;
import com.example.b2c.net.listener.UploadListener;
import com.example.b2c.net.response.common.ImageItem;
import com.example.b2c.net.response.seller.DescriptionModule;
import com.example.b2c.widget.util.Utils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * 用途：
 * 作者：Created by john on 2017/2/7.
 * 邮箱：liulei2@aixuedai.com
 */

public class DescriptionActivity extends TempBaseActivity implements AdapterView.OnItemClickListener {
    private static ImageListCallBack mListener;
    private PickImageDialog mPickImageDialog;
    @Bind(R.id.etAdvice)
    EditText mEtAdvice;
    @Bind(R.id.grid)
    GridView mGrid;
    private ImagePublishAdapter mAdapter;
    private List<ImageItem> mListData = new ArrayList<>();
    private String type = "change";
    private List<ImageItem> mSpData = new ArrayList<>();
    private List<ImageItem> testData = new ArrayList<>();

    private DescriptionModule mData;

    @Override
    public int getRootId() {
        return R.layout.activity_choose_image;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(mTranslatesString.getGoods_message());
        initView();
        initData();
    }


    public void initView() {
        mEtAdvice.setHint(mTranslatesString.getCommon_inputdescription());
        addText(mTranslatesString.getCommon_sure(), new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String advice = Utils.cutNull(mEtAdvice.getText().toString());
                final List<String> mListUrl = new ArrayList<String>();
                showLoading();
                for (ImageItem mUrl : mListData) {
                    mListUrl.add(mUrl.getUrl());
                }
                sellerRdm.SellerUploadFile(ConstantS.BASE_PIC_URL + "remoting/rest/mobile/security/mobileUploadSampleDetailPic.htm", UserHelper.isSellerLogin(), UserHelper.getSellerID(), UserHelper.getSellerToken(), mListUrl, "goodsImage");
                sellerRdm.uploadListener = new UploadListener() {
                    @Override
                    public void onSuccess(List<String> path) {
                        EventHelper.post(new DescriptionListEvent(path, advice, mListUrl));
                        finish();
                    }

                    @Override
                    public void onError(int errorNO, String errorInfo) {
                        ToastHelper.makeErrorToast(errorInfo);
                    }

                    @Override
                    public void onFinish() {
                        dissLoading();
                    }

                    @Override
                    public void onLose() {

                    }
                };
            }
        });
    }

    public void initData() {
//        mListData = (List<ImageItem>) getIntent().getSerializableExtra("mListData");
        mData = (DescriptionModule) getIntent().getSerializableExtra("mData");
        if (mData != null) {
            mEtAdvice.setText(Utils.cutNull(mData.getSampleDetailMessage()));
            for (String url : mData.getLocalUrl()) {
                ImageItem itemDmata = new ImageItem();
                itemDmata.setUrl(url);
                mListData.add(itemDmata);
            }
        }
        mAdapter = new ImagePublishAdapter(this, mListData);
        mGrid.setSelector(new ColorDrawable(Color.TRANSPARENT));
        mGrid.setAdapter(mAdapter);
        mGrid.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
        if (position == getDataSize()) {
            mPickImageDialog = new PickImageDialog(this, position, type) {
                @Override
                protected void onImageUpLoad(int id, String url, String url1) {
                    ImageItem module = new ImageItem();
                    module.setUrl(url);
                    Log.d("mSpData", "onActivityResult: " + url);

                    mListData.add(module);
                    mAdapter.notifyDataSetChanged();
                }
            };
            mPickImageDialog.show();
        } else {
            Intent intent = new Intent(this, UpdateImageActivity.class);
            intent.putExtra("mListData", (Serializable) mListData);
            intent.putExtra("imageMark", position);
            startActivityForResult(intent, 1001);
        }
    }

    private int getDataSize() {
        return mListData == null ? 0 : mListData.size();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1001) {
            if (data != null) {
                if (mListData == null) {
                    mListData = new ArrayList<>();
                }
                mListData = (List<ImageItem>) data.getSerializableExtra("mListData");
                mAdapter.setmDataList(mListData);
                mAdapter.notifyDataSetChanged();
            }
        } else if (requestCode == 1002) {
            if (data != null) {
                if (mSpData == null) {
                    mSpData = new ArrayList<>();
                }
                mSpData = (List<ImageItem>) data.getSerializableExtra("mListData");
                Log.d("mSpData", "onActivityResult: " + mSpData.size());
                mListData.addAll(mSpData);
                mAdapter.setmDataList(mListData);
                mAdapter.notifyDataSetChanged();
            }
        }
    }

    public static void getInstance(ReleaseProductActivity mContext, DescriptionModule mDescriptionModule, ImageListCallBack mCallBack) {
        Intent intent = new Intent();
        mListener = mCallBack;
        intent.setClass(mContext, DescriptionActivity.class);
        intent.putExtra("mData", (Serializable) mDescriptionModule);
        mContext.startActivity(intent);
    }

    public interface ImageListCallBack {
        void onImageList(List<ImageItem> mList);
    }
}
