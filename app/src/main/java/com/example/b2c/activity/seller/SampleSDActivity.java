package com.example.b2c.activity.seller;

import android.os.Bundle;
import android.widget.FrameLayout;

import com.example.b2c.R;
import com.example.b2c.activity.common.TempBaseActivity;
import com.example.b2c.fragment.seller.DepotFragment;
import com.example.b2c.fragment.seller.SellingFragment;

public class SampleSDActivity extends TempBaseActivity {

    private  static final int ON_DEPOT = 1;
    private  static final int ON_SALE = 2;


    private final HomePage[] fragmentPage = new HomePage[]{
            new HomePage("仓库中", DepotFragment.class),
            new HomePage("在售中", SellingFragment.class)
    };
    private FrameLayout tabcontent;
    private int type;

    @Override
    public int getRootId() {
        return R.layout.activity_sample_sd;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
    }

    private void initView() {
        type = getIntent().getExtras().getInt("type");
        tabcontent = (FrameLayout) findViewById(android.R.id.tabcontent);
        if(type == ON_DEPOT){
            setTitle(mTranslatesString.getGoods_depot());
            setCurrentTab(0,fragmentPage);
        }else if(type == ON_SALE){
            setTitle( mTranslatesString.getCommon_onselling());
            setCurrentTab(1,fragmentPage);
        }
    }
}
