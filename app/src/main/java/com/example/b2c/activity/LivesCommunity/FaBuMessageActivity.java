package com.example.b2c.activity.LivesCommunity;
/**
 * 1.写出gridView的适配器
 * 2.点击适配器最后一个加号进行选择照片还是相机
 * 3.将选好的照片显示出来
 */

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.b2c.R;
import com.example.b2c.activity.MainActivity;
import com.example.b2c.activity.common.ImageBucketChooseActivity;
import com.example.b2c.activity.common.TempBaseActivity;
import com.example.b2c.config.ConstantS;
import com.example.b2c.helper.BitmapUtil;
import com.example.b2c.helper.DialogGetHeadPicture;
import com.example.b2c.helper.DialogUtils;
import com.example.b2c.helper.RegularExpression;
import com.example.b2c.helper.ShowNativeImageActivity;
import com.example.b2c.helper.ToastHelper;
import com.example.b2c.helper.UserHelper;
import com.example.b2c.net.listener.UploadListener;
import com.example.b2c.net.listener.base.NoDataListener;
import com.example.b2c.net.listener.base.OneDataListener;
import com.example.b2c.net.response.common.ImageItem;
import com.example.b2c.net.response.livesCommunity.LivesDetailsBean;
import com.example.b2c.net.zthHttp.OkhttpUtils;
import com.example.b2c.widget.GridViewForScroll;
import com.example.b2c.widget.util.FileUtils;
import com.google.gson.Gson;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import me.iwf.photopicker.PhotoPicker;
import me.iwf.photopicker.PhotoPickerActivity;
import me.iwf.photopicker.PhotoPreview;
import me.iwf.photopicker.utils.ImageCaptureManager;

public class FaBuMessageActivity extends TempBaseActivity  {

    private Bitmap bimap;
    private TextView toolbar_title;
    private EditText et_content;
    private GridViewForScroll noScrollgridview;
    private LivesFaBuGridViewAdapter adapter;
    private List<String> fileList;
    private EditText et_title;
    private TextView tv_lianxi_title;
    private TextView fabu_zishu;
    private EditText et_peplo_name;
    private TextView tv_lianxi_phone;
    private EditText et_peplo_number;
    private TextView tv_message_fabu;
    private OkhttpUtils instance;
    private List<Bitmap> mapList;
    private Gson gson;
    private String categoryId;
    private LocalBroadcastManager lm;
    private ImageCaptureManager captureManager;
    private BitmapUtil bitmapUtil;
    private LivesDetailsBean livesDetailsBean;
    private String id;
    private List<String> urlsList;

    @Override
    public int getRootId() {
        return R.layout.activity_fa_bu_message;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bimap = BitmapFactory.decodeResource(
                getResources(),
                R.drawable.icon_addpic_unfocused);
        //定义一个集合
        Intent intent = getIntent();
        categoryId = intent.getStringExtra("categoryId");
        id = intent.getStringExtra("informationId");
        String tag= intent.getStringExtra("tag");
        fileList = new ArrayList<>();//存放本地uri的集合
        mapList = new ArrayList<>();//存放bitmap的集合
        urlsList = new ArrayList<>();//存放上个界面传递过来的url
        bitmapUtil = new BitmapUtil();
        gson = new Gson();
        instance = OkhttpUtils.getInstance();
        lm = LocalBroadcastManager.getInstance(this);
        initView();
        initLinster();
        if (!TextUtils.isEmpty(tag)&&tag.equals("bianji")){
            requestDetails(id);
        }else{
            adapter = new LivesFaBuGridViewAdapter(this);
            noScrollgridview.setAdapter(adapter);
        }
    }

    //gridView的适配器以及点击
    private void initView() {
        noScrollgridview = (GridViewForScroll) findViewById(R.id.noScrollgridview);
        et_content = (EditText) findViewById(R.id.et_content);
        toolbar_title = (TextView) findViewById(R.id.toolbar_title);
        noScrollgridview.setSelector(new ColorDrawable(Color.TRANSPARENT));
        adapter = new LivesFaBuGridViewAdapter(this);
        noScrollgridview.setAdapter(adapter);
        toolbar_title.setText(mTranslatesString.getCommon_fabuMessage());
        et_title = (EditText) findViewById(R.id.et_title);
        tv_lianxi_title = (TextView) findViewById(R.id.tv_lianxi_title);
        et_peplo_name = (EditText) findViewById(R.id.et_peplo_name);
        tv_lianxi_phone = (TextView) findViewById(R.id.tv_lianxi_phone);
        et_peplo_number = (EditText) findViewById(R.id.et_peplo_number);
        tv_message_fabu = (TextView) findViewById(R.id.tv_message_fabu);
        fabu_zishu = (TextView) findViewById(R.id.fabu_zishu);
        tv_lianxi_title.setText(mTranslatesString.getCommon_lianxiname());
        tv_lianxi_phone.setText(mTranslatesString.getCommon_lianxiphone());
        tv_message_fabu.setText(mTranslatesString.getCommon_fabu());
    }

