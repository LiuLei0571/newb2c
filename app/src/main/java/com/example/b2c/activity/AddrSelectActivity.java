package com.example.b2c.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.b2c.R;
import com.example.b2c.activity.common.TempBaseActivity;
import com.example.b2c.adapter.AddressAdapter;
import com.example.b2c.helper.ToastHelper;
import com.example.b2c.net.listener.base.PageListListener;
import com.example.b2c.net.response.ShoppingAddressDetail;

import java.util.List;

/**
 * 买家下单选择地址，地址列表页面
 */
public class AddrSelectActivity extends TempBaseActivity implements AdapterView.OnItemClickListener{
    private ListView lv_my_addr;
    private List<ShoppingAddressDetail> addressList;
    private AddressAdapter adapter;
    private int  addressType;

    @Override
    public int getRootId() {
        return R.layout.activity_addr_select;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addressType = getIntent().getExtras().getInt("addressType");
        initView();
    }
    public void initView() {
        lv_my_addr = (ListView) findViewById(R.id.lv_my_addr);
        adapter = new AddressAdapter(this);
        lv_my_addr.setAdapter(adapter);
        lv_my_addr.setOnItemClickListener(this);
        addText(mTranslatesString.getCommon_manage(), new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i_manage_cas_addr = new Intent(AddrSelectActivity.this, MyAddrActivity.class);
                Bundle  bundle2 =new Bundle();
                bundle2.putInt("addressType",addressType);
                i_manage_cas_addr.putExtras(bundle2);
                startActivity(i_manage_cas_addr);
            }
        });
        initText();
    }
    public void initText() {
        if(addressType == 10){
            setTitle(mTranslatesString.getCommon_pleaseselect()+mTranslatesString.getCommon_receivelocation());
        }else if(addressType == 20){
            setTitle(mTranslatesString.getCommon_pleaseselect()+mTranslatesString.getConnom_shoukuandizhi());
        }
    }
    public void initData() {
        showLoading();
        rdm.GetShoppingAddressList(addressType);
        rdm.mPageListListener = new PageListListener() {
            @Override
            public void onFinish() {
                dissLoading();
            }

            @Override
            public void onLose() {
                ToastHelper.makeErrorToast(net_error);
            }

            @Override
            public void onSuccess(List list) {
                addressList = list;
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        adapter.setData(addressList);
                        adapter.notifyDataSetChanged();
                    }
                });
            }

            @Override
            public void onError(int errorNO, String errorInfo) {
                ToastHelper.makeErrorToast(errorInfo);
            }
        };
    }
    @Override
    protected void onResume() {
        super.onResume();
        initData();
    }

    /**
     * 点击查看详情
     * @param parent
     * @param view
     * @param position
     * @param id
     */
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        ShoppingAddressDetail detail = adapter.getItem(position);
        int detailId = detail.getId();
        Intent intent =new Intent();
        intent.putExtra("addressId",detailId);
        setResult(RESULT_OK,intent);
        finish();
    }

}
