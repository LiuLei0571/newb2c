package com.example.b2c.adapter;

import java.util.ArrayList;
import java.util.List;

import com.example.b2c.R;
import com.example.b2c.activity.GoodsListActivity;
import com.example.b2c.adapter.TreeViewAdapter.TreeNode;
import com.example.b2c.widget.RemoteDataManager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.TextView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ExpandableListView.OnGroupCollapseListener;
import android.widget.ExpandableListView.OnGroupExpandListener;

@Deprecated
public class SuperTreeViewAdapter extends BaseExpandableListAdapter {
	private LayoutInflater inflater;
	private RemoteDataManager rdm;
	private boolean isLogin;
	private int userId;
	private String token;
	public static final int HIGH_CATEGORY = 1;
	public static final int MID_CATEGORY=2;
	public static final int LOW_CATEGORY = 3;

	private Intent intent;
	private Bundle bundle;

	static public class SuperTreeNode {
		public Object parent;
		public String idPath;
		public List<TreeNode> childs = new ArrayList<TreeNode>();
	}

	private List<SuperTreeNode> superTreeNodes = new ArrayList<SuperTreeNode>();
	private Context parentContext;
	private OnChildClickListener stvClickEvent;

	public SuperTreeViewAdapter(Context parentContext, RemoteDataManager rdm,
			boolean isLogin, int userId, String token) {
		super();
		this.parentContext = parentContext;
		inflater = LayoutInflater.from(this.parentContext);
		this.isLogin = isLogin;
		this.userId = userId;
		this.token = token;
		this.rdm = rdm;
		intent = new Intent();
		intent.setClass(parentContext, GoodsListActivity.class);
		bundle = new Bundle();
	}

	public SuperTreeViewAdapter(Context view, OnChildClickListener stvClickEvent) {
		parentContext = view;
		this.stvClickEvent = stvClickEvent;
	}

	public List<SuperTreeNode> GetTreeNode() {
		return superTreeNodes;
	}

	public void UpdateTreeNode(List<SuperTreeNode> node) {
		superTreeNodes = node;
	}

	public void RemoveAll() {
		superTreeNodes.clear();
	}

	public Object getChild(int groupPosition, int childPosition) {
		return superTreeNodes.get(groupPosition).childs.get(childPosition);
	}

	public int getChildrenCount(int groupPosition) {
		return superTreeNodes.get(groupPosition).childs.size();
	}

	public ExpandableListView getExpandableListView() {
		AbsListView.LayoutParams lp = new AbsListView.LayoutParams(
				ViewGroup.LayoutParams.MATCH_PARENT, TreeViewAdapter.ItemHeight);
		ExpandableListView superTreeView = new ExpandableListView(parentContext);
		superTreeView.setLayoutParams(lp);
		return superTreeView;
	}

	public View getChildView(final int groupPosition, final int childPosition,
			boolean isLastChild, View convertView, final ViewGroup parent) {
		
		convertView = inflater.inflate(R.layout.classify_gourp_item, null);
		final ExpandableListView treeView = getExpandableListView();
		final TreeViewAdapter treeViewAdapter = new TreeViewAdapter(
				this.parentContext, 0);
		List<TreeNode> tmp = treeViewAdapter.getTreeNode();// ��ʱ����ȡ��TreeViewAdapter��TreeNode���ϣ���Ϊ��
		final TreeNode treeNode = (TreeNode) getChild(groupPosition,
				childPosition);
		tmp.add(treeNode);
		treeViewAdapter.updateTreeNode(tmp);
		treeView.setAdapter(treeViewAdapter);
		treeView.setGroupIndicator(null);
		
		treeView.setOnChildClickListener(new OnChildClickListener() {

			@Override
			public boolean onChildClick(ExpandableListView second_parent,
					View v, int grounp_id, int child_id, long id) {
				// TODO Auto-generated method stub
				int categoryId = superTreeNodes.get(groupPosition).childs
						.get(childPosition).childs_id.get(child_id);
				bundle.putInt("categoryType", LOW_CATEGORY);
				bundle.putString("categoryId", categoryId+"");
				intent.putExtras(bundle);
				parentContext.startActivity(intent);
				return false;
			}
		});

		
		treeView.setOnGroupExpandListener(new OnGroupExpandListener() {
			@Override
			public void onGroupExpand(int groupPosition) {

				AbsListView.LayoutParams lp = new AbsListView.LayoutParams(
						ViewGroup.LayoutParams.MATCH_PARENT, (treeNode.childs
								.size() + 1) * TreeViewAdapter.ItemHeight + 10);
				treeView.setLayoutParams(lp);
			}
		});

		
		treeView.setOnGroupCollapseListener(new OnGroupCollapseListener() {
			@Override
			public void onGroupCollapse(int groupPosition) {

				AbsListView.LayoutParams lp = new AbsListView.LayoutParams(
						ViewGroup.LayoutParams.MATCH_PARENT,
						TreeViewAdapter.ItemHeight);
				treeView.setLayoutParams(lp);
			}
		});
		// treeView.setPadding(TreeViewAdapter.PaddingLeft * 2, 0, 0, 0);
		return treeView;
	}

	
	public View getGroupView(final int groupPosition, boolean isExpanded,
			View convertView, ViewGroup parent) {
		GroupViewHolder group_holder;
		if (convertView == null) {
			group_holder = new GroupViewHolder();
			convertView = inflater.inflate(R.layout.classify_gourp_item, null);
			group_holder.tv_group = (TextView) convertView
					.findViewById(R.id.tv_classify_group);
			group_holder.tv_group.setText(getGroup(groupPosition).toString());
			convertView.setTag(group_holder);
		} else {
			group_holder = (GroupViewHolder) convertView.getTag();
		}
		group_holder.tv_group.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				String categoryId = superTreeNodes.get(groupPosition).idPath;
				bundle.putInt("categoryType", HIGH_CATEGORY);
				bundle.putString("categoryId", categoryId);
				intent.putExtras(bundle);
				parentContext.startActivity(intent);
			}
		});
		return convertView;
	}

	public long getChildId(int groupPosition, int childPosition) {
		return childPosition;
	}

	public Object getGroup(int groupPosition) {
		return superTreeNodes.get(groupPosition).parent;
	}

	public int getGroupCount() {
		return superTreeNodes.size();
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

	public class GroupViewHolder {
		TextView tv_group;
	}
}
