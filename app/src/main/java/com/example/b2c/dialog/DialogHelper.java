package com.example.b2c.dialog;

import android.app.Dialog;
import android.content.Context;
import android.text.Html;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.b2c.R;
import com.example.b2c.helper.UIHelper;
import com.example.b2c.net.response.base.Item;
import com.example.b2c.widget.util.Utils;

import java.util.List;
import java.util.Map;

/**
 * @author Created by liulei on 16-11-12.
 */
public class DialogHelper {

    public static boolean isShowing = false;

    public static class Btn {

        public Btn(int btn, View.OnClickListener clickListener) {
            this.btn = btn;
            this.clickListener = clickListener;
        }
        public Btn(String btn, View.OnClickListener clickListener) {
            this.btnString = btn;
            this.clickListener = clickListener;
        }
        int btn;
        String btnString;
        View.OnClickListener clickListener;
    }


    public static class BtnTv {

        public BtnTv(String btn, View.OnClickListener clickListener) {
            this.btn = btn;
            this.clickListener = clickListener;
        }

        String btn;
        View.OnClickListener clickListener;
    }

    public static Dialog showDialog(Context context, String title, String content, int gravity, Btn... btns) {
        if (context == null) return null;

        final Dialog dialog = new Dialog(context, R.style.Dialog);
        dialog.setCancelable(false);
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_common, null);

        TextView titleView = (TextView) view.findViewById(R.id.dialog_title);
        TextView contentView = (TextView) view.findViewById(R.id.dialog_content);

        if (!TextUtils.isEmpty(title)) {
            titleView.setText(title);
        } else {
            titleView.setVisibility(View.GONE);
        }
        contentView.setText(Html.fromHtml(content));
        contentView.setGravity(gravity);

        Button leftButton = (Button) view.findViewById(R.id.dialog_btn_left);
        Button rightButton = (Button) view.findViewById(R.id.dialog_btn_right);
        View leftLine = view.findViewById(R.id.dialog_line_left);
        View rightLine = view.findViewById(R.id.dialog_line_right);
        Button button = (Button) view.findViewById(R.id.dialog_btn);
        LinearLayout buttons = (LinearLayout) view.findViewById(R.id.dialog_btns);

