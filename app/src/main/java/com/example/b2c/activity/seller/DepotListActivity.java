package com.example.b2c.activity.seller;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.b2c.R;
import com.example.b2c.activity.common.TempBaseActivity;
import com.example.b2c.dialog.DialogHelper;
import com.example.b2c.helper.ToastHelper;
import com.example.b2c.net.listener.ResponseListener;
import com.example.b2c.net.listener.base.MyItemClickListener;
import com.example.b2c.net.listener.base.MyItemLongClickListener;
import com.example.b2c.net.listener.base.PageListListener;
import com.example.b2c.net.response.seller.DepotEntry;
import com.example.b2c.widget.util.Utils;

import java.util.ArrayList;
import java.util.List;


/**
 * 查看仓库列表页面
 */

public class DepotListActivity extends TempBaseActivity implements MyItemClickListener, MyItemLongClickListener {

    private RecyclerView rv_recyclerview;
    private List<DepotEntry> mDatas = new ArrayList<>();
    private DepotAdapter mAdapter;
    public static ItemClick mItemListener;

    @Override
    public int getRootId() {
        return R.layout.activity_depot_list;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
    }

    /**
     * 获取所有的仓库信息
     */
    private void initData() {
        showLoading();
        sellerRdm.getAllDepotList();
        sellerRdm.mPageListListener = new PageListListener<DepotEntry>() {

            @Override
            public void onFinish() {
                dissLoading();
            }

            @Override
            public void onLose() {
                ToastHelper.makeErrorToast(request_failure);
            }

            @Override
            public void onSuccess(List<DepotEntry> list) {
                mDatas.addAll(list);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mAdapter.notifyDataSetChanged();
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
        mDatas.clear();
        initData();
    }

    /**
     * 删除仓库地址
     */
    private void deleteDepot(int depotId, final int postion) {
        showLoading();
        sellerRdm.deleteDepotById(depotId);
        sellerRdm.mResponseListener = new ResponseListener() {

            @Override
            public void onFinish() {
                dissLoading();
            }

            @Override
            public void onLose() {
                ToastHelper.makeErrorToast(request_failure);
            }

            @Override
            public void onSuccess(String errorInfo) {
                ToastHelper.makeToast(errorInfo);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mDatas.remove(postion);
                        mAdapter.notifyDataSetChanged();
                    }
                });
            }

            @Override
            public void onError(int errorNO, String errorInfo) {
                ToastHelper.makeErrorToast(errorInfo);
           }
        };

    }


    private void initView() {
        setTitle(mTranslatesString.getCommon_depotlist());
        rv_recyclerview = (RecyclerView) findViewById(R.id.rv_recyclerview);
        rv_recyclerview.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new DepotAdapter();
        mAdapter.setOnItemClickListener(this);
        mAdapter.setOnItemLongClickListener(this);
        rv_recyclerview.setAdapter(mAdapter);
        if (mItemListener == null) {
            addText(mTranslatesString.getCommon_newinsert(), new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i_new = new Intent(DepotListActivity.this, EditDepotActivity.class);
                    i_new.putExtra("depot", new DepotEntry());
                    startActivity(i_new);
                }
            });
        }

    }

    @Override
    public void onItemClick(View view, int postion) {
        if (mItemListener == null) {
            Intent i_edit = new Intent(DepotListActivity.this, EditDepotActivity.class);
            i_edit.putExtra("depot", mDatas.get(postion));
            startActivity(i_edit);
        } else {
            mItemListener.onItemClicks(mDatas.get(postion));
            finish();
        }
    }

    @Override
    public void onItemLongClick(View view, final int postion) {
//        Toast.makeText(this, "long click", Toast.LENGTH_SHORT).show();
        DialogHelper.BtnTv deleteBtn = new DialogHelper.BtnTv(mTranslatesString.getCommon_delete(), new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DepotEntry depotEntry = mDatas.get(postion);
                if (depotEntry == null) {
                    return;
                } else {
                    deleteDepot(depotEntry.getId(), postion);
                }
            }
        });
        DialogHelper.BtnTv cancelBtn = new DialogHelper.BtnTv(mTranslatesString.getNotice_cancel(), new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        DialogHelper.showDialog(this, mTranslatesString.getNotice_noticename(), mTranslatesString.getCommon_quedingdeletedepot()+"?", Gravity.CENTER, deleteBtn, cancelBtn);
    }


    class DepotAdapter extends RecyclerView.Adapter<DepotAdapter.MyViewHolder> {

        MyItemClickListener mItemClickListener;
        MyItemLongClickListener mItemLongClickListener;

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(DepotListActivity.this).inflate(R.layout.item_depot, parent, false);
            MyViewHolder holder = new MyViewHolder(itemView, mItemClickListener, mItemLongClickListener);
            return holder;
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, int position) {
            holder.tv.setText(Utils.cutNull(mDatas.get(position).getProvinceName()) + " "
                    + Utils.cutNull(mDatas.get(position).getCityName()) + " "
                    + Utils.cutNull(mDatas.get(position).getAddress()));
        }

        /**
         * 设置Item点击监听
         *
         * @param listener
         */
        public void setOnItemClickListener(MyItemClickListener listener) {
            this.mItemClickListener = listener;
        }

        /**
         * * 设置Item长按监听
         *
         * @param listener
         */
        public void setOnItemLongClickListener(MyItemLongClickListener listener) {
            this.mItemLongClickListener = listener;
        }

        @Override
        public int getItemCount() {
            return mDatas.size();
        }

        class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
            private MyItemClickListener mItemClickListener;
            private MyItemLongClickListener mItemLongClickListener;
            TextView tv;

            public MyViewHolder(View view, MyItemClickListener mItemClickListener, MyItemLongClickListener mItemLongClickListener) {
                super(view);
                tv = (TextView) view.findViewById(R.id.tv_depot_name);
                this.mItemClickListener = mItemClickListener;
                this.mItemLongClickListener = mItemLongClickListener;
                view.setOnClickListener(this);
                view.setOnLongClickListener(this);
            }

            /**
             * 点击监听
             */
            @Override
            public void onClick(View v) {
                if (mItemClickListener != null) {
                    mItemClickListener.onItemClick(v, getPosition());
                }
            }

            /**
             * 长按监听
             */
            @Override
            public boolean onLongClick(View arg0) {
                if (mItemLongClickListener != null) {
                    mItemLongClickListener.onItemLongClick(arg0, getPosition());
                }
                return true;
            }

        }
    }

    public static void getInstance(TempBaseActivity activity, ItemClick itemClick) {
        mItemListener = itemClick;
        activity.startActivity(new Intent(activity, DepotListActivity.class));

    }

    public interface ItemClick {
        void onItemClicks(DepotEntry depotEntry);
    }
}
