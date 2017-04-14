package com.example.b2c.net.response;

import com.example.b2c.adapter.TreeViewAdapter;

import java.util.ArrayList;
import java.util.List;

public class SuperTreeNode {
	public Object parent;
	public List<TreeViewAdapter.TreeNode> childs = new ArrayList<TreeViewAdapter.TreeNode>();
}
