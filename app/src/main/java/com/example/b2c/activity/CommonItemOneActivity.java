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

import java.io.Serializable;
import java.util.List;
/**
 * 单选项
 */
public class CommonItemOneActivity extends TempBaseActivity{

    private  String  mModifyTitle;
    private  CellText cell ;
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
                //全不选
                for(CellText ce:cells){
                    ce.setChecked(false);
                }
                //选中点击的按钮
                CellText  cellText = cells.get(position);
                cellText.setChecked(true);
                commonItemAdapter.notifyDataSetChanged();
            }
        });
    }

    private void initView() {
        mModifyTitle = getIntent().getExtras().getString("title");
        Serializable sobject = getIntent().getSerializableExtra("object");
        if(sobject == null){
            cell = (CellText)sobject ;
        }else {
            cell = null;
        }
        goodSources = (GoodSources) getIntent().getSerializableExtra("items");
        lv_items = (ListView) findViewById(R.id.lv_items);
        addText(mTranslatesString.getCommon_done(), new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cell = getCheckedItems();
                if(cell == null){
                    return;
                }else{
                    Intent intent=  CommonItemOneActivity.this.getIntent();
                    intent.putExtra("object",cell);
                    setResult(RESULT_OK,intent);
                    finish();
                }
            }
        });
    }

    private CellText getCheckedItems() {
        CellText cell = null;
        List<CellText> cells = goodSources.getCells();
        for (int i = 0; i < cells.size(); i++) {
            if(cells.get(i).isChecked()){
                cell = cells.get(i);
            }
        }
        return cell;
    }

    private void initText() {
        setTitle(mModifyTitle);
    }

}
