package com.example.b2c.dialog;

import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.b2c.R;
import com.example.b2c.widget.util.Utils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Created by yy on 16-11-12.
 */
public class ItemsDialogHelper{
    private Context context;
    private String title;
    private List<HashMap<String, String>> value;
    private HashMap<String, String> selected;
    private ItemsDialogHelper.AbstractOnItemClickListener itemClickListener;


    public ItemsDialogHelper(Context context,String title, List<HashMap<String, String>> value, HashMap<String, String> selected, ItemsDialogHelper.AbstractOnItemClickListener itemClickListener) {
        this.context = context;
        this.title = title;
        this.value = value;
        this.selected = selected;
        this.itemClickListener = itemClickListener;
    }
    public  void show(){
        Dialog dialog = new Dialog(context, R.style.Dialog);
        dialog.setCanceledOnTouchOutside(true);
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_items_common, null);
        if (TextUtils.isEmpty(title)) {
            (view.findViewById(R.id.title)).setVisibility(View.GONE);
        } else {
            (view.findViewById(R.id.title)).setVisibility(View.VISIBLE);
            ((TextView) view.findViewById(R.id.title)).setText(title);
        }
        ListView contentView = (ListView) view.findViewById(R.id.popup_list_view);
        contentView.setAdapter(new PopupListAdapter(context, value, getItemPostion(selected)));
        contentView.setOnItemClickListener(itemClickListener);
        itemClickListener.setDialog(dialog);
        dialog.setContentView(view);
        dialog.show();
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

    public static class PopupListAdapter extends BaseAdapter {
        Context context;
        List<HashMap<String, String>> value;
        int selectItem = -1;


        public PopupListAdapter(Context context, List<HashMap<String, String>> value, int selectItem) {
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
            final ItemsDialogHelper.PopupListAdapter.ViewHolder holder;
            if (convertView == null) {
                holder = new ItemsDialogHelper.PopupListAdapter.ViewHolder();
                convertView = LayoutInflater.from(context).inflate(R.layout.dialog_items_common_item, parent, false);
                holder.textView = (TextView) convertView.findViewById(R.id.text);
                holder.imgView = (ImageView) convertView.findViewById(R.id.img);
                convertView.setTag(holder);
            } else {
                holder = ( ItemsDialogHelper.PopupListAdapter.ViewHolder) convertView.getTag();
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
