package com.example.b2c.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.example.b2c.R;
import com.example.b2c.activity.common.ImageBucketChooseActivity;
import com.example.b2c.activity.common.TempBaseActivity;
import com.example.b2c.config.Configs;
import com.example.b2c.config.ConstantS;
import com.example.b2c.helper.LoadingHelper;
import com.example.b2c.helper.SPHelper;
import com.example.b2c.helper.ToastHelper;
import com.example.b2c.net.listener.UploadListener;
import com.example.b2c.net.response.translate.MobileStaticTextCode;
import com.example.b2c.widget.LogisticsDataConnection;
import com.example.b2c.widget.Progress;
import com.example.b2c.widget.RemoteDataConnection;
import com.example.b2c.widget.util.DialogUtil;
import com.example.b2c.widget.util.FileUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Liu on 2016/4/12.
 */
public abstract class PickImageDialog extends Dialog implements View.OnClickListener, TempBaseActivity.ActivityResultCallback {
    public static final int REQUEST_PICK_IMAGE = 1;
    public static final int REQUEST_SELECT_IMAGE = 2;
    private Handler mHanlder = new Handler();
    private Context mContext;
    private TempBaseActivity mActivity;
    private int id;
    private MobileStaticTextCode mTranslatesString;

    private LogisticsDataConnection mSellerDataConnection;
    private RemoteDataConnection rdm;
    private String newPath;
    private String type;

    public PickImageDialog(TempBaseActivity activity, int id, LogisticsDataConnection sellerRdm) {
        super(activity, R.style.DiaLog);
        mContext = getContext();
        activity.addActivityResultCallback(this);
        this.mActivity = activity;
        this.id = id;
        mSellerDataConnection = sellerRdm;
        init();
    }

    public PickImageDialog(TempBaseActivity activity, int id, String type) {
        super(activity, R.style.DiaLog);
        activity.addActivityResultCallback(this);
        this.mActivity = activity;
        this.id = id;
        this.type = type;
        init();
    }

    public PickImageDialog(TempBaseActivity activity, int id, RemoteDataConnection rdm) {
        super(activity, R.style.DiaLog);
        mContext = getContext();
        activity.addActivityResultCallback(this);
        this.mActivity = activity;
        this.id = id;
        this.rdm = rdm;
        init();
    }

    public void init() {
        Context context = getContext();
        mTranslatesString = SPHelper.getBean("translate", MobileStaticTextCode.class);
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_pickimage, null);
        setContentView(view);
        TextView mTvCamera = (TextView) view.findViewById(R.id.dialog_camera);
        mTvCamera.setText(mTranslatesString.getCommon_camera());
        mTvCamera.setOnClickListener(this);
        TextView mTvTakePhoto = (TextView) view.findViewById(R.id.dialog_take_photo);
        mTvTakePhoto.setText(mTranslatesString.getCommon_takephoto());
        mTvTakePhoto.setOnClickListener(this);
        if (rdm != null) {
            mTvTakePhoto.setVisibility(View.GONE);
        } else {
            mTvTakePhoto.setVisibility(View.VISIBLE);
        }
        newPath = FileUtils.getCachePath(getContext()) + FileUtils.getImgName(".jpg");
    }

    protected abstract void onImageUpLoad(int id, String url, String url1);

    @Override
    public void onClick(View v) {
        DialogUtil.dismiss(this);
        int id = v.getId();
        switch (id) {
            case R.id.dialog_take_photo://打开图库
                String status = Environment.getExternalStorageState();
                if (status.equals(Environment.MEDIA_MOUNTED)) {
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    File mImagePath = new File(newPath);
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(mImagePath));
                    mActivity.startActivityForResult(intent, REQUEST_PICK_IMAGE);
                } else {
                    ToastHelper.makeErrorToast("SD is missing");
                    destory();
                }
                break;
            case R.id.dialog_camera://打开照相机

                if (Configs.RELEASE.PRODUCT.Bucket.equals(type) || "change".equals(type)) {
                    Intent intent = new Intent(mActivity, ImageBucketChooseActivity.class);
                    intent.putExtra("release", type);
                    mActivity.startActivityForResult(intent, 1002);
                } else {
                    Intent intent = new Intent(Intent.ACTION_PICK);
                    String IMAGE_UNSPECIFIED = "image/*";
                    intent.setType(IMAGE_UNSPECIFIED);
                    mActivity.startActivityForResult(intent, REQUEST_SELECT_IMAGE);
                    break;
                }

        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_PICK_IMAGE && resultCode == Activity.RESULT_OK) {
            if ("multi".equals(type) || "change".equals(type)) {
                onImageUpLoad(id, newPath, "");

            } else {
                mHanlder.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        upload(newPath);
                    }
                }, 1000);
            }
        } else if (requestCode == REQUEST_SELECT_IMAGE && resultCode == Activity.RESULT_OK && data != null) {
            Uri uri = data.getData();
            if (uri != null) {
                String path = uri.getPath();
                String[] proj = {MediaStore.Images.Media.DATA};
                Cursor cursor = mActivity.managedQuery(uri, proj, null, null, null);
                if (cursor != null) {
                    int colum_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                    cursor.moveToFirst();
                    path = cursor.getString(colum_index);
                }
                if (!TextUtils.isEmpty(path)) {
                    File compressFile = FileUtils.compressFile(path, newPath);
                    if (compressFile == null) {
                        return;
                    }
                    if ("multi".equals(type)) {
                        onImageUpLoad(id, path, "");
                    } else {
                        upload(path);

                    }
                }
            }
        }
    }

    public void upload(final String imgFile) {
        LoadingHelper.showLoading(mActivity, "uploading...");
        List<String> mList = new ArrayList<>();
        mList.add(imgFile);
        if (mSellerDataConnection != null) {
            mSellerDataConnection.logisitcisUploadFile(false, -1, "", mList);
            mSellerDataConnection.uploadListener = new UploadListener() {
                @Override
                public void onSuccess(final List<String> path) {
                    mActivity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            onImageUpLoad(id, ConstantS.BASE_PIC_URL + path.get(0), path.get(0));
                            ToastHelper.makeToast("success");
                            FileUtils.delFile(newPath);
                        }
                    });

                }

                @Override
                public void onError(int errorNO, String errorInfo) {
                    ToastHelper.makeErrorToast(errorInfo);
                }

                @Override
                public void onFinish() {
                    Progress.dismiss();
                }

                @Override
                public void onLose() {
                    ToastHelper.makeErrorToast("request error");
                }
            };
        }
        if (rdm != null) {
            rdm.UploadFile(false, -1, "", mList, "description");
            rdm.uploadListener = new UploadListener() {
                @Override
                public void onSuccess(List<String> path) {
                    onImageUpLoad(id, ConstantS.BASE_PIC_URL + path.get(0), imgFile);
                    FileUtils.delFile(newPath);
                }

                @Override
                public void onError(int errorNO, String errorInfo) {
                    ToastHelper.makeErrorToast(errorInfo);
                }

                @Override
                public void onFinish() {
                    Progress.dismiss();
                }

                @Override
                public void onLose() {
                    ToastHelper.makeErrorToast("request error");

                }
            };
        }

    }

    public void dismiss() {
        super.dismiss();
    }

    public void destory() {
        DialogUtil.dismiss(this);
        mActivity.removeActivityResultCallback(this);
    }
}
