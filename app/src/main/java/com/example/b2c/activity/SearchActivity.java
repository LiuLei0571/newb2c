package com.example.b2c.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.b2c.R;
import com.example.b2c.activity.common.TempBaseActivity;

/**
 * 买家搜索页面
 */
public class SearchActivity extends TempBaseActivity implements OnClickListener {

    public static int SAMPLE = 0, SHOP = 1;

    private Spinner sp_search;
    private Button btn_search;
    private EditText et_sample_name;

    private int search_type = SAMPLE;
    private String search_name;

    private Intent intent;
    private Bundle bundle;
    private String[] type;
    private ArrayAdapter<String> sp_adapter;

    @Override
    public int getRootId() {
        return R.layout.search_layout;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        type = new String[]{
                mTranslatesString.getCommon_sample(),
                mTranslatesString.getCommon_shop()};
        initView();
    }

    public void initView() {
        sp_search = (Spinner) findViewById(R.id.sp_search_type);
        btn_search = (Button) findViewById(R.id.btn_search);
        et_sample_name = (EditText) findViewById(R.id.et_search);

        sp_adapter = new ArrayAdapter<>(this,android.R.layout.simple_spinner_item, type);
        sp_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        sp_search.setAdapter(sp_adapter);

        btn_search.setOnClickListener(this);
        sp_search.setOnItemSelectedListener(new TypeSpinnerListener());
        initText();
    }

    public void initText() {
        setTitle(mTranslatesString
                .getCommon_samplesearch());
        et_sample_name.setHint(mTranslatesString
                .getCommon_pleaseinputsampleshop());
        btn_search.setText(mTranslatesString.getCommon_search());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_search:
                search_name = et_sample_name.getText().toString();
                intent = new Intent();
                bundle = new Bundle();
                if(search_type== SAMPLE){
                    intent.setClass(this, GoodsListActivity.class);
                    bundle.putInt("search_type", search_type);
                    bundle.putString("search_name", search_name);
                    bundle.putBoolean("is_search",true);
                }else if(search_type == SHOP){
                    intent.setClass(this, ShopsListActivity.class);
                    bundle.putString("search_name", search_name);
                }
                intent.putExtras(bundle);
                startActivity(intent);
                break;
            default:
                break;
        }
    }

    class TypeSpinnerListener implements OnItemSelectedListener {

        @Override
        public void onItemSelected(AdapterView<?> arg0, View arg1,
                                   int position, long arg3) {
            if (position == 0)
                search_type = SAMPLE;
            else if (position == 1)
                search_type = SHOP;
        }

        @Override
        public void onNothingSelected(AdapterView<?> arg0) {

        }

    }
}
