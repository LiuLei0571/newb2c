package com.example.b2c.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.example.b2c.R;
import com.example.b2c.activity.common.TempBaseActivity;
import com.example.b2c.adapter.SampleCommentAdapter;
import com.example.b2c.helper.ToastHelper;
import com.example.b2c.net.listener.SampleCommentListener;
import com.example.b2c.net.response.SampleEvaluationListDetail;

import java.util.List;

/**
 * 商品详情----查看商品评价页面
 */
public class GoodsCommentsActivity extends TempBaseActivity {
	private ListView lv_comment;
	private List<SampleEvaluationListDetail> evaluation_list;
	private SampleCommentAdapter adapter;
	private int sampleId;

	@Override
	public int getRootId() {
		return R.layout.goods_comments_layout;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Intent i = getIntent();
		Bundle bundle = i.getExtras();
		sampleId = bundle.getInt("sampleId");

		initView();
		initData();
	}

	public void initView() {
		lv_comment = (ListView) findViewById(R.id.lv_sample_comment);

		initText();
	}

	public void initText() {
		setTitle(mTranslatesString.getCommon_samplecomment());
	}

	public void initData() {
		showProgressDialog(Loading);
		rdm.GetSampleComment(unLogin, sampleId);
		rdm.samplecommentListener = new SampleCommentListener() {

			@Override
			public void onSuccess(List<SampleEvaluationListDetail> sampleEvaluationList) {
				setAdapter(sampleEvaluationList);

			}

			@Override
			public void onError(int errorNO, String errorInfo) {
				ToastHelper.makeErrorToast(errorInfo);
			}

			@Override
			public void onFinish() {
				dismissProgressDialog();
			}

			@Override
			public void onLose() {
				ToastHelper.makeErrorToast(request_failure);
			}
		};
	}

	public void setAdapter(List<SampleEvaluationListDetail> sampleEvaluationList) {
		evaluation_list = sampleEvaluationList;
		adapter = new SampleCommentAdapter(GoodsCommentsActivity.this,
				evaluation_list);
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				if(evaluation_list!=null && evaluation_list.size()>0){
					mEmpty.setVisibility(View.GONE);
				}else{
					mEmpty.setVisibility(View.VISIBLE);
				}
				lv_comment.setAdapter(adapter);
			}
		});
	}

}
