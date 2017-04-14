package com.example.b2c.dialog;

import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.b2c.R;
import com.example.b2c.net.response.CodeDetail;
import com.example.b2c.widget.util.Utils;

import java.util.List;

/**
 * @author Created by milk on 16-11-12.
 */
public class AreaDialogHelper {
    /**
     * 显示所有的省份或者地区
     * @param context
     * @param title
     * @param list
     * @param onItemClickListener
     * @return
     */
    public static Dialog showListPopupWindows(Context context, String title, List<CodeDetail> list, AbstractOnItemClickListener onItemClickListener) {
        final Dialog dialog = new Dialog(context, R.style.Dialog);
        dialog.setCanceledOnTouchOutside(true);
        View view = LayoutInflater.from(context).inflate(R.layout.popup_list_common, null);
        if (TextUtils.isEmpty(title)) {
            (view.findViewById(R.id.title)).setVisibility(View.GONE);
        } else {
            (view.findViewById(R.id.title)).setVisibility(View.VISIBLE);
            ((TextView) view.findViewById(R.id.title)).setText(title);
        }

        ListView contentView = (ListView) view.findViewById(R.id.popup_list_view);
        contentView.setAdapter(new PopupListAdapter(context, list, Gravity.CENTER));
        contentView.setOnItemClickListener(onItemClickListener);
        onItemClickListener.setDialog(dialog);
        dialog.setContentView(view);
        dialog.show();
        return dialog;
    }

    public static class PopupListAdapter extends BaseAdapter {
        Context context;
        List<CodeDetail> lists;
        int gravity;

        public PopupListAdapter(Context context, List<CodeDetail> lists, int gravity) {
            this.context = context;
            this.lists = Utils.cutNull(lists);
            this.gravity = gravity;
        }
        @Override
        public int getCount() {
            return lists.size();
        }
        @Override
        public CodeDetail getItem(int position) {
            return lists.get(position);
        }
        @Override
        public long getItemId(int position) {
            return position;
        }
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView == null) {
                holder = new ViewHolder();
                convertView = LayoutInflater.from(context).inflate(R.layout.area_item, null, false);
                holder.areaName = (TextView) convertView.findViewById(R.id.area_name);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            CodeDetail temp = getItem(position);
            holder.areaName.setText(temp.getText());
            return convertView;
        }
        class ViewHolder {
            public TextView areaName;
        }

    }


    public static abstract class AbstractOnItemClickListener implements AdapterView.OnItemClickListener {
        private Dialog dialog;
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            if (dialog != null) {
                dialog.dismiss();
            }
            onItemClick(parent, view, position);
        }

        public void setDialog(Dialog dialog) {
            this.dialog = dialog;
        }

        public abstract void onItemClick(AdapterView<?> parent, View view, int position);
    }

}
