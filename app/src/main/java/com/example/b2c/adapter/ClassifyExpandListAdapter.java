package com.example.b2c.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.b2c.R;
import com.example.b2c.activity.GoodsListActivity;
import com.example.b2c.net.response.TransFirstCategory;
import com.example.b2c.widget.util.Utils;

import java.util.List;

public abstract class ClassifyExpandListAdapter extends BaseExpandableListAdapter {
    private Context context;
    private List<TransFirstCategory> firstCategoryList;
    private String type;
    String categoryName = "";

    public ClassifyExpandListAdapter(Context context, List<TransFirstCategory> firstCategoryList) {
        super();
        this.context = context;
        this.firstCategoryList = firstCategoryList;
    }

    public ClassifyExpandListAdapter(Context context, List<TransFirstCategory> transFirstCategories, String type) {
        super();
        this.context = context;
        this.firstCategoryList = transFirstCategories;
        this.type = type;
    }

    @Override
    public int getGroupCount() {
        return firstCategoryList.size();
    }

    @Override
    public Object getGroup(int arg0) {
        return firstCategoryList.get(arg0);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return firstCategoryList.get(groupPosition).getSecondCategoryList().get(childPosition);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getChildView(final int group_position, final int child_position, boolean isLastChild, View convertView, ViewGroup parent) {
        ChildViewHolder childHolder = null;
        if (firstCategoryList.get(group_position).getSecondCategoryList()
                .get(child_position).getCategoryLevel() == 1) {
            convertView = View.inflate(context, R.layout.classify_second_item, null);
        } else {
            convertView = View.inflate(context, R.layout.classify_third_item, null);
        }
        childHolder = new ChildViewHolder();
        childHolder.rl_item = (RelativeLayout) convertView
                .findViewById(R.id.rl_child_item);
        childHolder.tv_child = (TextView) convertView
                .findViewById(R.id.tv_classify_item);
        categoryName = Utils.cutNull(firstCategoryList.get(group_position).getSecondCategoryList().get(child_position).getName());
        childHolder.rl_item.setOnClickListener(new ChildToGoodsListListener(
                group_position, child_position));
        childHolder.rl_item.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (type.equals("activity")) {
                    itemClick(categoryName);
                } else if (type.equals("fragment")) {
                    itemClick(firstCategoryList.get(group_position)
                            .getSecondCategoryList().get(child_position).getId() + "");
                }
            }
        });

        childHolder.tv_child.setText(firstCategoryList.get(group_position)
                .getSecondCategoryList().get(child_position).getName());
        return convertView;
    }

    @Override
    public int getChildrenCount(int position) {
        return firstCategoryList.get(position).getSecondCategoryList().size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public View getGroupView(int position, boolean isExpanded, View convertView, ViewGroup parent) {
        GroupViewHolder groupHolder;
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.classify_gourp_item, null);
            groupHolder = new GroupViewHolder();
            groupHolder.tv_group = (TextView) convertView.findViewById(R.id.tv_classify_group);
            groupHolder.iv_indicator = (ImageView) convertView.findViewById(R.id.iv_indicator);
            convertView.setTag(groupHolder);
        } else {
            groupHolder = (GroupViewHolder) convertView.getTag();
        }
        if (isExpanded) {
            groupHolder.iv_indicator
                    .setBackgroundResource(R.drawable.classify_indicator_unexpand);
        } else {
            groupHolder.iv_indicator
                    .setBackgroundResource(R.drawable.classify_indicator_expand);
        }
        groupHolder.tv_group.setText(firstCategoryList.get(position).getName());
        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public boolean isChildSelectable(int arg0, int arg1) {
        return true;
    }

    public class GroupViewHolder {
        TextView tv_group;
        ImageView iv_indicator;
    }

    public class ChildViewHolder {
        TextView tv_child;
        RelativeLayout rl_item;
    }

    class ChildToGoodsListListener implements OnClickListener {
        private int groupPosition, childPosition;

        public ChildToGoodsListListener(int groupPositoin, int childPosition) {
            super();
            this.groupPosition = groupPositoin;
            this.childPosition = childPosition;
        }

        @Override
        public void onClick(View arg0) {
            Intent i = new Intent(context, GoodsListActivity.class);
            Bundle bundle = new Bundle();
            bundle.putString("categoryId", firstCategoryList.get(groupPosition)
                    .getSecondCategoryList().get(childPosition).getId() + "");
            i.putExtras(bundle);
            context.startActivity(i);
        }
    }

    public abstract void itemClick(String name);
}
