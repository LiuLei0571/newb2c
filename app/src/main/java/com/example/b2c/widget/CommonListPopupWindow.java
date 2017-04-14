package com.example.b2c.widget;


import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.example.b2c.R;
import com.example.b2c.widget.util.Utils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 用途：
 * Created by milk on 16/10/30.
 * 邮箱：649444395@qq.com
 */

public class CommonListPopupWindow extends PopupWindow implements OnItemClickListener {

    private Context context;
    private List<HashMap<String, String>> value;
    private CommonCallBack callBack;
    private PopupCommonListAdapter adapter;
    private HashMap<String, String> selected;

    public interface CommonCallBack {
        void onClick(int selectItem, String value);
    }

    public CommonListPopupWindow(Context context, List<HashMap<String, String>> value, HashMap<String, String> selected, CommonCallBack callBack) {
        this.context = context;
        this.value = value;
        Log.d("List", "size:" + value.size());
        this.selected = selected;
        this.callBack = callBack;
        showPopupWindow();
    }

    public void showPopupWindow() {
        View view = LayoutInflater.from(context).inflate(R.layout.popup_common_list, null);
        ListView listView = (ListView) view.findViewById(R.id.popup_list_view);
        adapter = new PopupCommonListAdapter(context, value, getItemPostion(selected));
        listView.setAdapter(adapter);

        int popHeight;
        if (value.size() > 6) {
            popHeight = (int) context.getResources().getDimension(R.dimen.popup_height);  //5.5个Item高度
        } else {
            popHeight = LayoutParams.WRAP_CONTENT;
        }


        // 设置SelectPicPopupWindow的View
        this.setContentView(view);
        // 设置SelectPicPopupWindow弹出窗体的宽
        this.setWidth(LayoutParams.MATCH_PARENT);
        // 设置SelectPicPopupWindow弹出窗体的高
        this.setHeight(popHeight);
        // 设置SelectPicPopupWindow弹出窗体可点击
        this.setFocusable(true);
        this.setOutsideTouchable(true);
        // 设置SelectPicPopupWindow弹出窗体动画效果
        this.setAnimationStyle(R.style.PopupBottomAnim);
        // 实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(0x00000000);
        // 设置SelectPicPopupWindow弹出窗体的背景
        this.setBackgroundDrawable(dw);

        view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                dismiss();
                return false;
            }
        });

        listView.setOnItemClickListener(this);
        Log.d("CommonListPopupWindow", "popHeight:" + this.getHeight());
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        adapter.setSelectItem(position);
        adapter.notifyDataSetInvalidated();
        callBack.onClick(position, adapter.getItem(position).getValue());
        dismiss();
    }

    public void dismiss() {
        super.dismiss();
    }


    public void show(View parent) {
        showAtLocation(parent,
                Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
    }

    private int getItemPostion(HashMap<String, String> selected) {
        int selectItem = -1;
        if (selected == null) return selectItem;
        for (int i = 0; i < value.size(); i++) {
            if (selected.keySet().equals(value.get(i).keySet())) {
                selectItem = i;
                break;
            }
        }

        return selectItem;
    }

    public static class PopupCommonListAdapter extends BaseAdapter {

        Context context;
        List<HashMap<String, String>> value;
        int selectItem = -1;

        public void setSelectItem(int selectItem) {
            this.selectItem = selectItem;
        }

        public PopupCommonListAdapter(Context context, List<HashMap<String, String>> value, int selectItem) {
            this.context = context;
            this.selectItem = selectItem;
            this.value = Utils.cutNull(value);
        }

        @Override
        public int getCount() {
            return value.size();
        }

        @Override
        public Map.Entry<String, String> getItem(int position) {
            return getEntry(value.get(position));
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            final ViewHolder holder;
            if (convertView == null) {
                holder = new ViewHolder();
                convertView = LayoutInflater.from(context).inflate(R.layout.popup_list_common_item, parent, false);
                holder.textView = (TextView) convertView.findViewById(R.id.text);
                holder.imgView = (ImageView) convertView.findViewById(R.id.img);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            if (position == selectItem) {
                holder.textView.setTextColor(context.getResources().getColor(R.color.orange));
                holder.imgView.setVisibility(View.VISIBLE);
            } else {
                holder.textView.setTextColor(context.getResources().getColor(R.color.text_dark));
                holder.imgView.setVisibility(View.GONE);
            }

            holder.textView.setText(getItem(position).getValue());
            return convertView;
        }

        class ViewHolder {
            public TextView textView;
            public ImageView imgView;
        }
    }

    public static Map.Entry<String, String> getEntry(Map<String, String> content) {
        for (Map.Entry<String, String> entry : content.entrySet()) {
            return entry;
        }
        return null;
    }
}