        if (btns != null) {
            int length = btns.length;

            if (length == 1) {
                final Btn btn1 = btns[0];
                buttons.removeView(rightButton);
                buttons.removeView(leftButton);
                leftLine.setVisibility(View.GONE);
                rightLine.setVisibility(View.GONE);
                button.setText(btn1.btn);
                button.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        closeDialog(dialog);
                        if (btn1.clickListener != null) {
                            btn1.clickListener.onClick(v);
                        }
                    }
                });
            } else if (length == 2) {
                buttons.removeView(button);
                final Btn btn1 = btns[0];
                final Btn btn2 = btns[1];
                leftButton.setText(btn1.btn);
                leftButton.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        closeDialog(dialog);
                        if (btn1.clickListener != null) {
                            btn1.clickListener.onClick(v);
                        }
                    }
                });

                leftLine.setVisibility(View.VISIBLE);
                rightLine.setVisibility(View.GONE);

                rightButton.setText(btn2.btn);
                rightButton.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        closeDialog(dialog);
                        if (btn2.clickListener != null) {
                            btn2.clickListener.onClick(v);
                        }
                    }
                });

            } else {
                buttons.removeAllViews();
            }

        }

        dialog.setContentView(view);
        dialog.show();
        isShowing = true;
        return dialog;
    }

    public static Dialog showDialog(Context context, String title, String content, int gravity, BtnTv... btns) {
        if (context == null) return null;

        final Dialog dialog = new Dialog(context, R.style.Dialog);
        dialog.setCanceledOnTouchOutside(true);
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_common, null);

        TextView titleView = (TextView) view.findViewById(R.id.dialog_title);
        TextView contentView = (TextView) view.findViewById(R.id.dialog_content);

        if (!TextUtils.isEmpty(title)) {
            titleView.setText(title);
        } else {
            titleView.setVisibility(View.GONE);
        }
        contentView.setText(Html.fromHtml(content));
        contentView.setGravity(gravity);

        Button leftButton = (Button) view.findViewById(R.id.dialog_btn_left);
        Button rightButton = (Button) view.findViewById(R.id.dialog_btn_right);
        View leftLine = view.findViewById(R.id.dialog_line_left);
        View rightLine = view.findViewById(R.id.dialog_line_right);
        Button button = (Button) view.findViewById(R.id.dialog_btn);
        LinearLayout buttons = (LinearLayout) view.findViewById(R.id.dialog_btns);

        if (btns != null) {
            int length = btns.length;

            if (length == 1) {
                final BtnTv btn1 = btns[0];
                buttons.removeView(rightButton);
                buttons.removeView(leftButton);
                leftLine.setVisibility(View.GONE);
                rightLine.setVisibility(View.GONE);
                button.setText(btn1.btn);
                button.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        closeDialog(dialog);
                        if (btn1.clickListener != null) {
                            btn1.clickListener.onClick(v);
                        }
                    }
                });
            } else if (length == 2) {
                buttons.removeView(button);
                final BtnTv btn1 = btns[0];
                final BtnTv btn2 = btns[1];
                leftButton.setText(btn1.btn);
                leftButton.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        closeDialog(dialog);
                        if (btn1.clickListener != null) {
                            btn1.clickListener.onClick(v);
                        }
                    }
                });

                leftLine.setVisibility(View.VISIBLE);
                rightLine.setVisibility(View.GONE);

                rightButton.setText(btn2.btn);
                rightButton.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        closeDialog(dialog);
                        if (btn2.clickListener != null) {
                            btn2.clickListener.onClick(v);
                        }
                    }
                });

            } else {
                buttons.removeAllViews();
            }

        }

        dialog.setContentView(view);
        dialog.show();
        return dialog;
    }

    public static Dialog showDialog(Context context, int title, String content, int gravity, Btn... btns) {
        if (context == null) return null;
        final Dialog dialog = new Dialog(context, R.style.Dialog);
        dialog.setCancelable(false);
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_common, null);

        TextView titleView = (TextView) view.findViewById(R.id.dialog_title);
        TextView contentView = (TextView) view.findViewById(R.id.dialog_content);

        if (title != 0) {
            try {
                titleView.setText(title);
            } catch (Exception e) {
                titleView.setVisibility(View.GONE);
            }
        } else {
            titleView.setVisibility(View.GONE);
        }
        contentView.setText(content);
        contentView.setGravity(gravity);

        Button leftButton = (Button) view.findViewById(R.id.dialog_btn_left);
        Button rightButton = (Button) view.findViewById(R.id.dialog_btn_right);
        View leftLine = view.findViewById(R.id.dialog_line_left);
        View rightLine = view.findViewById(R.id.dialog_line_right);
        Button button = (Button) view.findViewById(R.id.dialog_btn);
        LinearLayout buttons = (LinearLayout) view.findViewById(R.id.dialog_btns);

        if (btns != null) {
            int length = btns.length;

            if (length == 1) {
                final Btn btn1 = btns[0];
                buttons.removeView(rightButton);
                buttons.removeView(leftButton);
                leftLine.setVisibility(View.GONE);
                rightLine.setVisibility(View.GONE);
                button.setText(btn1.btn);
                button.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        closeDialog(dialog);
                        if (btn1.clickListener != null) {
                            btn1.clickListener.onClick(v);
                        }
                    }
                });
            } else if (length == 2) {
                buttons.removeView(button);
                final Btn btn1 = btns[0];
                final Btn btn2 = btns[1];
                leftButton.setText(btn1.btn);
                leftButton.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        closeDialog(dialog);
                        if (btn1.clickListener != null) {
                            btn1.clickListener.onClick(v);
                        }
                    }
                });

                leftLine.setVisibility(View.VISIBLE);
                rightLine.setVisibility(View.GONE);

                rightButton.setText(btn2.btn);
                rightButton.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        closeDialog(dialog);
                        if (btn2.clickListener != null) {
                            btn2.clickListener.onClick(v);
                        }
                    }
                });

            } else {
                buttons.removeAllViews();
            }

        }

        dialog.setContentView(view);
        dialog.show();
        return dialog;
    }

    public static Dialog showListPopupWindows(Context context, String title, List<Item> list, AbstractOnItemClickListener onItemClickListener) {
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
        contentView.setAdapter(new PopupListImgAdapter(context, list, Gravity.CENTER));
        contentView.setOnItemClickListener(onItemClickListener);
        onItemClickListener.setDialog(dialog);
        dialog.setContentView(view);
        dialog.show();
        return dialog;
    }

    public static class PopupListImgAdapter extends BaseAdapter {
        Context context;
        List<Item> lists;
        int gravity;

        public PopupListImgAdapter(Context context, List<Item> lists, int gravity) {
            this.context = context;
            this.lists = Utils.cutNull(lists);
            this.gravity = gravity;
        }

        @Override
        public int getCount() {
            return lists.size();
        }

        @Override
        public Item getItem(int position) {
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
                convertView = LayoutInflater.from(context).inflate(R.layout.bank_item, null, false);
                holder.selected = (ImageView) convertView.findViewById(R.id.selected_img);
                holder.bankName = (TextView) convertView.findViewById(R.id.bank_name);
                holder.bankIco = (ImageView) convertView.findViewById(R.id.bank_ico);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            Item temp = getItem(position);
            holder.bankName.setText(temp.getItemName());
            if (!TextUtils.isEmpty(temp.getItemPic())) {
                UIHelper.displayImage(holder.bankIco, temp.getItemPic());
            }
            if (temp.getShowType() == 1) {
                holder.selected.setVisibility(View.VISIBLE);
            } else {
                holder.selected.setVisibility(View.GONE);
            }
            return convertView;
        }

        class ViewHolder {
            public TextView bankName;
            public ImageView selected;
            public ImageView bankIco;
        }

    }

    public static Dialog showListDialog(Context context, String title, List<String> content, int gravity, AbstractOnItemClickListener onItemClickListener, Btn... btns) {
        final Dialog dialog = new Dialog(context, R.style.Dialog);
        dialog.setCanceledOnTouchOutside(true);
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_list, null);

        TextView titleView = (TextView) view.findViewById(R.id.dialog_title);
        ListView contentView = (ListView) view.findViewById(R.id.dialog_list_view);

        if (!TextUtils.isEmpty(title)) {
            titleView.setText(title);
        } else {
            titleView.setVisibility(View.GONE);
        }
        contentView.setAdapter(new SimpleListAdapter(context, content, gravity));
        contentView.setOnItemClickListener(onItemClickListener);
        onItemClickListener.setDialog(dialog);
        Button leftButton = (Button) view.findViewById(R.id.dialog_btn_left);
        Button rightButton = (Button) view.findViewById(R.id.dialog_btn_right);
        Button button = (Button) view.findViewById(R.id.dialog_btn);
        LinearLayout buttons = (LinearLayout) view.findViewById(R.id.dialog_btns);

        if (btns != null) {
            int length = btns.length;

            if (length == 1) {
                final Btn btn1 = btns[0];
                buttons.removeView(rightButton);
                buttons.removeView(leftButton);

                button.setText(btn1.btn);
                button.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        closeDialog(dialog);
                        if (btn1.clickListener != null) {
                            btn1.clickListener.onClick(v);
                        }
                    }
                });
            } else if (length == 2) {
                buttons.removeView(button);
                final Btn btn1 = btns[0];
                final Btn btn2 = btns[1];
                leftButton.setText(btn1.btn);
                leftButton.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        closeDialog(dialog);
                        if (btn1.clickListener != null) {
                            btn1.clickListener.onClick(v);
                        }
                    }
                });


                rightButton.setText(btn2.btn);
                rightButton.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        closeDialog(dialog);
                        if (btn2.clickListener != null) {
                            btn2.clickListener.onClick(v);
                        }
                    }
                });

            } else {
                buttons.removeAllViews();
            }

        }

        dialog.setContentView(view);
        dialog.show();
        return dialog;

    }

    public static Dialog showMapListDialog(Context context, String title, List<Map.Entry<String, String>> content, int gravity, AbstractOnItemClickListener onItemClickListener, Btn... btns) {
        final Dialog dialog = new Dialog(context, R.style.Dialog);
        dialog.setCanceledOnTouchOutside(true);
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_list, null);

        TextView titleView = (TextView) view.findViewById(R.id.dialog_title);
        ListView contentView = (ListView) view.findViewById(R.id.dialog_list_view);

        if (!TextUtils.isEmpty(title)) {
            titleView.setText(title);
        } else {
            titleView.setVisibility(View.GONE);
        }