    private int zishu;
    private void initLinster() {
        //gridView的点击事件
        noScrollgridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String ss;
                if (i ==fileList.size()+urlsList.size() ) {
                    showDialog();
                } else {
                    Intent intent = new Intent(getApplication(), ShowNativeImageActivity.class);
                    if (i<urlsList.size()) {//如果点击的位置小于传递过来的集合，就要从传递过来的集合取
                        ss = ConstantS.BASE_PIC_URL + urlsList.get(i).toString();
                        intent.putExtra("tag", "tag");
                    }else{
                        ss =  fileList.get(i-urlsList.size()).toString();
                    }
                    intent.putExtra("smallPath", ss);
                    startActivityForResult(intent, 101);
                }
            }
        });
        noScrollgridview.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                deleteDialog(i);
                return true;
            }
        });

        //发布按钮的点击事件
        tv_message_fabu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submit();
            }
        });
        et_content.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                zishu=et_content.getText().length();

            }
            @Override
            public void afterTextChanged(Editable editable) {
                fabu_zishu.setText(zishu+"/100");
            }
        });
    }
    /**
     * 点击编辑跳转过来的要先请求服务器数据，将数据放到页面上，然后将请求来下的图片集合
     * 放到适配器中，点击删除就删除集合中的数据，然后刷新，保存的时候可以直接将请求下来的url上传
     *
     */
    private void requestDetails(String id){
        Map<String,String>map=new HashMap<>();
        map.put("informationId",id);
        showLoading();
        mLogisticsDataConnection.getLivesDetails(getApplication(),map,UserHelper.getLivesID());
        mLogisticsDataConnection.mOneDataListener=new OneDataListener() {
            @Override
            public void onSuccess(Object data, String successInfo) {
                livesDetailsBean = (LivesDetailsBean) data;
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        for (LivesDetailsBean.ImageList ss: livesDetailsBean.getImageList()){
                            Bitmap bitmap = bitmapUtil.downloadImgByUrl(ConstantS.BASE_PIC_URL+ss.getImagePath(), 720 * 1280);
                            mapList.add(bitmap);
                            urlsList.add(ss.getImagePath());
                            handler.sendEmptyMessage(900);
                        }
                    }
                }).start();


            }
            @Override
            public void onError(int errorNO, String errorInfo) {
                ToastHelper.makeErrorToast(errorInfo);
                dissLoading();
            }
            @Override
            public void onFinish() {}
            @Override
            public void onLose() {
                dissLoading();
            }
        };
    }
    private static final int WRITE_EXTERNAL_STORAGE_REQUEST_CODE = 1;
    private static final int READ_EXTERNAL_STORAGE = 2;
    private static final int TAKE_PICTURE = 0x000001;

    //显示dialog选择拍照还是图库
    private void showDialog() {
        new DialogGetHeadPicture(FaBuMessageActivity.this, R.style.progress_dialog, mTranslatesString.getCommon_takephoto(),
                mTranslatesString.getCommon_camera(), mTranslatesString.getNotice_cancel()
        ) {
            @Override
            public void saoma() {
                if (ContextCompat.checkSelfPermission(getApplication(), Manifest.permission.CAMERA)
                        != PackageManager.PERMISSION_GRANTED) {
                    //申请WRITE_EXTERNAL_STORAGE权限
                    ActivityCompat.requestPermissions(FaBuMessageActivity.this, new String[]{Manifest.permission.CAMERA},
                            WRITE_EXTERNAL_STORAGE_REQUEST_CODE);
                } else {
                    try {
                        if(captureManager == null){
                            captureManager = new ImageCaptureManager(FaBuMessageActivity.this);
                        }
                        Intent intent = captureManager.dispatchTakePictureIntent();
                        startActivityForResult(intent, ImageCaptureManager.REQUEST_TAKE_PHOTO);
                    } catch (IOException e) {
                        Toast.makeText(getApplication(),"", Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                    }
                }
            }
            @Override
            public void shuru() {
//                //选择图库
                PhotoPicker.builder()
                        .setPhotoCount(6-mapList.size())
                        .setShowCamera(false)
                        .setPreviewEnabled(false)
                        .start(FaBuMessageActivity.this);

            }
        }.show();
    }
    //长按删除要给我提示
    private void deleteDialog(final int i){
        final DialogUtils dialogUtils=new DialogUtils(FaBuMessageActivity.this);
        dialogUtils.myshowDialog(mTranslatesString.getCommon_suredelete(),
                mTranslatesString.getCommon_makesure(),mTranslatesString.getNotice_cancel());
        dialogUtils.setMyOnClickListener(new DialogUtils.MyOnClickListener() {
            @Override
            public void onZuoClickListener() {
                //删除集合对应的item
                if (i<urlsList.size()){
                    urlsList.remove(i);
                }else{
                    fileList.remove(i-urlsList.size());
                }
                mapList.remove(i);
                adapter.notifyDataSetChanged();
                dialogUtils.aa.dismiss();
            }
            @Override
            public void onYouClickListener() {
                dialogUtils.aa.dismiss();
            }
        });
    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case ImageCaptureManager.REQUEST_TAKE_PHOTO:
                    if(captureManager.getCurrentPhotoPath() != null) {
                        captureManager.galleryAddPic();
                        // 照片地址
                        String imagePaht = captureManager.getCurrentPhotoPath();
                        File file = bitmapUtil.yasuo(getApplication(), imagePaht);
                        fileList.add(file.toString());
                        mapList.add(bitmapUtil.revitionImageSize(file.toString(), 1080 * 1920)) ;
                        adapter.notifyDataSetChanged();
                    }
                    break;
                case PhotoPicker.REQUEST_CODE:
                    List<String> photos = null;
                    if (data != null) {
                        photos = data.getStringArrayListExtra(PhotoPicker.KEY_SELECTED_PHOTOS);
                        for(String iamge:photos){
                            File file = bitmapUtil.yasuo(getApplication(), iamge);
                            fileList.add(file.toString());
                            mapList.add(bitmapUtil.revitionImageSize(iamge, 1080 * 1920)) ;
                        }
                    }
                    adapter.notifyDataSetChanged();
                    break;
            }
        }
    }
        //进行图片上传
    private void submit() {
        final String title = et_title.getText().toString().trim();
        final String name = et_peplo_name.getText().toString().trim();
        final String number = et_peplo_number.getText().toString().trim();
        final String content = et_content.getText().toString().trim();

        if (TextUtils.isEmpty(title)||TextUtils.isEmpty(name)||TextUtils.isEmpty(number)||TextUtils.isEmpty(content)) {
            Toast.makeText(this,mTranslatesString.getComon_xinxibuwanzheng(), Toast.LENGTH_SHORT).show();
            return;
        }
        if (RegularExpression.isEmoji(title)||RegularExpression.isEmoji(name)||
                RegularExpression.isEmoji(number)||RegularExpression.isEmoji(content)){
            Toast.makeText(this,mTranslatesString.getCommon_biaoqing(), Toast.LENGTH_SHORT).show();
            return;
        }
        showLoading();
//        //先进性上传图片
        sellerRdm.SellerUploadFile(ConstantS.BASE_PIC_URL+"remoting/rest/mobile/security/mobileUploadSamplePic.htm",true, UserHelper.getLivesID(), UserHelper.getLivesToken(), fileList, "informationImage");
        sellerRdm.uploadListener = new UploadListener() {
            @Override
            public void onSuccess(List<String> path) {
                //图片上传成功就进行发布
                urlsList.addAll(path);
                fabu(urlsList,title,content,name,number);
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
                dissLoading();
            }
        };
    }
