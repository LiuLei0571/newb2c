package com.example.b2c.activity.common;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

import com.example.b2c.R;
import com.example.b2c.activity.seller.ChooseImageActivity;
import com.example.b2c.adapter.common.ShowBucketImageAdapter;
import com.example.b2c.config.Configs;
import com.example.b2c.helper.ImageFetcher;
import com.example.b2c.helper.ToastHelper;
import com.example.b2c.net.response.ImageBucket;
import com.example.b2c.net.response.common.ImageItem;
import com.example.b2c.widget.CommonListPopupWindow;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * 用途：图片选择
 * 作者：Created by john on 2017/2/8.
 * 邮箱：liulei2@aixuedai.com
 */


public class ImageBucketChooseActivity extends TempBaseActivity implements AdapterView.OnItemClickListener {
    @Bind(R.id.grid)
    GridView mGridView;
    @Bind(R.id.tv_file)
    TextView mTvFile;
    @Bind(R.id.btn_submit)
    TextView mBtnSubmit;
    private ImageFetcher mHelper;
    private List<ImageBucket> mDataList = new ArrayList<ImageBucket>();
    private List<String> mBucketName = new ArrayList<>();
    private List<ImageItem> mImageList = new ArrayList<>();
    private List<HashMap<String, String>> value = new ArrayList<>();
    private HashMap<String, String> selected = new HashMap<>();
    private ShowBucketImageAdapter mAdapter;
    private HashMap<String, ImageItem> selectedImgs = new HashMap<String, ImageItem>();
    private List<ImageItem> mItemData = new ArrayList<>();
    private String fromType;

    @Override
    public int getRootId() {
        return R.layout.activity_bucket_choose;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initData();
        initView();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    public void initData() {
        fromType = getIntent().getStringExtra("release");
        mHelper = ImageFetcher.getInstance(this);
        mDataList.clear();
        mDataList = mHelper.getImagesBucketList(false);
        mBtnSubmit.setText(mTranslatesString.getCommon_done());
        mAdapter = new ShowBucketImageAdapter(this) {
            @Override
            public void onClickItem(ImageItem mItem) {
                if (mItem.isSelected()) {
                    mItem.setSelected(false);
                    selectedImgs.remove(mItem.imageId);
                } else {
                    selectedImgs.put(mItem.imageId, mItem);
                    mItem.setSelected(true);
                }
                if (selectedImgs.size() > 0 && selectedImgs.size() < 6) {
                    mBtnSubmit.setText(mTranslatesString.getCommon_done() + " (" + selectedImgs.size() + "/" + Configs.MAX_IMAGE_SIZE + ")");
                    mBtnSubmit.setClickable(true);

                } else if (selectedImgs.size() == 0) {
                    mBtnSubmit.setText(mTranslatesString.getCommon_done());
                    mBtnSubmit.setClickable(false);
                } else {
                    ToastHelper.makeErrorToast(mTranslatesString.getCommon_toastselerctphoto());
                }
                notifyDataSetChanged();
            }
        };

        for (ImageBucket bucket : mDataList) {
            HashMap map = new HashMap();
            map.put(bucket.count + "", bucket.bucketName);
            value.add(map);
            mBucketName.add(bucket.bucketName);
            for (int i = 0; i < bucket.imageList.size(); i++) {
                bucket.imageList.get(i).setSelected(false);
            }
        }

        mAdapter.addAll(mDataList.get(0).imageList);
        mTvFile.setText(mDataList.get(0).bucketName);
        mGridView.setAdapter(mAdapter);
    }

    private void initView() {
        setTitle(mTranslatesString.getCommon_selerctphoto());

    }

    @OnClick({R.id.tv_file, R.id.btn_submit})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_file:
                CommonListPopupWindow pop = new CommonListPopupWindow(this, value, selected, new CommonListPopupWindow.CommonCallBack() {
                    @Override
                    public void onClick(int selectItem, String value) {
                        for (ImageBucket bucket : mDataList) {
                            if (value.equals(bucket.bucketName)) {
                                mAdapter.clear();
                                mTvFile.setText(bucket.bucketName);
                                mAdapter.addAll(bucket.imageList);
                                mAdapter.notifyDataSetChanged();
                                break;
                            }
                        }
                    }
                });
                pop.show(view);
                break;
            case R.id.btn_submit:
                for (ImageItem item : selectedImgs.values()) {
                    mItemData.add(item);
                }
                if (fromType.equals(Configs.RELEASE.PRODUCT.Bucket)) {
                    Intent intent = new Intent(this, ChooseImageActivity.class);
                    intent.putExtra("mListData", (Serializable) mItemData);
                    startActivity(intent);
                } else {
                    Intent intent = new Intent();
                    intent.putExtra("mListData", (Serializable) mItemData);
                    this.setResult(1002, intent);
                }
                finish();
                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }
}
