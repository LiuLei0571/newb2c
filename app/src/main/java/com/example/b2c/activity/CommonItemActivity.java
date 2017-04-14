package com.example.b2c.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.b2c.R;
import com.example.b2c.activity.common.TempBaseActivity;
import com.example.b2c.adapter.seller.CommonItemAdapter;
import com.example.b2c.net.response.seller.GoodSources;
import com.example.b2c.net.response.translate.CellText;
import com.example.b2c.net.util.Logs;

import org.apache.http.util.TextUtils;

import java.util.List;

/**
 * 多选项
 */
public class CommonItemActivity extends TempBaseActivity{

    private  String  mModifyTitle;
    private  String  mTextString;
    GoodSources goodSources;

    ListView lv_items;
    CommonItemAdapter commonItemAdapter;

    @Override
    public int getRootId() {
        return R.layout.activity_common_item;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initText();
        initData();
    }

    private void initData() {
        commonItemAdapter = new CommonItemAdapter(this, goodSources.getCells());
        Logs.d("commonItemAdapter",goodSources.getCells().toString());
        lv_items.setAdapter(commonItemAdapter);
        lv_items.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                List<CellText> cells = goodSources.getCells();
                CellText  cellText = cells.get(position);
                if(cellText.isChecked()){
                    cellText.setChecked(false);
                }else {
                    cellText.setChecked(true);
                }
                commonItemAdapter.notifyDataSetChanged();
            }
        });
    }

    private void initView() {
        mModifyTitle = getIntent().getExtras().getString("title");
        mTextString = getIntent().getExtras().getString("text");
        goodSources = (GoodSources) getIntent().getSerializableExtra("items");
        lv_items = (ListView) findViewById(R.id.lv_items);
        addText(mTranslatesString.getCommon_done(), new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTextString = getCheckedItems();
                if(TextUtils.isBlank(mTextString)){
                    return;
                }else{
                    Intent intent=  CommonItemActivity.this.getIntent();
                    intent.putExtra("text",mTextString);
                    intent.putExtra("items",goodSources);
                    setResult(RESULT_OK,intent);
                    finish();
                }
            }
        });
    }

    private String getCheckedItems() {

        StringBuilder builder = new StringBuilder();
        List<CellText> cells = goodSources.getCells();
        for (int i = 0; i < cells.size(); i++) {
            if(cells.get(i).isChecked()){
                builder.append(cells.get(i).getValue()+",");
            }
        }
        String builderString = builder.toString();
        if(builderString.endsWith(",")){
         return  builderString.substring(0,builderString.length()-1);
        }
        return builderString;
    }

    private void initText() {
        setTitle(mModifyTitle);
    }

}
