package com.example.b2c.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.b2c.R;
import com.example.b2c.activity.common.TempBaseActivity;
import com.example.b2c.widget.EditTextWithDelete;

import org.apache.http.util.TextUtils;

/**
 * 一般编辑页面
 * 传入要编辑的ｔｉｔｌｅ
 * 传入要编辑的ｔｅｘｔ
 * 返回编辑过得文本
 */
public class CommonEditActivity extends TempBaseActivity {
    private  String  mModifyTitle;
    private  String  mTextString;
    EditTextWithDelete et_textName;
    @Override
    public int getRootId() {
        return R.layout.activity_common_edit;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initText();
        
    }

    private void initText() {
        setTitle(mModifyTitle);
        if(!TextUtils.isBlank(mTextString)){
            et_textName.setText(mTextString);
        }
    }

    private void initView() {
        mModifyTitle = getIntent().getExtras().getString("title");
        mTextString = getIntent().getExtras().getString("text");
        et_textName = (EditTextWithDelete) findViewById(R.id.et_textName);
        addText(mTranslatesString.getCommon_done(), new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(TextUtils.isBlank(et_textName.getText().toString())){
                    return;
                }else{
                    Intent intent=  CommonEditActivity.this.getIntent();
                    intent.putExtra("text",et_textName.getText().toString());
                    setResult(RESULT_OK,intent);
                    finish();
                }
            }
        });
    }

}
