package com.example.b2c.adapter.base;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jcodecraeer.xrecyclerview.XRecyclerView;

/**
 * XRcycleView适配起，里面有点击事件
 */

public abstract class XRcycleViewAdapterBase extends XRecyclerView.Adapter<XRcycleViewAdapterBase.MyViewHolder> implements View.OnClickListener,View.OnLongClickListener {


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(getContext()).inflate(getLayoutID(), parent, false);
        XRcycleViewAdapterBase.MyViewHolder holder = new XRcycleViewAdapterBase.MyViewHolder(view);
        view.setOnClickListener(this);
        view.setOnLongClickListener(this);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        initData(holder ,position);
        holder.itemView.setTag(position);
    }
    @Override
    public int getItemCount() {
        return getItemPostion();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    /**
     * 获取布局的条目数（有多少个item）：
     */
    public abstract int getItemPostion();
    /**
     * 获取布局（R.layout.xxxx）：
     */
    public abstract int getLayoutID();

    /**
     * 获取上下文
     * @return
     */
    public abstract Context getContext();

    /**
     * 在这方法里写填充数据（将请求的数据设置给具体的控件）
     * @param holder
     * @param position
     */
    public abstract void initData(MyViewHolder holder, int position);

    /**
     * 再找个方法里写获取布局文件里的控件id（
     * 例子：this.tv_username = (TextView) itemView.findViewById(R.id.tv_child_username);
     * ）
     * @param itemView
     */
    public abstract void initView(View itemView);

    /**
     * 点击事件的监听
     */
    private OnRecyclerViewItemClickListener mOnItemClickListener = null;
    /**
     * 长按事件的监听
     */
    private OnRecycleViewLongItemClickListener mOnItemLongClickListener = null;

    @Override
    public void onClick(View view) {
        if (mOnItemClickListener != null) {
            //注意这里使用getTag方法获取数据
            mOnItemClickListener.onItemClick(view,(int)view.getTag());
        }
    }

    @Override
    public boolean onLongClick(View view) {
        if (mOnItemLongClickListener != null) {
            //注意这里使用getTag方法获取数据
            mOnItemLongClickListener.onLongItemClick(view,(int)view.getTag());
        }
        return true;
    }
    //点击事件
    public  interface OnRecyclerViewItemClickListener {
        void onItemClick(View view , int position);
    }
    public void setOnItemClickListener(OnRecyclerViewItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }
    //长按事件
    public  interface OnRecycleViewLongItemClickListener{
        void onLongItemClick(View view,int position);
    }
    public void setOnLongItemClickListener(OnRecycleViewLongItemClickListener listener){
        this.mOnItemLongClickListener=listener;
    }
    public class MyViewHolder extends RecyclerView.ViewHolder{

        public MyViewHolder(View itemView) {
            super(itemView);
            initView(itemView);
        }
    }
}
