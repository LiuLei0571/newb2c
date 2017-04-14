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
import com.example.b2c.net.response.translate.CellText;

import java.util.List;

/**
 * @author Created by yy on 16-11-12.
 */
public class MyItemsDialogHelper {
    private Context context;
    private String title;
    private List<CellText> items;
    private MyItemsDialogHelper.AbstractOnItemClickListener itemClickListener;


    public MyItemsDialogHelper(Context context, String title, List<CellText> items, MyItemsDialogHelper.AbstractOnItemClickListener itemClickListener) {
        this.context = context;
        this.title = title;
        this.items = items;
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
        contentView.setAdapter(new PopupListAdapter(context, items));
        contentView.setOnItemClickListener(itemClickListener);
        itemClickListener.setDialog(dialog);
        dialog.setContentView(view);
        dialog.show();
    }

    public static class PopupListAdapter extends BaseAdapter {
        Context context;
        List<CellText> items;


        public PopupListAdapter(Context context, List<CellText> items) {
            this.context = context;
            this.items = items;
        }

        @Override
        public int getCount() {
            return items.size();
        }

        @Override
        public CellText getItem(int position) {
            return items.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            CellText item = getItem(position);
            final MyItemsDialogHelper.PopupListAdapter.ViewHolder holder;
            if (convertView == null) {
                holder = new MyItemsDialogHelper.PopupListAdapter.ViewHolder();
                convertView = LayoutInflater.from(context).inflate(R.layout.dialog_items_common_item, parent, false);
                holder.textView = (TextView) convertView.findViewById(R.id.text);
                holder.imgView = (ImageView) convertView.findViewById(R.id.img);
                convertView.setTag(holder);
            } else {
                holder = ( MyItemsDialogHelper.PopupListAdapter.ViewHolder) convertView.getTag();
            }

            if (item != null && item.isChecked()) {
                holder.textView.setTextColor(context.getResources().getColor(R.color.main_green));
                holder.imgView.setVisibility(View.VISIBLE);
            } else {
                holder.textView.setTextColor(context.getResources().getColor(R.color.text_normal));
                holder.imgView.setVisibility(View.GONE);
            }
            holder.textView.setText(item.getText());
            return convertView;
        }
        class ViewHolder {
            public TextView textView;
            public ImageView imgView;
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
