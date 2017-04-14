package com.example.b2c.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.example.b2c.R;
import com.example.b2c.activity.common.TempBaseActivity;
import com.example.b2c.adapter.AddressAdapter;
import com.example.b2c.dialog.DialogHelper;
import com.example.b2c.helper.ToastHelper;
import com.example.b2c.net.listener.ResponseListener;
import com.example.b2c.net.listener.base.PageListListener;
import com.example.b2c.net.response.ShoppingAddressDetail;

import java.util.List;

/**
 * 收款、收货地址列表
 */
public class MyAddrActivity extends TempBaseActivity implements OnClickListener, AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener{

    private Button btn_add;
    private ListView lv_my_addr;
    private List<ShoppingAddressDetail> addressList;
    private AddressAdapter adapter;
    private int  addressType;


    @Override
    public int getRootId() {
        return R.layout.my_addr_layout;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addressType = getIntent().getExtras().getInt("addressType");
        initView();
    }

    public void initView() {
        btn_add = (Button) findViewById(R.id.btn_add_addr);
        lv_my_addr = (ListView) findViewById(R.id.lv_my_addr);
        btn_add.setOnClickListener(this);
        adapter = new AddressAdapter(this);
        lv_my_addr.setAdapter(adapter);
        lv_my_addr.setOnItemClickListener(this);
        lv_my_addr.setOnItemLongClickListener(this);
        initText();
    }
    public void initText() {
        if(addressType == 10){
            setTitle(mTranslatesString.getCommon_managereceivelocation());
        }else{
            setTitle(mTranslatesString.getCommon_managecashaddr());
        }
        btn_add.setText(mTranslatesString.getCommon_addnewlocation());
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
            public void onSuccess( List list) {
                addressList =list;
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
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_add_addr:
                Intent i_add_addr = new Intent(this, EditAddrActivity.class);
                Bundle bundle = new Bundle();
                bundle.putInt("addressId", -1);// 点击添加地址按钮，也传递addressId，传入-1以便判断是否是新增地址，否则会报空
                bundle.putInt("addressType",addressType);//地址的类型： 10收货  20收款
                i_add_addr.putExtras(bundle);
                startActivity(i_add_addr);
                break;
            default:
                break;
        }
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
        Intent intent = new Intent(MyAddrActivity.this, EditAddrActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("address", detail);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    /**
     * 长按删除
     * @param parent
     * @param view
     * @param position
     * @param id
     * @return
     */
    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
        DialogHelper.BtnTv deleteBtn =  new DialogHelper.BtnTv(mTranslatesString.getCommon_delete(),new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                ShoppingAddressDetail addr =  addressList.get(position);
                if(addr==null){
                    return;
                }else{
                    deleteAddr(addr.getId(),position);
                }
            }
        });
        DialogHelper.BtnTv cancelBtn =  new DialogHelper.BtnTv(mTranslatesString.getNotice_cancel(),new View.OnClickListener(){
            @Override
            public void onClick(View v) {

            }
        });

        DialogHelper.showDialog(this,mTranslatesString.getNotice_noticename(),mTranslatesString.getCommon_suredelete(), Gravity.CENTER,deleteBtn,cancelBtn);
        return true;
    }

    /**
     * 删除地址信息
     * @param id
     * @param position
     */
    private void deleteAddr(int id, final int position) {

        showLoading();
        rdm.DeleteShoppingAddress(id);
        rdm.responseListener = new ResponseListener() {
                @Override
                public void onSuccess(String errorInfo) {
                    ToastHelper.makeToast(errorInfo);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            addressList.remove(position);
                            adapter.setData(addressList);
                            adapter.notifyDataSetChanged();
                        }
                    });
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
                    ToastHelper.makeErrorToast(request_failure);
                }
            };
        }
}