private void fabu(List<String> path,String title,String content,String name,String number){
    Map<String,String>map=new HashMap<>();
    if (!TextUtils.isEmpty(id)){
        //为空说明是发布，不为空说明是修改
        map.put("informationId",id);
    }
    if (TextUtils.isEmpty(categoryId)){
        //如果为空说明是点击编辑过来的
        map.put("categoryId",livesDetailsBean.getCategoryId());
    }else{
        map.put("categoryId",categoryId);
    }
    map.put("images",gson.toJson(path));

    map.put("title",title);
    map.put("content",content);
    map.put("contactsName",name);
    map.put("contactsMobile",number);
    mLogisticsDataConnection.livesFabu(getApplication(),map);
    mLogisticsDataConnection.mNodataListener=new NoDataListener() {
        @Override
        public void onSuccess(String success) {
            Toast.makeText(getApplication(),success,Toast.LENGTH_LONG).show();
            dissLoading();
            //发布成功
            //发送一个广播
            lm.sendBroadcast(new Intent().setAction("com.plusjun.test.hahaha"));
            finish();
            //开启一个子线程去删除图片的缓存文件
            deleteFile();
        }

        @Override
        public void onError(int errorNo, String errorInfo) {
            Toast.makeText(getApplication(),errorInfo,Toast.LENGTH_LONG).show();
            dissLoading();
        }
        @Override
        public void onFinish() {}
        @Override
        public void onLose() {
            dissLoading();
        }
    };
}

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapList.clear();
        fileList.clear();
        urlsList.clear();
    }

    @SuppressLint("HandlerLeak")
    public class LivesFaBuGridViewAdapter extends BaseAdapter {
        private LayoutInflater inflater;
        private int selectedPosition = -1;
        private boolean shape;

        public boolean isShape() {
            return shape;
        }

        public void setShape(boolean shape) {
            this.shape = shape;
        }

        public LivesFaBuGridViewAdapter(Context context) {
            inflater = LayoutInflater.from(context);
        }
        public int getCount() {
            if (mapList.size() == 6) {
                return 6;
            }
            return (mapList.size() + 1);
        }

        public Object getItem(int arg0) {
            return null;
        }

        public long getItemId(int arg0) {
            return 0;
        }

        public void setSelectedPosition(int position) {
            selectedPosition = position;
        }

        public int getSelectedPosition() {
            return selectedPosition;
        }

        public View getView(final int position, View convertView, ViewGroup parent) {
            ViewHolder holder = null;
            if (convertView == null) {
                convertView = inflater.inflate(R.layout.item_published_grida,
                        parent, false);
                holder = new ViewHolder();
                holder.image = (ImageView) convertView
                        .findViewById(R.id.item_grida_image);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            if (position == mapList.size()) {
                holder.image.setImageBitmap(BitmapFactory.decodeResource(
                        getResources(), R.drawable.icon_addpic_unfocused));
                if (position == 6) {
                    holder.image.setVisibility(View.GONE);
                }
            } else {
                holder.image.setImageBitmap(mapList.get(position));
            }
            return convertView;
        }

        public class ViewHolder {
            public ImageView image;
        }
    }
    Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    adapter.notifyDataSetChanged();
                    break;
                case 900:
                    adapter = new LivesFaBuGridViewAdapter(FaBuMessageActivity.this);
                    noScrollgridview.setAdapter(adapter);
                    settingData();
                    dissLoading();
                    break;
            }
            super.handleMessage(msg);
        }
    };
    private void settingData(){
        et_title.setText(livesDetailsBean.getTitle());
        et_content.setText(livesDetailsBean.getContent());
        et_peplo_name.setText(livesDetailsBean.getContactsName());
        et_peplo_number.setText(livesDetailsBean.getContactsMobile());
    }
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case WRITE_EXTERNAL_STORAGE_REQUEST_CODE: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Intent openCameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(openCameraIntent, TAKE_PICTURE);
                } else {
                    Toast.makeText(getApplication(), "permission denied", Toast.LENGTH_LONG).show();
                }
                return;

            }
            case READ_EXTERNAL_STORAGE:{
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    deleteFile();
                } else {
                    Toast.makeText(getApplication(), "permission denied", Toast.LENGTH_LONG).show();
                }
                return;
            }
        }
    }
    private int i;
    /**
     * 删除图片的缓存文件
     */
    private void deleteFile(){
       // FileUtils.getCachePath(context)
        if (ContextCompat.checkSelfPermission(getApplication(), Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            //申请WRITE_EXTERNAL_STORAGE权限
            ActivityCompat.requestPermissions(FaBuMessageActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    READ_EXTERNAL_STORAGE);
        }else{
            new Thread(new Runnable() {
                @Override
                public void run() {
                    getFiles(FileUtils.getCachePath(getApplication()));
                    if (i>20){
                        //删除图片
                        File file=new File(FileUtils.getCachePath(getApplication()));
                        deleteFilesByDirectory(file);
                    }
                }
            }).start();
        }
    }

    private void getFiles(String string) {
// TODO Auto-generated method stub
        File file =new File(string);
        File[] files=file.listFiles();
        for(int j=0;j<files.length;j++){
            String name=files[j].getName();
            if (files[j].isDirectory()){
                String dirPath=files[j].toString().toLowerCase();
                getFiles(dirPath+"/");
            }else if(files[j].isFile()&name.endsWith(".jpg")||name.endsWith(".png")||name.endsWith(".bmp")||name.endsWith(".gif")||name.endsWith(".jpeg")){
                i++;
            }
        }
    }
    private static void deleteFilesByDirectory(File directory) {
        if (directory != null && directory.exists() && directory.isDirectory()) {
            for (File item : directory.listFiles()) {
                item.delete();
            }
        }
    }
}