//        contentView.setText(content);
//        contentView.setGravity(gravity);
        contentView.setAdapter(new SimpleMapListAdapter(context, content, gravity));
        contentView.setOnItemClickListener(onItemClickListener);
        onItemClickListener.setDialog(dialog);
        Button leftButton = (Button) view.findViewById(R.id.dialog_btn_left);
        Button rightButton = (Button) view.findViewById(R.id.dialog_btn_right);
        Button button = (Button) view.findViewById(R.id.dialog_btn);
        LinearLayout buttons = (LinearLayout) view.findViewById(R.id.dialog_btns);

        if (btns != null) {
            int length = btns.length;

            if (length == 1) {
                final Btn btn1 = btns[0];
                buttons.removeView(rightButton);
                buttons.removeView(leftButton);

                button.setText(btn1.btn);
                button.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        closeDialog(dialog);
                        if (btn1.clickListener != null) {
                            btn1.clickListener.onClick(v);
                        }
                    }
                });
            } else if (length == 2) {
                buttons.removeView(button);
                final Btn btn1 = btns[0];
                final Btn btn2 = btns[1];
                leftButton.setText(btn1.btn);
                leftButton.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        closeDialog(dialog);
                        if (btn1.clickListener != null) {
                            btn1.clickListener.onClick(v);
                        }
                    }
                });


                rightButton.setText(btn2.btn);
                rightButton.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        closeDialog(dialog);
                        if (btn2.clickListener != null) {
                            btn2.clickListener.onClick(v);
                        }
                    }
                });

            } else {
                buttons.removeAllViews();
            }

        }

        dialog.setContentView(view);
        dialog.show();
        return dialog;

    }

    public static void closeDialog(Dialog dialog) {
        try {
            if (dialog != null) {
                dialog.dismiss();
            }
        } catch (Exception e) {

        }
    }

    public static class SimpleListAdapter extends BaseAdapter {
        Context context;
        List<String> lists;
        int gravity;
        List<Integer> icons;

        public SimpleListAdapter(Context context, List<String> lists, int gravity) {
            this.context = context;
            this.lists = Utils.cutNull(lists);
            this.gravity = gravity;
        }

        public SimpleListAdapter(Context context, List<String> lists, List<Integer> integers, int gravity) {
            this.context = context;
            this.lists = Utils.cutNull(lists);
            this.gravity = gravity;
            this.icons = Utils.cutNull(icons);
        }

        @Override
        public int getCount() {
            return lists.size();
        }

        @Override
        public String getItem(int position) {
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
                convertView = LayoutInflater.from(context).inflate(R.layout.dialog_list_item, null, false);
                holder.textView = (TextView) convertView.findViewById(R.id.text);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            holder.textView.setText(getItem(position));

            return convertView;
        }

        class ViewHolder {
            public TextView textView;
        }

    }

    public static class SimpleMapListAdapter extends BaseAdapter {
        Context context;
        List<Map.Entry<String, String>> lists;
        int gravity;
        List<Integer> icons;

        public SimpleMapListAdapter(Context context, List<Map.Entry<String, String>> lists, int gravity) {
            this.context = context;
            this.lists = Utils.cutNull(lists);
            this.gravity = gravity;
        }

        public SimpleMapListAdapter(Context context, List<Map.Entry<String, String>> lists, List<Integer> integers, int gravity) {
            this.context = context;
            this.lists = Utils.cutNull(lists);
            this.gravity = gravity;
            this.icons = Utils.cutNull(icons);
        }

        @Override
        public int getCount() {
            return lists.size();
        }

        @Override
        public Map.Entry<String, String> getItem(int position) {
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
                convertView = LayoutInflater.from(context).inflate(R.layout.dialog_list_item, null, false);
                holder.textView = (TextView) convertView.findViewById(R.id.text);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            holder.textView.setText(getItem(position).getValue());

            return convertView;
        }

        class ViewHolder {
            public TextView textView;
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
