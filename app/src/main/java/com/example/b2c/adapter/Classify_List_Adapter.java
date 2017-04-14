package com.example.b2c.adapter;

import java.util.ArrayList;
import java.util.List;

import com.example.b2c.R;
import com.example.b2c.net.response.FirstCategoryDetail;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

public class Classify_List_Adapter extends BaseExpandableListAdapter {
	public static final int ItemHeight = 48;// 每项的高度
	public static final int PaddingLeft = 36;// 每项的高度
	private int myPaddingLeft = 0;// 如果是由SuperTreeView调用，则作为子项需要往右移
	private LayoutInflater inflater;
	public String[] groups;
	public String[][] childs;
	public ArrayList<FirstCategoryDetail> category_list;

	static public class TreeNode {
		public Object parent;
		public List<Object> childs = new ArrayList<Object>();
	}

	List<TreeNode> treeNodes = new ArrayList<TreeNode>();
	Context parentContext;

	public Classify_List_Adapter(Context view, int myPaddingLeft,
			String[] group, String[][] child) {
		parentContext = view;
		this.myPaddingLeft = myPaddingLeft;
		inflater = LayoutInflater.from(view);
		groups = group;
		childs = child;
	}

	public Classify_List_Adapter(int myPaddingLeft,
			ArrayList<FirstCategoryDetail> category_list, Context parentContext) {
		super();
		this.myPaddingLeft = myPaddingLeft;
		this.category_list = category_list;
		this.parentContext = parentContext;
	}

	public List<TreeNode> GetTreeNode() {
		return treeNodes;
	}

	public void UpdateTreeNode(List<TreeNode> nodes) {
		treeNodes = nodes;
	}

	public void RemoveAll() {
		treeNodes.clear();
	}

	public Object getChild(int groupPosition, int childPosition) {
		return treeNodes.get(groupPosition).childs.get(childPosition);
	}

	public int getChildrenCount(int groupPosition) {
		return treeNodes.get(groupPosition).childs.size();
	}

	static public TextView getTextView(Context context) {
		AbsListView.LayoutParams lp = new AbsListView.LayoutParams(
				ViewGroup.LayoutParams.FILL_PARENT, ItemHeight);

		TextView textView = new TextView(context);
		textView.setLayoutParams(lp);
		textView.setGravity(Gravity.CENTER_VERTICAL | Gravity.LEFT);
		return textView;
	}

	public View getChildView(int groupPosition, int childPosition,
			boolean isLastChild, View convertView, ViewGroup parent) {
		ChildViewHolder group_holder;
		if (convertView == null) {
			group_holder = new ChildViewHolder();
			convertView = inflater.inflate(R.layout.classify_child_item, null);
			group_holder.tv_child = (TextView) convertView
					.findViewById(R.id.tv_classify_child);
			 group_holder.tv_child.setText(childs[groupPosition][childPosition]);
			convertView.setTag(group_holder);
		} else {
			group_holder = (ChildViewHolder) convertView.getTag();
		}
		return convertView;
	}

	public View getGroupView(int groupPosition, boolean isExpanded,
			View convertView, ViewGroup parent) {
		GroupViewHolder group_holder;
		if (convertView == null) {
			group_holder = new GroupViewHolder();
			convertView = inflater.inflate(R.layout.classify_gourp_item, null);
			group_holder.tv_group = (TextView) convertView
					.findViewById(R.id.tv_classify_group);
			 group_holder.tv_group.setText(groups[groupPosition]);
//			group_holder.tv_group.setText(category_list.getFirstCategoryList()
//					.getFirstCategoryList());
			convertView.setTag(group_holder);
		} else {
			group_holder = (GroupViewHolder) convertView.getTag();
		}

		return convertView;
	}

	public long getChildId(int groupPosition, int childPosition) {
		return childPosition;
	}

	public Object getGroup(int groupPosition) {
		return treeNodes.get(groupPosition).parent;
	}

	public int getGroupCount() {
		return treeNodes.size();
	}

	public long getGroupId(int groupPosition) {
		return groupPosition;
	}

	public boolean isChildSelectable(int groupPosition, int childPosition) {
		return true;
	}

	public boolean hasStableIds() {
		return true;
	}

	class GroupViewHolder {
		TextView tv_group;
	}

	class ChildViewHolder {
		TextView tv_child;
	}
}