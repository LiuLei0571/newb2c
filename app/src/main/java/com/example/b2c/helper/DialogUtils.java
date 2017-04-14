package com.example.b2c.helper;

import android.app.Dialog;
import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.example.b2c.R;


/**
 * 弹出对话框
 */
public class DialogUtils {
    public AlertDialog aa;
    private Context context;
    public DialogUtils (Context context){
        this.context=context;
    }
    /**
     * 弹出提示对话框
     */
    public void myshowDialog(String jindu,String zuo,String you){
        //TODO
        AlertDialog.Builder builder=new AlertDialog.Builder(context);
        View view= View.inflate(context, R.layout.adapter_newsfudao_popwin,null);
        builder.setView(view);
        TextView tv_count=  (TextView) view.findViewById(R.id.pop_tv_count);
        TextView pop_tv_chongzuo= (TextView) view.findViewById(R.id.pop_tv_chongzuo);
        TextView pop_tv_jixu= (TextView) view.findViewById(R.id.pop_tv_jixu);
        tv_count.setText(jindu);
        pop_tv_chongzuo.setText(zuo);
        pop_tv_jixu.setText(you);
        //点击重做
        pop_tv_chongzuo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickListener.onZuoClickListener();
            }
        });
        //点击确定
        pop_tv_jixu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickListener.onYouClickListener();
            }
        });
        aa=builder.show();

    }
    public interface MyOnClickListener{
        /**
         * 左边按钮的回调
         */
        public void onZuoClickListener();
        /**
         * 右边按钮的回调
         */
        public void onYouClickListener();
    }
    private MyOnClickListener onClickListener;
    public void setMyOnClickListener(MyOnClickListener onClickListener){
        this.onClickListener=onClickListener;
    }
}
