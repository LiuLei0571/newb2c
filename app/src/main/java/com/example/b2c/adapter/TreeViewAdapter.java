package com.example.b2c.adapter;

import java.util.ArrayList;
import java.util.List;

import com.example.b2c.R;
import com.example.b2c.activity.GoodsListActivity;
import com.example.b2c.adapter.Classify_List_Adapter.ChildViewHolder;
import com.example.b2c.adapter.Classify_List_Adapter.GroupViewHolder;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.AbsListView;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.TextView;

public class TreeViewAdapter extends BaseExpandableListAdapter {

	public static int ItemHeight = 80;
	public static final int PaddingLeft = 38;
	private int myPaddingLeft = 0;
	private LayoutInflater inflater;
	private Intent intent;
	private Bundle bundle;
	public static final int HIGH_CATEGORY = 1;
	public static final int MID_CATEGORY=2;
	public static final int LOW_CATEGORY = 3;

	static public class TreeNode {
		public Object parent;
		public int parent_id;
		public List<Object> childs = new ArrayList<Object>();
		public List<Integer> childs_id = new ArrayList<Integer>();
	}

	List<TreeNode> treeNodes = new ArrayList<TreeNode>();
	Context parentContext;

	public TreeViewAdapter(Context context, int myPaddingLeft) {
		parentContext = context;
		this.myPaddingLeft = myPaddingLeft;
		inflater = LayoutInflater.from(parentContext);
		intent = new Intent();
		intent.setClass(parentContext, GoodsListActivity.class);
		bundle = new Bundle();
	}

	public List<TreeNode> getTreeNode() {
		return treeNodes;
	}

	public void updateTreeNode(List<TreeNode> nodes) {
		treeNodes = nodes;
	}

	public void removeAll() {
		treeNodes.clear();
	}

	@Override
	public Object getChild(int groupPosition, int childPosition) {
		// TODO Auto-generated method stub
		return treeNodes.get(groupPosition).childs.get(childPosition);
	}

	@Override
	public long getChildId(int groupPosition, int childPosition) {
		// TODO Auto-generated method stub
		return childPosition;
	}

	static public TextView getTextView(Context context) {
		AbsListView.LayoutParams lp = new AbsListView.LayoutParams(
				LayoutParams.MATCH_PARENT, ItemHeight);
		TextView textView = new TextView(context);
		textView.setLayoutParams(lp);
		textView.setGravity(Gravity.CENTER_VERTICAL | Gravity.LEFT);
		return textView;
	}

	@Override
	public View getChildView(int groupPosition, int childPosition,
			boolean isLastChild, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ChildViewHolder group_holder;
		if (convertView == null) {
			group_holder = new ChildViewHolder();
			convertView = inflater.inflate(R.layout.classify_child_item, null);
			LayoutParams lp = new LayoutParams(LayoutParams.MATCH_PARENT,
					ItemHeight);
			convertView.setLayoutParams(lp);
			group_holder.tv_child = (TextView) convertView
					.findViewById(R.id.tv_classify_child);
			group_holder.tv_child
					.setText(getChild(groupPosition, childPosition).toString());
			convertView.setTag(group_holder);
		} else {
			group_holder = (ChildViewHolder) convertView.getTag();
		}
		return convertView;
		// TextView textView = getTextView(this.parentContext);
		// textView.setText(getChild(groupPosition, childPosition).toString());
		// textView.setPadding(myPaddingLeft + PaddingLeft, 0, 0, 0);
	}

	@Override
	public int getChildrenCount(int groupPosition) {
		// TODO Auto-generated method stub
		return treeNodes.get(groupPosition).childs.size();
	}

	@Override
	public Object getGroup(int groupPosition) {
		// TODO Auto-generated method stub
		return treeNodes.get(groupPosition).parent;
	}

	@Override
	public int getGroupCount() {
		// TODO Auto-generated method stub
		return treeNodes.size();
	}

	@Override
	public long getGroupId(int groupPosition) {
		// TODO Auto-generated method stub
		return groupPosition;
	}

	@Override
	public View getGroupView(final int groupPosition, boolean isExpanded,
			View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		GroupViewHolder group_holder;
		if (convertView == null) {
			group_holder = new GroupViewHolder();
			convertView = inflater.inflate(R.layout.classify_gourp_item, null);
			LayoutParams lp = new LayoutParams(LayoutParams.MATCH_PARENT,
					ItemHeight);
			convertView.setLayoutParams(lp);
			group_holder.tv_group = (TextView) convertView
					.findViewById(R.id.tv_classify_group);
			group_holder.tv_group.setText(getGroup(groupPosition).toString());
			group_holder.tv_group.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					String categoryId = treeNodes.get(groupPosition).parent_id+"";
					bundle.putInt("categoryType", MID_CATEGORY);
					bundle.putString("categoryId", categoryId);
					intent.putExtras(bundle);
					parentContext.startActivity(intent);	
				}
			});
			
			convertView.setTag(group_holder);
		} else {
			group_holder = (GroupViewHolder) convertView.getTag();
		}
		return convertView;
	}

	@Override
	public boolean hasStableIds() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		// TODO Auto-generated method stub
		return true;
	}

	class GroupViewHolder {
		TextView tv_group;
	}

	class ChildViewHolder {
		TextView tv_child;
	}
}
