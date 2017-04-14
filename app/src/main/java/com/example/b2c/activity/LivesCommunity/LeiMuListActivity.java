package com.example.b2c.activity.LivesCommunity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.b2c.R;
import com.example.b2c.activity.common.TempBaseActivity;
import com.example.b2c.adapter.livesCommunity.LeiMuGridViewAdapter;
import com.example.b2c.helper.ImageHelper;
import com.example.b2c.net.listener.base.OneDataListener;
import com.example.b2c.net.response.livesCommunity.HomeLeiMu;

import java.util.List;

public class LeiMuListActivity extends TempBaseActivity {

    private TextView toolbar_title;
    private ListView lv_leimu;
    private List<HomeLeiMu.Rows> rows;

    @Override
    public int getRootId() {
        return R.layout.activity_lei_mu_list;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initData();
        initLister();
    }

    private void initView() {
        toolbar_title = (TextView) findViewById(R.id.toolbar_title);
        lv_leimu = (ListView) findViewById(R.id.lv_leimu);
        toolbar_title.setText(mTranslatesString.getCommon_leimuselect());
    }
    private void initLister(){
        lv_leimu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //跳转到发布界面
                startActivity(new Intent(getApplication(),FaBuMessageActivity.class).putExtra("categoryId",rows.get(i).getId()+""));
                finish();
            }
        });
    }
    private void initData(){
        showLoading();
        final MyAdapter myAdapter=new MyAdapter();
        mLogisticsDataConnection.getLivesLeimu(getApplication());
        mLogisticsDataConnection.mOneDataListener=new OneDataListener() {
            @Override
            public void onSuccess(Object data, String successInfo) {
                HomeLeiMu homeLeiMu= (HomeLeiMu) data;
                rows = homeLeiMu.getRows();
                lv_leimu.setAdapter(myAdapter);
                dissLoading();
            }
            @Override
            public void onError(int errorNO, String errorInfo) {
                Toast.makeText(getApplication(),errorInfo,Toast.LENGTH_LONG).show();
                dissLoading();
            }
            @Override
            public void onFinish() {}
            @Override
            public void onLose() {dissLoading();}
        };
    }
    private class MyAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return rows.size();
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View convertView, ViewGroup viewGroup) {
           ViewHolder holder;
            if(convertView == null || convertView.getTag() == null){
                convertView = View.inflate(getApplication(),R.layout.item_child_account,null);
                 holder = new ViewHolder();
                holder.tv_title = (TextView)convertView.findViewById(R.id.tv_child_username);
                convertView.setTag(holder);
            }else{
                holder = (ViewHolder)convertView.getTag();
            }
            holder.tv_title.setText(rows.get(i).getCategoryName());
            return convertView;
        }
    }
    class ViewHolder{
        private TextView tv_title;
    }

}
