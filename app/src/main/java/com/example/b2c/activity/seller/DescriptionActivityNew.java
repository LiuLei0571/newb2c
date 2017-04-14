package com.example.b2c.activity.seller;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.Editable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ImageSpan;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.b2c.R;
import com.example.b2c.activity.common.TempBaseActivity;
import com.example.b2c.dialog.PickImageDialog;
import com.example.b2c.widget.SimpleTextWatcher;
import com.example.b2c.widget.util.BitmapUtils;
import com.example.b2c.widget.util.Utils;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * 用途：
 * 作者：Created by john on 2017/2/10.
 * 邮箱：liulei2@aixuedai.com
 */


public class DescriptionActivityNew extends TempBaseActivity {
    public static ImageStringCallBack mListener;
    @Bind(R.id.et_message)
    EditText mEditText;
    @Bind(R.id.tv_submit)
    TextView tvSubmit;
    @Bind(R.id.selected_img)
    TextView mTvSelectPhoto;
    private PickImageDialog mImageDialog;
    private String descriptionValue;

    @Override
    public int getRootId() {
        return R.layout.activity_description_new;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(mTranslatesString.getGoods_message());
        tvSubmit.setText(mTranslatesString.getCommon_sure());
        mTvSelectPhoto.setText(mTranslatesString.getCommon_selerctphoto());
    }

    @OnClick({R.id.selected_img, R.id.tv_submit})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.selected_img:
                mImageDialog = new PickImageDialog(this, view.getId(), rdm) {
                    @Override
                    protected void onImageUpLoad(int id, final String url, final String localPath) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Log.d("localPath", localPath);
                                Bitmap bitmap = BitmapUtils.decodeBitmapFromFile(localPath, 600, 1000);
                                insertPic(bitmap, 0, url);
                            }
                        });
                    }
                };
                mImageDialog.show();
                break;
            case R.id.tv_submit:
                mListener.onImageString(Utils.cutNull(descriptionValue));
                finish();
                break;
        }
        mEditText.addTextChangedListener(new SimpleTextWatcher() {
            @Override
            public void afterTextChanged(Editable editable) {
                descriptionValue = editable.toString();
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }
        });
    }

    private void insertPic(Bitmap bitmap, int i, String url) {
        ImageSpan imageSpan = new ImageSpan(this, bitmap);
        int index = mEditText.getSelectionStart();
        Editable mEtAble = mEditText.getEditableText();
        SpannableString newLine = new SpannableString("\n");
        mEtAble.insert(index, newLine);
        String temple = "<img src=\"" + url + "\"/>";
        SpannableString spannableString = new SpannableString(temple);
        spannableString.setSpan(imageSpan, 0, temple.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        if (index < 0 || index > mEtAble.length()) {
            mEtAble.append(spannableString);
        } else {
            mEtAble.insert(index, spannableString);
        }
        mEtAble.insert(index, newLine);
    }

    public interface ImageStringCallBack {
        void onImageString(String value);
    }

    public static void getInstance(ReleaseProductActivity mContext, String value, ImageStringCallBack mCallBack) {
        Intent intent = new Intent();
        mListener = mCallBack;
        intent.setClass(mContext, DescriptionActivityNew.class);
        intent.putExtra("value", value);
        mContext.startActivity(intent);
    }
}
